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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Handler splashHandler = new Handler();
        splashHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
               // boolean loggedIn = getCoreService().isLoggedIn();
                boolean loggedIn = true;
                if (loggedIn) {
                    openMainActivity();
                } else {
                    showLogin();
                }
            }
        }, 1000);
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showLogin() {
        Fragment fragment = new LoginFragment();
        replaceContentWithTag(fragment, LoginFragment.TAG);
    }

    @Override
    public void onBackPressed() {

        final AboutYourselfFragment aboutFragment = (AboutYourselfFragment) getSupportFragmentManager().findFragmentByTag(AboutYourselfFragment.TAG);
        final LoadCarPhotoFragment loadCarPhotoFragment = (LoadCarPhotoFragment) getSupportFragmentManager().findFragmentByTag(LoadCarPhotoFragment.TAG);

        if ((aboutFragment == null || (aboutFragment != null && !aboutFragment.isVisible()))
                && (loadCarPhotoFragment == null || (loadCarPhotoFragment != null && !loadCarPhotoFragment.isVisible()))) {

            finish();

        } else
            super.onBackPressed();
    }
}
