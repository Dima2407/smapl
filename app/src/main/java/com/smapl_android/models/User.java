package com.smapl_android.models;

/**
 * Created by dima on 06.06.17.
 */

public class User {

    private Long id;
    private String phoneNumber;
    private String password;
    private String name;
    private boolean gender;
    private Age age;
    private String carBrand;
    private String carModel;
    private int carYearOfIssue;
    private String color;
    private String[] interests;

    public User() {
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
