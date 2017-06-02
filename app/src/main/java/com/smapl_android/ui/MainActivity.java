package com.smapl_android.ui;

import android.os.Bundle;
import com.smapl_android.R;
import com.smapl_android.ui.fragments.LoginFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginFragment fragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, fragment)
                .commit();
    }
}
