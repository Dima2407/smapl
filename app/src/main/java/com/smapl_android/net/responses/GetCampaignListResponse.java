package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.io.StringReader;

public class GetCampaignListResponse extends ServerResponse<GetCampaignListResponse.Campaign[]> {

    public Campaign[] getCampaigns() {
        return getResult();
    }

    public static class Campaign implements Serializable {

        @SerializedName("name")
        private String name;
        @SerializedName("client_id")
        private int clientId;
        @SerializedName("status")
        private String status;
        @SerializedName("budget")
        private String budget;
        @SerializedName("car_class")
        private String carClass;
        @SerializedName("car_color")
        private String carColor;
        @SerializedName("gender")
        private String gender;
        @SerializedName("age")
        private String age;
        @SerializedName("sticker")
        private String[] stickers;
        @SerializedName("description")
        private String description;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("id")
        private int id;
        @SerializedName("logo")
        private String logo;

        public String getName() {
            return name;
        }

        public int getClientId() {
            return clientId;
        }

        public String getStatus() {
            return status;
        }

        public String getBudget() {
            return budget;
        }

        public String getCarClass() {
            return carClass;
        }

        public String getCarColor() {
            return carColor;
        }

        public String getGender() {
            return gender;
        }

        public String getAge() {
            return age;
        }

        public String[] getStickers() {
            return stickers;
        }

        public String getDescription() {
            return description;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public int getId() {
            return id;
        }

        public String getImage (){
            return logo;
        }
    }

    public static class Client{
        @SerializedName("email")
        private String email;
        @SerializedName("name")
        private String name;
        @SerializedName("mobile_number")
        private String mobileNumber;
        @SerializedName("car_year")
        private String carYear;
        @SerializedName("car_model")
        private String carModel;
        @SerializedName("car_color")
        private String carColor;
        @SerializedName("car_photo")
        private String carPhoto;
        @SerializedName("interest")
        private String interest;
        @SerializedName("type")
        private String type;
        @SerializedName("description")
        private String description;
        @SerializedName("user_representative")
        private String userRepresentative;
        @SerializedName("mileage")
        private String mileage;
        @SerializedName("amount")
        private String amount;
        @SerializedName("avatar")
        private String avatar;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("id")
        private int id;

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public String getCarYear() {
            return carYear;
        }

        public String getCarModel() {
            return carModel;
        }

        public String getCarColor() {
            return carColor;
        }

        public String getCarPhoto() {
            return carPhoto;
        }

        public String getInterest() {
            return interest;
        }

        public String getType() {
            return type;
        }

        public String getDescription() {
            return description;
        }

        public String getUserRepresentative() {
            return userRepresentative;
        }

        public String getMileage() {
            return mileage;
        }

        public String getAmount() {
            return amount;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public int getId() {
            return id;
        }
    }
}
