package com.smapl_android.net.requests;

import com.google.gson.annotations.SerializedName;

public class FbLoginRequest {

    @SerializedName("fb_token")
    private String fbToken;

    public FbLoginRequest(String fbToken) {
        this.fbToken = fbToken;
    }

    public String getFbToken() {
        return fbToken;
    }
}
