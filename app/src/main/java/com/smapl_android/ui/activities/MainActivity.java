package com.smapl_android.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.smapl_android.R;
import com.smapl_android.SmaplApplication;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.model.UserInfo;
import com.smapl_android.net.responses.UserResponse;
import com.smapl_android.services.GeolocationService;
import com.smapl_android.ui.base.CoreActivity;
import com.smapl_android.ui.fragments.LoginFragment;
import com.smapl_android.ui.fragments.MainScreenFragment;

public class MainActivity extends CoreActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final CoreRequest<UserResponse> request = newWaitingRequest(new SuccessOutput<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse result) {
                        userInfo.apply(getResources(), result);
                    }
                });

        getCoreService().getUser(request);
        Fragment fragment = new MainScreenFragment();
        replaceContent(fragment);
    }


}
