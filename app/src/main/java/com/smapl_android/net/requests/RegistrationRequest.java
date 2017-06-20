package com.smapl_android.net.requests;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {

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

    @SerializedName("car_photo")
    private Bitmap carPhoto;


    public RegistrationRequest(String email, String password, String name, String phoneNumber, int carYear, String carMark, String carModel, String carColor, Bitmap carPhoto) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.carYear = carYear;
        this.carMark = carMark;
        this.carModel = carModel;
        this.carColor = carColor;
        this.carPhoto = carPhoto;
    }

    public RegistrationRequest(String email, String password, String name, String phoneNumber, int carYear, String carMark, String carModel, String carColor) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.carYear = carYear;
        this.carMark = carMark;
        this.carModel = carModel;
        this.carColor = carColor;
    }

    public String getEmail() {
        return email;
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

    public String getName() {
        return name;
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

    public int getCarYear() {
        return carYear;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    public String getCarMark() {
        return carMark;
    }

    public void setCarMark(String carMark) {
        this.carMark = carMark;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Bitmap getCarPhoto() {
        return carPhoto;
    }

    public void setCarPhoto(Bitmap carPhoto) {
        this.carPhoto = carPhoto;
    }
}
