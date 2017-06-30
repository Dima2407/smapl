package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;


public class EditProfileResponse {

    @SerializedName("count")
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
