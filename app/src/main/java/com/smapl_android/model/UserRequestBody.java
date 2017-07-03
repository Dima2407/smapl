package com.smapl_android.model;

import com.google.gson.annotations.SerializedName;

public class UserRequestBody {

    @SerializedName("id")
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
