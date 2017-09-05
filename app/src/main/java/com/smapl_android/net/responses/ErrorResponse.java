package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("error")
    private Error error;

    public Error getError() {
        return error;
    }

    public String getErrorMessage(){
        return getError().getMessage();
    }

    public static class Error {
        @SerializedName("statusCode")
        private int statusCode;

        @SerializedName("name")
        private String name;

        @SerializedName("message")
        private String message;


        public int getStatusCode() {
            return statusCode;
        }

        public String getName() {
            return name;
        }

        public String getMessage() {
            return message;
        }
    }
}
