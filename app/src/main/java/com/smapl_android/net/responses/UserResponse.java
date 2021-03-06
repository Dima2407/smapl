package com.smapl_android.net.responses;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class UserResponse extends ServerResponse<UserResponse>{

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile_number")
    private String mobileNumber;

    @SerializedName("age")
    private String age;

    @SerializedName("car_year")
    private Integer carYear;

    @SerializedName("car_mark")
    private String carMark;

    @SerializedName("car_model")
    private String carModel;

    @SerializedName("car_color")
    private String carColor;

    @SerializedName("car_photo")
    private String carPhoto;

    @SerializedName("interest")
    private String[] interests;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("gender")
    private String gender;

    @SerializedName("total_amount")
    private Double totalAmount;

    @SerializedName("balance")
    private Double balance;

    @SerializedName("total_distance")
    private Double totalDistance;

    @SerializedName("campaign_id")
    private Integer campaignId;

    public String getName() {
        return getResult().name;
    }

    public String getEmail() {
        return getResult().email;
    }

    public String getMobileNumber() {
        return getResult().mobileNumber;
    }

    public Integer getCarYear() {
        return getResult().carYear;
    }

    public String getCarMark() {
        return getResult().carMark;
    }

    public String getCarModel() {
        return getResult().carModel;
    }

    public String getCarColor() {
        return getResult().carColor;
    }

    public String getInterests() {
        return TextUtils.join(",", getResult().interests);
    }

    public String getAge() {
        return getResult().age;
    }

    public String getCarPhoto() {
        return getResult().carPhoto;
    }

    public String getGender() {
        return getResult().gender;
    }

    public double getTotalAmount() {
        Double totalAmount= getResult().totalAmount;
        return totalAmount != null ? totalAmount : 0;
    }

    public double getTotalDistance() {
        Double totalDistance = getResult().totalDistance;
        return totalDistance != null ? totalDistance : 0;
    }

    public double getBalance() {
        Double balance = getResult().balance;
        return balance != null ? balance : 0;
    }

    public int getCampaignId() {
        Integer id = getResult().campaignId;
        return id != null ? id : 0;
    }
}
