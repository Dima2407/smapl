package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

public class GetNewsResponse {

    @SerializedName("news")
    private News[] news;

    public News[] getNews() {
        return news;
    }

    public void setNews(News[] news) {
        this.news = news;
    }

    public static class News {

        @SerializedName("title")
        private String title;

        @SerializedName("company")
        private String company;

        @SerializedName("image")
        private String image;

        @SerializedName("text")
        private String text;

        @SerializedName("company_id")
        private String companyId;

        @SerializedName("news_id")
        private String newsId;

        public String getTitle() {
            return title;
        }

        public String getCompany() {
            return company;
        }

        public String getImage() {
            return image;
        }

        public String getText() {
            return text;
        }

        public String getCompanyId() {
            return companyId;
        }

        public String getNewsId() {
            return newsId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public void setNewsId(String newsId) {
            this.newsId = newsId;
        }
    }
}
