package com.smapl_android.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.StringBuilderPrinter;

public class SessionStorage {

    private static final String FILE_NAME = "_session_storage";

    private static final String AUTH_KEY = "_auth_key";
    private static final String USER_ID = "_user_id";

    private SharedPreferences preferences;

    public SessionStorage(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(getAuthKey());
    }

    public void logout() {
        preferences.edit().clear().apply();
    }

    public void saveAuthKey(String authKey) {
        preferences.edit().putString(AUTH_KEY, authKey).apply();
    }

    public void saveUserId(int userId){
        preferences.edit().putInt(USER_ID, userId).apply();
    }

    public String getAuthKey() {
        return preferences.getString(AUTH_KEY, null);
    }

    public int getUserId() {
        return preferences.getInt(USER_ID, -1);
    }
}
