package com.smapl_android;

import android.app.Application;

import com.crashlytics.android.core.CrashlyticsCore;
import com.smapl_android.core.CoreService;
import com.crashlytics.android.Crashlytics;
import com.smapl_android.services.GeolocationService;

import io.fabric.sdk.android.Fabric;

import java.io.File;

public class SmaplApplication extends Application {

    private CoreService coreService;

    @Override
    public void onCreate() {
        super.onCreate();
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();
        Fabric.with(this, crashlyticsKit);
        coreService = new CoreService(this);
    }

    public CoreService getCoreService() {
        return coreService;
    }
}
