package com.smapl_android.core;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.smapl_android.R;
import com.smapl_android.model.UserInfo;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.net.ApiService;
import com.smapl_android.net.NetworkService;
import com.smapl_android.net.NetworkServiceFactory;
import com.smapl_android.net.requests.CoordinateRequest;
import com.smapl_android.net.requests.EditProfileRequest;
import com.smapl_android.net.requests.FbLoginRequest;
import com.smapl_android.net.requests.MoneyWithdrawRequest;
import com.smapl_android.net.requests.RegistrationRequest;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.*;
import com.smapl_android.storage.SessionStorage;
import com.smapl_android.ui.base.CoreActivity;
import com.smapl_android.ui.fragments.AboutYourselfFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CoreService {

    private static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

    private static final String TAG = CoreService.class.getSimpleName();
    public static final int RESPONSE_NO_RELATED_FB_ACCOUNT = 471;

    private final SessionStorage sessionStorage;

    private final UserInfo userInfo = new UserInfo();

    private final NetworkService networkServiceImpl;

    private Handler uiHandler;
    private boolean isEmptyPage = false;

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

    public void joinCampaign(int campaignId, final CoreRequest<Boolean> request) {
        String token = sessionStorage.getAuthKey();
        networkServiceImpl.joinCampaign(campaignId, token, getCallback(request));
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


    public void getCampaigns(final CoreRequest<List<GetCampaignListResponse.Campaign>> coreRequest) {
        String token = sessionStorage.getAuthKey();
        networkServiceImpl.getCampaigns(token, new NetworkService.OnResultCallback<GetCampaignListResponse, Throwable>() {
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

    public void getHistory(int page, final CoreRequest<List<GetTrackingHistoryResponse.TrackingHistory>> coreRequest) {
        final String token = sessionStorage.getAuthKey();
        final int userId = sessionStorage.getUserId();
        networkServiceImpl.getHistory(token, userId, page, new NetworkService.OnResultCallback<GetTrackingHistoryResponse, Throwable>() {
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
                        isEmptyPage = true;
                    } else {
                        if (result.getHistory().length == 0) {
                            isEmptyPage = true;
                        }
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
        networkServiceImpl.restorePassword(email, getCallback(request));
    }

    public void loginFacebook(final Activity activity, CallbackManager facebookCallbackManager, final CoreRequest<Boolean> request) {
        LoginManager.getInstance().registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginWithFacebook((CoreActivity) activity, new FbLoginRequest(loginResult.getAccessToken().getUserId()), request);
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

        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email", "user_birthday", "user_mobile_phone", "public_profile"));
    }

    public void loginWithFacebook(final CoreActivity coreActivity, final FbLoginRequest fbRequest, final CoreRequest<Boolean> coreRequest) {

        networkServiceImpl.fbLogin(fbRequest, new NetworkService.OnResultCallback<LoginResponse, Throwable>() {
            @Override
            public void onResult(LoginResponse result, Throwable error) {

                if (error == null) {
                    if (result.isSuccess()) {
                        Log.d(TAG, "onResult.success: " + result.getResult());
                        sessionStorage.saveUserId(result.getUserId());
                        sessionStorage.saveAuthKey(result.getId());
                        coreRequest.processResult(true);
                    } else {
                        Log.d(TAG, "onResult: " + result);
                        coreRequest.processResult(false);
                    }
                } else {
                    Log.e(TAG, "onResult: error = " + error.getMessage());

                    if (error instanceof ErrorResponse) {
                        ErrorResponse errorResponse = (ErrorResponse) error;
                        if (errorResponse.getCode() == RESPONSE_NO_RELATED_FB_ACCOUNT) {

                            GraphRequest graphRequest = GraphRequest.newMeRequest(
                                    AccessToken.getCurrentAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject object, GraphResponse response) {
                                            Log.d(TAG, "onCompleted: ");
                                            UserInfoViewModel user = new UserInfoViewModel();
                                            try {
                                                user.name.set(object.getString("name"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            try {
                                                user.email.set(object.getString("email"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            try {
                                                user.gender.set(object.getString("gender").equals("male") ?
                                                        coreActivity.getString(R.string.man) : coreActivity.getString(R.string.woman));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            user.setFbToken(AccessToken.getCurrentAccessToken().getUserId());
                                            coreActivity.replaceContentWithHistory(AboutYourselfFragment.create(user));
                                        }
                                    });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "gender,birthday,name,email");
                            graphRequest.setParameters(parameters);
                            graphRequest.executeAsync();

                        }
                    }
                }
            }
        });

    }

    public void stopTracking(final CoreRequest<TrackingResponse> coreRequest, List<Pair<Double, Double>> coordinates) {
        String token = sessionStorage.getAuthKey();
        final CoordinateRequest request = CoordinateRequest.stop();
        for (Pair<Double, Double> location : coordinates) {
            request.addCoordinate(location.first, location.second);
        }
        networkServiceImpl.stopTracking(token, request, new NetworkService.OnResultCallback<TrackingResponse, Throwable>() {
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

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void startTracking(final CoreRequest<TrackingResponse> coreRequest) {
        final String token = sessionStorage.getAuthKey();
        networkServiceImpl.startTracking(token, CoordinateRequest.start(), new NetworkService.OnResultCallback<TrackingResponse, Throwable>() {
            @Override
            public void onResult(final TrackingResponse result, final Throwable error) {
                final NetworkService.OnResultCallback<TrackingResponse, Throwable> startCallback = this;
                if (coreRequest != null) {
                    if (error != null) {
                        if (error instanceof ErrorResponse) {
                            ErrorResponse errorResponse = (ErrorResponse) error;
                            if (errorResponse.getCode() == 461) {
                                networkServiceImpl.stopTracking(token, CoordinateRequest.stop(), new NetworkService.OnResultCallback<TrackingResponse, Throwable>() {
                                    @Override
                                    public void onResult(TrackingResponse result, Throwable error) {
                                        if (error != null) return;
                                        networkServiceImpl.startTracking(token, CoordinateRequest.start(), startCallback);
                                    }
                                });
                                return;
                            }
                        }
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
        for (Pair<Double, Double> location : coordinates) {
            request.addCoordinate(location.first, location.second);
        }
        networkServiceImpl.updateTracking(token, request, new NetworkService.OnResultCallback<TrackingResponse, Throwable>() {
            @Override
            public void onResult(TrackingResponse result, Throwable error) {
                if (error != null) return;
                getUserInfo().currentDrive.set(result.getTotalDistance());
                getUserInfo().currentEarn.set(result.getTotalAmount());
            }
        });
    }

    public void uploadCarPhoto(String path, final CoreRequest<Boolean> coreRequest) {
        File file = new File(path);
        final String name = file.getName();
        final String extension = name.substring(name.lastIndexOf(".") + 1);

        MultipartBody.Builder requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);


        String filePartHeader = String.format("form-data; name=\"image\"; filename=\"%s\"", file.getName(), file.getName());

        requestBody.addPart(
                Headers.of(HEADER_CONTENT_DISPOSITION, filePartHeader),
                RequestBody.create(okhttp3.MediaType.parse("image/" + extension), file));


        final Request request = new Request.Builder()
                .url(ApiService.DEV_URL + "api/user/upload_image/" + sessionStorage.getUserId())
                .addHeader("Authorization", sessionStorage.getAuthKey())
                .post(requestBody.build())
                .build();

        OkHttpClient client = new OkHttpClient.Builder().build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException error) {
                if (coreRequest != null) {
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            coreRequest.processError(error);
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (coreRequest != null) {
                    uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            coreRequest.processResult(response.isSuccessful());
                        }
                    });
                }
            }
        });

    }

    public void withdrawMoney(MoneyWithdrawRequest moneyWithdrawRequest, final CoreRequest<Boolean> coreRequest) {
        String token = sessionStorage.getAuthKey();
        networkServiceImpl.withdrawMoney(token, moneyWithdrawRequest, getCallback(coreRequest));
    }
}
