package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;


public class EditCarResponse {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}
