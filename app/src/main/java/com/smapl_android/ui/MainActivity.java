package com.smapl_android.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.smapl_android.ui.fragments.LoginFragment;
import com.smapl_android.ui.fragments.MainScreenFragment;

public class MainActivity extends CoreActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean loggedIn = getCoreService().isLoggedIn();
        Fragment fragment;
        if(loggedIn){
            fragment = new MainScreenFragment();
        }else {
            fragment = new LoginFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, fragment)
                .commit();
    }
}
