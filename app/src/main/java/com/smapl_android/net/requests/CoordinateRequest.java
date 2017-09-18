package com.smapl_android.net.requests;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoordinateRequest {

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @SerializedName("status")
    private String status;

    @SerializedName("coordinate")
    private List<Coordinate> coordinates = new ArrayList<>();


    public static CoordinateRequest start() {
        CoordinateRequest request = new CoordinateRequest();
        request.status = "start";
        return request;
    }

    public static CoordinateRequest stop() {
        CoordinateRequest request = new CoordinateRequest();
        request.status = "stop";
        return request;
    }


    public static CoordinateRequest inProgress() {
        CoordinateRequest request = new CoordinateRequest();
        request.status = "in_progress";
        return request;
    }

    public void addCoordinate(Location location) {
        addCoordinate(location.getLatitude(), location.getLongitude());
    }

    public void addCoordinate(double latitude, double longitude) {
        Coordinate c = new Coordinate();
        c.latitude = String.valueOf(latitude);
        c.longitude = String.valueOf(longitude);
        c.date = timeFormat.format(new Date());
        coordinates.add(c);
    }


    private static class Coordinate {
        @SerializedName("latitude")
        private String latitude;
        @SerializedName("longitude")
        private String longitude;
        @SerializedName("date")
        private String date;
    }
}
