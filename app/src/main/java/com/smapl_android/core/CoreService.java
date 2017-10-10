package com.smapl_android.core;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.smapl_android.model.UserInfo;
import com.smapl_android.net.NetworkService;
import com.smapl_android.net.NetworkServiceFactory;
import com.smapl_android.net.requests.CoordinateRequest;
import com.smapl_android.net.requests.EditProfileRequest;
import com.smapl_android.net.requests.RegistrationRequest;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.*;
import com.smapl_android.storage.SessionStorage;
import com.smapl_android.ui.base.CoreActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoreService {

    private static final String TAG = CoreService.class.getSimpleName();

    private final SessionStorage sessionStorage;

    private final UserInfo userInfo = new UserInfo();

    private final NetworkService networkServiceImpl;

    private Handler uiHandler;

    public CoreService(Context rootContext) {
        this.networkServiceImpl = NetworkServiceFactory.create(false);
        this.uiHandler = new Handler(Looper.getMainLooper());
        this.sessionStorage = new SessionStorage(rootContext);
    }

    public boolean isLoggedIn() {
        return sessionStorage.isLoggedIn();
    }

    public void login(String login, String password, final CoreRequest<Boolean> coreRequest) {

        networkServiceImpl.login(login, password, new NetworkService.OnResultCallback<LoginResponse, Throwable>() {
            @Override
            public void onResult(LoginResponse result, final Throwable error) {
                if (coreRequest != null) {
                    if (error != null) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processError(error);
                            }
                        });

                    } else {
                        sessionStorage.saveAuthKey(result.getId());
                        sessionStorage.saveUserId(result.getUserId());
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processResult(true);
                            }
                        });
                    }
                }
            }
        });
    }

    public void registration(final RegistrationRequest user, final CoreRequest<Boolean> successOutput) {

        networkServiceImpl.registration(user, new NetworkService.OnResultCallback<RegistrationResponse, Throwable>() {
            @Override
            public void onResult(RegistrationResponse result, final Throwable error) {
                if (successOutput != null) {
                    if (error != null) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                successOutput.processError(error);
                            }
                        });
                    } else {
                        login(user.getPhoneNumber(), user.getPassword(), successOutput);
                    }
                }
            }
        });
    }

    public void logout(final CoreRequest<Boolean> request) {
        String token = sessionStorage.getAuthKey();
        sessionStorage.logout();
        networkServiceImpl.logout(token, getCallback(request));
    }

    public void getUser(final CoreRequest<UserResponse> coreRequest) {
        int userId = sessionStorage.getUserId();
        String token = sessionStorage.getAuthKey();
        Log.d(TAG, "getUser: " + userId);
        Log.d(TAG, "getUser: " + token);
        networkServiceImpl.getUserById(userId, token, getCallback(coreRequest));
    }

    public <T> CoreRequest<T> newRequest(CoreActivity activity) {
        return new CoreRequest<T>(activity, this);
    }

    public void updateCar(UpdateCarRequest updateUserRequest, final CoreRequest<UserResponse> coreRequest) {
        int userId = sessionStorage.getUserId();
        String token = sessionStorage.getAuthKey();
        networkServiceImpl.updateCar(userId, token, updateUserRequest, getCallback(coreRequest));
    }

    @NonNull
    private <T> NetworkService.OnResultCallback<T, Throwable> getCallback(final CoreRequest<T> coreRequest) {
        return new NetworkService.OnResultCallback<T, Throwable>() {
            @Override
            public void onResult(final T result, final Throwable error) {
                if (coreRequest != null) {
                    if (error != null) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processError(error);
                            }
                        });

                    } else {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processResult(result);
                            }
                        });
                    }
                }
            }
        };
    }

    public void changePassword(String oldPassword, String newPassword, final CoreRequest<Boolean> coreRequest) {
        String token = sessionStorage.getAuthKey();
        networkServiceImpl.editPassword(token, oldPassword, newPassword, getCallback(coreRequest));
    }

    public void editProfile(EditProfileRequest request, final CoreRequest<UserResponse> coreRequest) {
        int userId = sessionStorage.getUserId();
        String token = sessionStorage.getAuthKey();
        networkServiceImpl.editProfile(userId, token, request, getCallback(coreRequest));
    }


    public void getCampaigns(final CoreRequest<List<GetCampaignListResponse.Campaign>> coreRequest){
        String token = sessionStorage.getAuthKey();
        networkServiceImpl.getCampaigns(token,  new NetworkService.OnResultCallback<GetCampaignListResponse, Throwable>() {
            @Override
            public void onResult(final GetCampaignListResponse result, final Throwable error) {
                if (coreRequest != null) {
                    if (error != null) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processError(error);
                            }
                        });
                    } else {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processResult(Arrays.asList(result.getCampaigns()));
                            }
                        });
                    }
                }
            }
        });
    }

    public void getHistory(final  CoreRequest<List<GetTrackingHistoryResponse.TrackingHistory>> coreRequest){
        String token = sessionStorage.getAuthKey();
        int userId = sessionStorage.getUserId();
        networkServiceImpl.getHistory(token, userId, new NetworkService.OnResultCallback<GetTrackingHistoryResponse, Throwable>() {
            @Override
            public void onResult(final GetTrackingHistoryResponse result, final Throwable error) {
                if (coreRequest != null) {
                    if (error != null) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processError(error);
                            }
                        });
                    } else {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processResult(Arrays.asList(result.getHistory()));
                            }
                        });
                    }
                }
            }
        });
    }

    public void restorePassword(String email, final CoreRequest<Boolean> request) {
        networkServiceImpl.restorePassword(email,  getCallback(request));
    }

    public void loginFacebook(Activity activity, CallbackManager facebookCallbackManager, final CoreRequest<Boolean> request) {
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email","user_photos","public_profile"));
        LoginManager.getInstance().registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                request.processResult(true);
            }

            @Override
            public void onCancel() {
                request.processResult(false);
            }

            @Override
            public void onError(FacebookException error) {
                request.processError(error);
            }
        });
    }

    public void stopTracking(List<Pair<Double, Double>> coordinates) {
        String token = sessionStorage.getAuthKey();
        final CoordinateRequest request = CoordinateRequest.stop();
        for(Pair<Double, Double> location : coordinates){
            request.addCoordinate(location.first, location.second);
        }
        networkServiceImpl.stopTracking(token, request, new NetworkService.OnResultCallback<TrackingResponse, Throwable>() {
            @Override
            public void onResult(TrackingResponse result, Throwable error) {
                if(error != null) return;
                getUserInfo().drive.set(result.getTotalDistance());
                getUserInfo().earn.set(result.getTotalAmount());
            }
        });
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void startTracking(final  CoreRequest<TrackingResponse> coreRequest, List<Pair<Double, Double>> coordinates) {
        String token = sessionStorage.getAuthKey();
        final CoordinateRequest request = CoordinateRequest.start();
        for(Pair<Double, Double> location : coordinates){
            request.addCoordinate(location.first, location.second);
        }
        networkServiceImpl.startTracking(token, request, new NetworkService.OnResultCallback<TrackingResponse, Throwable>() {
            @Override
            public void onResult(final TrackingResponse result, final Throwable error) {
                if (coreRequest != null) {
                    if (error != null) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processError(error);
                            }
                        });
                    } else {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processResult(result);
                            }
                        });
                    }
                }
            }
        });
    }

    public void updateTracking(List<Pair<Double, Double>> coordinates) {
        String token = sessionStorage.getAuthKey();
        final CoordinateRequest request = CoordinateRequest.inProgress();
        for(Pair<Double, Double> location : coordinates){
            request.addCoordinate(location.first, location.second);
        }
        networkServiceImpl.updateTracking(token, request, new NetworkService.OnResultCallback<TrackingResponse, Throwable>() {
            @Override
            public void onResult(TrackingResponse result, Throwable error) {
                if(error != null) return;
                getUserInfo().drive.set(result.getTotalDistance());
                getUserInfo().earn.set(result.getTotalAmount());
            }
        });
    }
}
