package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

public class RestorePasswordResponse {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
