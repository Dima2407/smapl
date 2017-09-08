package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponse extends ServerResponse<RegistrationResponse> {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("mobile_number")
    private String mobileNumber;

    @SerializedName("car_year")
    private Integer carYear;

    @SerializedName("car_mark")
    private String carMark;

    @SerializedName("car_model")
    private String carModel;

    @SerializedName("car_color")
    private String carColor;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public String getFirstName() {
        return getResult().firstName;
    }

    public String getLastName() {
        return getResult().lastName;
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

    public String getCreatedAt() {
        return getResult().createdAt;
    }

    public String getUpdatedAt() {
        return getResult().updatedAt;
    }
}
