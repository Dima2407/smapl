package com.smapl_android.net.responses;


import com.google.gson.annotations.SerializedName;

public class LoginResponse extends ServerResponse<LoginResponse> {

    @SerializedName("id")
    private String id;

    @SerializedName("ttl")
    private Integer ttl;

    @SerializedName("created")
    private String created;

    @SerializedName("userId")
    private int userId;

    public String getId() {
        return getResult().id;
    }

    public Integer getTtl() {
        return getResult().ttl;
    }

    public String getCreated() {
        return getResult().created;
    }

    public int getUserId() {
        return getResult().userId;
    }

}


