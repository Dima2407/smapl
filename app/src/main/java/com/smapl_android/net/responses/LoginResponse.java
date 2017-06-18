package com.smapl_android.net.responses;


import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("result")
    private Result result;

    public static class Result {

        @SerializedName("id")
        private String id;

        @SerializedName("ttl")
        private Integer ttl;

        @SerializedName("created")
        private String created;

        @SerializedName("userId")
        private int userId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getTtl() {
            return ttl;
        }

        public void setTtl(Integer ttl) {
            this.ttl = ttl;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}


