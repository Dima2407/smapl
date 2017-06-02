package com.smapl_android.net;

import com.smapl_android.net.responses.LoginResponse;

public interface NetworkService {

    void login(String login, String password, final NetworkServiceImpl.OnResultCallback<LoginResponse, Throwable> callback);

    interface OnResultCallback<T, E> {
        void onResult(T result, E error);
    }

}
