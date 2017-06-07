package com.smapl_android.core;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.models.User;
import com.smapl_android.net.NetworkService;
import com.smapl_android.net.NetworkServiceFactory;
import com.smapl_android.net.responses.LoginResponse;
import com.smapl_android.net.responses.RegistrationResponse;
import com.smapl_android.ui.CoreActivity;

public class CoreService {

    private final Context rootContext;

    private final NetworkService networkServiceImpl;

    private Handler uiHandler;

    public CoreService(Context rootContext) {
        this.rootContext = rootContext;
        this.networkServiceImpl = NetworkServiceFactory.create(true);
        this.uiHandler = new Handler(Looper.getMainLooper());
    }

    public void login(String login, String password, final CoreRequest<Boolean> coreRequest) {
        try {
            Validators.getPhoneValidator(rootContext)
                    .validate(login);

            Validators.getPasswordValidator(rootContext)
                    .validate(password);
        } catch (ValidationException e) {
            if (coreRequest != null) {
                coreRequest.processError(e.getMessage());
            }
            return;
        }
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

    public void registration(User user, final CoreRequest<Boolean> successOutput) {
        try {
            Validators.getPhoneValidator(rootContext)
                    .validate(user.getPhoneNumber());

            Validators.getPasswordValidator(rootContext)
                    .validate(user.getPassword());
        } catch (ValidationException e) {
            if (successOutput != null) {
                successOutput.processError(e.getMessage());
            }
            return;
        }

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
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                successOutput.processResult(true);
                            }
                        });
                    }
                }
            }
        });
    }

    public <T> CoreRequest<T> newRequest(CoreActivity activity){
        return new CoreRequest<T>(activity, this);
    }

}
