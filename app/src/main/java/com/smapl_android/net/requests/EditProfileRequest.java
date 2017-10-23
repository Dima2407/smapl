package com.smapl_android.net.requests;

import com.google.gson.annotations.SerializedName;

public class EditProfileRequest {

    @SerializedName("name")
    private String name;

    @SerializedName("mobile_number")
    private String phone;

    @SerializedName("age")
    private String age;

    @SerializedName("gender")
    private String gender;

    @SerializedName("interest")
    private String[] interests= new String[0];

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setInterests(String interests) {
        if (interests != null && interests.length() > 0) {
            this.interests = interests.replaceAll(",([^ ])", ";$1").split(";");
        } else {
            this.interests = new String[0];
        }
    }
}
