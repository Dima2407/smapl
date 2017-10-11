package com.smapl_android.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.smapl_android.core.UploadService;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.ui.base.CoreActivity;
import com.smapl_android.ui.fragments.CampaignListFragment;
import com.smapl_android.ui.fragments.LoadCarPhotoFragment;

public class DebugActivity extends CoreActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Fragment fragment = LoadCarPhotoFragment.create(new UserInfoViewModel());
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(android.R.id.content, fragment, LoadCarPhotoFragment.TAG)
                .commit();*/
        UploadService.uploadCarPhoto(this, "/storage/sdcard0/VK/Photos/1479760777525.jpg");
    }
}
