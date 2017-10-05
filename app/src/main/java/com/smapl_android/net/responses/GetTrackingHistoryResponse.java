package com.smapl_android.net.responses;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GetTrackingHistoryResponse extends ServerResponse<GetTrackingHistoryResponse.TrackingHistory[]> {

    public TrackingHistory [] getHistory(){
        return getResult();
    }

    public static class TrackingHistory{

        private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'", Locale.US);
        @SerializedName("company_name")
        private String companyName;
        @SerializedName("total_amount")
        private double totalAmount;
        @SerializedName("total_distance")
        private double totalDistance;
        @SerializedName("started_at")
        private String startedAt;
        @SerializedName("ended_at")
        private String endedAt;

        public String getCompanyName() {
            return companyName;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public double getTotalDistance() {
            return totalDistance;
        }

        public Date getDate(){
            try {
                return DATE_FORMAT.parse(getStartedAt());
            } catch (ParseException e) {
                return new Date();
            }
        }

        public String getStartedAt() {
            return startedAt;
        }

        public String getEndedAt() {
            return endedAt;
        }
    }
}
