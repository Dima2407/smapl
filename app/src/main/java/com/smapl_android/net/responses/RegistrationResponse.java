package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {

    @SerializedName("token")
    private String token;

    @SerializedName("userId")
    private String userId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
