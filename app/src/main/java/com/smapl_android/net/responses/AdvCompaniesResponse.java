package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

public class AdvCompaniesResponse {

    @SerializedName("companies")
    private Company[] companies;

    public Company[] getCompanies() {
        return companies;
    }

    public static class Service {

        @SerializedName("price")
        private String price;

        @SerializedName("description")
        private String description;

        public String getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class Sticker {

        @SerializedName("imageUrl")
        private String imageUrl;

        @SerializedName("title")
        private String title;

        public String getImageUrl() {
            return imageUrl;
        }

        public String getTitle() {
            return title;
        }
    }

    public static class Company {

        @SerializedName("company_Id")
        private int companyId;

        @SerializedName("title")
        private String title;

        @SerializedName("description")
        private String description;

        @SerializedName("service")
        private Service service;

        @SerializedName("stickers")
        private Sticker[] stickers;

        @SerializedName("reserv")
        private String reserv;

        public int getCompanyId() {
            return companyId;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public Service getService() {
            return service;
        }

        public Sticker[] getStickers() {
            return stickers;
        }

        public String getReserv() {
            return reserv;
        }
    }

}
