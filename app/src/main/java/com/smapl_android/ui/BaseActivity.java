package com.smapl_android.ui;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import com.smapl_android.SmaplApplication;
import com.smapl_android.core.CoreService;

public abstract class BaseActivity extends AppCompatActivity {

    protected CoreService getCoreService(){
        final SmaplApplication application = (SmaplApplication) getApplication();
        return application.getCoreService();
    }
}
