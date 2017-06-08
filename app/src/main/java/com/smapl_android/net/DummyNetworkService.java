package com.smapl_android.net;

import com.smapl_android.model.User;
import com.smapl_android.net.responses.LoginResponse;
import com.smapl_android.net.responses.RegistrationResponse;

class DummyNetworkService implements NetworkService{
    @Override
    public void login(final String login, String password, final OnResultCallback<LoginResponse, Throwable> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if("1111".equals(login)){
                        callback.onResult(new LoginResponse(), null);
                    }else {
                        callback.onResult(null, new Exception("hoho"));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void registration(User user, final OnResultCallback<RegistrationResponse, Throwable> callback) {
        final String phoneNumber = user.getPhoneNumber();
        final String password = user.getPassword();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if ("1111111111".equals(phoneNumber)){
                        callback.onResult(new RegistrationResponse(), null);
                    } else {
                        callback.onResult(null, new Exception("phoneNumber isn't equals 1111111111"));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
