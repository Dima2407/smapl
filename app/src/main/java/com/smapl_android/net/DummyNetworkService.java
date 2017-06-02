package com.smapl_android.net;

import com.smapl_android.net.responses.LoginResponse;

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
}
