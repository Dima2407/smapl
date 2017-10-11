package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetCampaignListResponse extends ServerResponse<GetCampaignListResponse.Campaign[]> {

    public Campaign[] getCampaigns() {
        return getResult();
    }

    public static class Campaign implements Serializable {

        @SerializedName("name")
        private String name;
        @SerializedName("price")
        private int price;
        @SerializedName("sticker")
        private List<String> stickers;
        @SerializedName("description")
        private String description;
        @SerializedName("description_short")
        private String descriptionShort;
        @SerializedName("id")
        private int id;
        @SerializedName("logo")
        private String logo;

        public String getName() {
            return name;
        }

        public List<String> getStickers() {
            if(stickers == null){
                return Collections.emptyList();
            }
            return stickers;
        }

        public String getDescription(boolean isShort) {
            if(isShort){
                return descriptionShort;
            }
            return description;
        }

        public int getId() {
            return id;
        }

        public String getImage (){
            return logo;
        }

        public int getPrice(){
            return price;
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
