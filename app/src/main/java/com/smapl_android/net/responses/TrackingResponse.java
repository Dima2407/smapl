package com.smapl_android.net.responses;


import com.google.gson.annotations.SerializedName;

public class TrackingResponse extends ServerResponse<TrackingResponse> {

    @SerializedName("total_amount")
    private double totalAmount;

    @SerializedName("total_distance")
    private double totalDistance;

    @SerializedName("status")
    private String status;

    public double getTotalAmount() {
        return getResult().totalAmount;
    }

    public double getTotalDistance() {
        return getResult().totalDistance;
    }

    public String getStatus() {
        return getResult().status;
    }
}
