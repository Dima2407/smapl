package com.smapl_android.core;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Patterns;

import com.smapl_android.R;
import com.smapl_android.models.User;
import com.smapl_android.net.NetworkService;
import com.smapl_android.net.NetworkServiceFactory;
import com.smapl_android.net.responses.LoginResponse;
import com.smapl_android.net.responses.RegistrationResponse;

import java.util.IllegalFormatCodePointException;
import java.util.regex.Pattern;

public class CoreService {

    private static final int MIN_PHONE_NUMBER_LENGHT = 10;
    private static final int MIN_PASSWORD_LENGHT = 4;

    private final Context rootContext;

    private final NetworkService networkServiceImpl;

    private Handler uiHandler;

    public CoreService(Context rootContext) {
        this.rootContext = rootContext;
        this.networkServiceImpl = NetworkServiceFactory.create(true);
        this.uiHandler = new Handler(Looper.getMainLooper());
    }

    public void login(String login, String password, final Callback<Boolean, String> callback) {
        if (TextUtils.isEmpty(login)) {

            if (callback != null) {
                callback.onError(rootContext.getString(R.string.error_empty_login));
            }
            return;
        }
        if (TextUtils.isEmpty(password)) {

            if (callback != null) {
                callback.onError(rootContext.getString(R.string.error_empty_password));
            }
            return;
        }
        networkServiceImpl.login(login, password, new NetworkService.OnResultCallback<LoginResponse, Throwable>() {
            @Override
            public void onResult(LoginResponse result, final Throwable error) {
                if (callback != null) {
                    if(error != null){
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onError(error.getMessage());
                            }
                        });

                    }else {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(true);
                            }
                        });
                    }
                }
            }
        });
    }

    public void registration(User user, final Callback<Boolean, String> callback){
        if (TextUtils.isEmpty(user.getPhoneNumber())){

            if (callback != null){
                callback.onError(rootContext.getString(R.string.error_empty_phone_number));
            }
            return;
        }
        if (TextUtils.isEmpty(user.getPassword())){

            if (callback != null){
                callback.onError(rootContext.getString(R.string.error_empty_password));
            }
            return;
        }

        if (user.getPhoneNumber().length() < MIN_PHONE_NUMBER_LENGHT){
            if (callback != null)
                callback.onError(rootContext.getString(R.string.error_phone_number_short));
            return;
        }

        if (!Patterns.PHONE.matcher(user.getPhoneNumber()).matches()){
            if (callback != null)
                callback.onError(rootContext.getString(R.string.error_wrong_phone_number));
            return;
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGHT){
            if (callback != null)
                callback.onError(rootContext.getString(R.string.error_password_short));
            return;
        }

        networkServiceImpl.registration(user, new NetworkService.OnResultCallback<RegistrationResponse, Throwable>() {
            @Override
            public void onResult(RegistrationResponse result, final Throwable error) {
                if (callback != null){
                    if (error != null){
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onError(error.getMessage());
                            }
                        });
                    } else {
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(true);
                            }
                        });
                    }
                }
            }
        });
    }



    public interface Callback<T, E> {
        void onError(E error);

        void onSuccess(T result);
    }

}
