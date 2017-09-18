package com.smapl_android.net.responses;


import com.google.gson.annotations.SerializedName;

public class TrackingResponse extends ServerResponse<TrackingResponse> {

    @SerializedName("total_amount")
    private int totalAmount;

    @SerializedName("total_distance")
    private int totalDistance;

    @SerializedName("status")
    private String status;

    public int getTotalAmount() {
        return getResult().totalAmount;
    }

    public int getTotalDistance() {
        return getResult().totalDistance;
    }

    public String getStatus() {
        return getResult().status;
    }
}
