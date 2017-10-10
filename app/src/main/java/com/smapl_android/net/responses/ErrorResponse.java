package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse extends Exception{

    @SerializedName("code")
    protected int code;

    @SerializedName("success")
    protected boolean success;

    @SerializedName("messageError")
    protected String messageError;

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessageError() {
        return messageError;
    }

    @Override
    public String getMessage() {
        return getMessageError();
    }
}
