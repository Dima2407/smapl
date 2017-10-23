package com.smapl_android.net.requests;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {

    @SerializedName("type")
    private String type;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("mobile_number")
    private String phoneNumber;
    @SerializedName("car_year")
    private int carYear;
    @SerializedName("car_mark")
    private String carMark;
    @SerializedName("car_model")
    private String carModel;
    @SerializedName("car_color")
    private String carColor;
    @SerializedName("gender")
    private String gender;
    @SerializedName("age")
    private String age;
    @SerializedName("interest")
    private String[] interests = new String[0];
    @SerializedName("app_auth_key")
    private String registrationId = "";
    @SerializedName("fb_token")
    private String fbToken;

    public RegistrationRequest() {
        this.type = "driver";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    public void setCarMark(String carMark) {
        this.carMark = carMark;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public void setInterests(String interests) {
        if (interests != null && interests.length() > 0) {
            this.interests = interests.replaceAll(",([^ ])", ";$1").split(";");
        } else {
            this.interests = new String[0];
        }
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }
}
