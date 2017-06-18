package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

public class UpdateCarResponse {

    @SerializedName("result")
    private boolean result;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
