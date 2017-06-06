package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {

    @SerializedName("token")
    private String token;

    @SerializedName("userId")
    private String userId;
}
