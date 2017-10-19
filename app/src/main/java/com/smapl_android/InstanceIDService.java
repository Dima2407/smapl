package com.smapl_android;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by user on 19.10.17.
 */

public class InstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseInstanceIDSer";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("registration_id",refreshedToken);
        editor.commit();
    }
}
