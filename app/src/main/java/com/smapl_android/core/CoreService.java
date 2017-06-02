package com.smapl_android.core;

import android.content.Context;
import android.text.TextUtils;
import com.smapl_android.R;
import com.smapl_android.net.NetworkService;
import com.smapl_android.net.NetworkServiceFactory;
import com.smapl_android.net.responses.LoginResponse;

public class CoreService {

    private final Context rootContext;

    private final NetworkService networkServiceImpl;

    public CoreService(Context rootContext) {
        this.rootContext = rootContext;
        this.networkServiceImpl = NetworkServiceFactory.create(true);
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
            public void onResult(LoginResponse result, Throwable error) {
                if (callback != null) {
                    if(error != null){
                        callback.onError(error.getMessage());
                    }else {
                        callback.onSuccess(true);
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