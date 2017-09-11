package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

public class ServerResponse<T> {

    @SerializedName("code")
    protected int code;

    @SerializedName("success")
    protected boolean success;

    @SerializedName("messageError")
    protected String messageError;

    @SerializedName("result")
    protected T result;

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessageError() {
        return messageError;
    }

    public T getResult() {
        return result;
    }
}
