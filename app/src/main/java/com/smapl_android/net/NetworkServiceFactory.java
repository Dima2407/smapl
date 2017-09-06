package com.smapl_android.net;

public final class NetworkServiceFactory {
    private NetworkServiceFactory() {
    }

    public static NetworkService create(boolean stub){
        if(stub){
            return null;//new DummyNetworkService();
        }else {
            return new NetworkServiceImpl();
        }
    }
}
