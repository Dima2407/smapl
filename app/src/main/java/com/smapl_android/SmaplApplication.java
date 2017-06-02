package com.smapl_android;

import android.app.Application;
import com.smapl_android.core.CoreService;

public class SmaplApplication extends Application {

    private CoreService coreService;

    @Override
    public void onCreate() {
        super.onCreate();
        coreService = new CoreService(this);
    }

    public CoreService getCoreService() {
        return coreService;
    }
}
