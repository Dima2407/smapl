package com.smapl_android.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dima on 06.06.17.
 */

public class User implements Parcelable {

    private Long id;
    private String phoneNumber;
    private String password;
    private String name;
    private String email;
    private boolean gender;
    private Age age;
    private String carBrand;
    private String carModel;
    private int carYearOfIssue;
    private String color;
    private String[] interests;
    private Bitmap carPhoto;

    public User() {
    }

    protected User(Parcel in) {
        phoneNumber = in.readString();
        password = in.readString();
        name = in.readString();
        email = in.readString();
        gender = in.readByte() != 0;
        carBrand = in.readString();
        carModel = in.readString();
        carYearOfIssue = in.readInt();
        color = in.readString();
        interests = in.createStringArray();
        carPhoto = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phoneNumber);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeByte((byte) (gender ? 1 : 0));
        dest.writeString(carBrand);
        dest.writeString(carModel);
        dest.writeInt(carYearOfIssue);
        dest.writeString(color);
        dest.writeStringArray(interests);
        dest.writeParcelable(carPhoto, flags);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarYearOfIssue() {
        return carYearOfIssue;
    }

    public void setCarYearOfIssue(int carYearOfIssue) {
        this.carYearOfIssue = carYearOfIssue;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String[] getInterests() {
        return interests;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    public Bitmap getCarPhoto() {
        return carPhoto;
    }

    public void setCarPhoto(Bitmap carPhoto) {
        this.carPhoto = carPhoto;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public enum Age{

        AGE_UP_TO_18(0),
        AGE_18_25(1),
        AGE_25_30(2),
        AGE_30_35(3),
        AGE_35_40(4),
        AGE_40_50(5),
        AGE_MORE_50(6);

        private int code;

        Age(int code){
            this.code = code;
        }
    }
}
