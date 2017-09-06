package com.smapl_android.core;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.net.NetworkService;
import com.smapl_android.net.NetworkServiceFactory;
import com.smapl_android.net.requests.EditProfileRequest;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.*;
import com.smapl_android.storage.SessionStorage;
import com.smapl_android.ui.base.CoreActivity;

import java.util.Arrays;
import java.util.List;

public class CoreService {

    private static final String TAG = CoreService.class.getSimpleName();
    private final Context rootContext;

    private final SessionStorage sessionStorage;

    private final NetworkService networkServiceImpl;

    private Handler uiHandler;

    public CoreService(Context rootContext) {
        this.rootContext = rootContext;
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
                                coreRequest.processError(error.getMessage());
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

    public void registration(final UserInfoViewModel user, final CoreRequest<Boolean> successOutput) {

        networkServiceImpl.registration(user, new NetworkService.OnResultCallback<RegistrationResponse, Throwable>() {
            @Override
            public void onResult(RegistrationResponse result, final Throwable error) {
                if (successOutput != null) {
                    if (error != null) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                successOutput.processError(error.getMessage());
                            }
                        });
                    } else {
                        login(user.phone.get(), user.password.get(), successOutput);
                    }
                }
            }
        });
    }

    public void logout(final CoreRequest<Boolean> request) {
        sessionStorage.logout();
        String token = sessionStorage.getAuthKey();
        networkServiceImpl.logout(token, new NetworkService.OnResultCallback<Boolean, Throwable>() {
            @Override
            public void onResult(final Boolean result, final Throwable error) {
                if (request != null) {
                    if (error != null) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                request.processError(error.getMessage());
                            }
                        });
                    } else {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                request.processResult(result);
                            }
                        });
                    }
                }
            }
        });
    }

    public void getUser(final CoreRequest<UserResponse> coreRequest) {
        int userId = sessionStorage.getUserId();
        String token = sessionStorage.getAuthKey();
        networkServiceImpl.getUserById(userId, token, new NetworkService.OnResultCallback<UserResponse, Throwable>() {
            @Override
            public void onResult(final UserResponse result, final Throwable error) {
                if (coreRequest != null) {
                    if (error != null) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processError(error.getMessage());
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

    public <T> CoreRequest<T> newRequest(CoreActivity activity) {
        return new CoreRequest<T>(activity, this);
    }

    public void updateCar(UpdateCarRequest updateUserRequest, final CoreRequest<Boolean> coreRequest) {
        int userId = sessionStorage.getUserId();
        String token = sessionStorage.getAuthKey();
        networkServiceImpl.updateCar(userId, token, updateUserRequest, new NetworkService.OnResultCallback<Boolean, Throwable>() {
            @Override
            public void onResult(final Boolean result, final Throwable error) {
                if (coreRequest != null) {
                    if (error != null) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processError(error.getMessage());
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

    public void changePassword(String oldPassword, String newPassword, final CoreRequest<Boolean> coreRequest) {
        String token = sessionStorage.getAuthKey();
        networkServiceImpl.editPassword(token, oldPassword, newPassword, new NetworkService.OnResultCallback<Boolean, Throwable>() {
            @Override
            public void onResult(final Boolean result, final Throwable error) {
                if (coreRequest != null) {
                    if (error != null) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processError(error.getMessage());
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

    public void editProfile(EditProfileRequest request, final CoreRequest<EditProfileResponse> coreRequest) {
        int userId = sessionStorage.getUserId();
        String token = sessionStorage.getAuthKey();
        networkServiceImpl.editProfile(userId, token, request, new NetworkService.OnResultCallback<EditProfileResponse, Throwable>() {
            @Override
            public void onResult(final EditProfileResponse result, final Throwable error) {
                if (coreRequest != null) {
                    if (error != null) {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                coreRequest.processError(error.getMessage());
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
                                coreRequest.processError(error.getMessage());
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
}
