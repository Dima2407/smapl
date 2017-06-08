package com.smapl_android.net;

import com.smapl_android.model.User;
import com.smapl_android.net.responses.LoginResponse;
import com.smapl_android.net.responses.RegistrationResponse;

public interface NetworkService {

    void login(String login, String password, final NetworkServiceImpl.OnResultCallback<LoginResponse, Throwable> callback);

    void registration(User user, final NetworkService.OnResultCallback<RegistrationResponse, Throwable> callback);


    interface OnResultCallback<T, E> {
        void onResult(T result, E error);
    }

}
