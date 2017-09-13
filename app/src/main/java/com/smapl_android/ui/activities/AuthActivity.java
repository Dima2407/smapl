package com.smapl_android.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.smapl_android.R;
import com.smapl_android.ui.base.CoreActivity;
import com.smapl_android.ui.fragments.AboutYourselfFragment;
import com.smapl_android.ui.fragments.LoadCarPhotoFragment;
import com.smapl_android.ui.fragments.LoginFragment;

public class AuthActivity extends CoreActivity {

    public static final String LOGOUT_ACTION = "com.smapl_android.intent.action.LOGOUT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(LOGOUT_ACTION.equalsIgnoreCase(getIntent().getAction())){
            showLogin();
        }else {
            setContentView(R.layout.activity_splash);

            final Handler splashHandler = new Handler();
            splashHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean loggedIn = getCoreService().isLoggedIn();
                    if (loggedIn) {
                        openMainActivity();
                    } else {
                        showLogin();
                    }
                }
            }, 1000);
        }
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showLogin() {
        Fragment fragment = new LoginFragment();
        replaceContent(fragment);
    }
}
