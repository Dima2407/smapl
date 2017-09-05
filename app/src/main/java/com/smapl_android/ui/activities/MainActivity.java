package com.smapl_android.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.smapl_android.SmaplApplication;
import com.smapl_android.services.GeolocationService;
import com.smapl_android.ui.base.CoreActivity;
import com.smapl_android.ui.fragments.LoginFragment;
import com.smapl_android.ui.fragments.MainScreenFragment;

public class MainActivity extends CoreActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment fragment = new MainScreenFragment();
        replaceContent(fragment);
    }


}
