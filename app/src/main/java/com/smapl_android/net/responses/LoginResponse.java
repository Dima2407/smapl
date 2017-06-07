package com.smapl_android.net.responses;


import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("auth_key")
    private String authKey;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
