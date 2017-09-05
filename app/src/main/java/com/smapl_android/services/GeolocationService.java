package com.smapl_android.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class GeolocationService extends Service {
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Location startLocation;
    private Location currentLocation;
    private Location lastSentLocation;
    private int sendedRequestCounter;

    private static final double INCORRECT_VALUE = -1.23;
    private static final String START_LOCATION_LATITUDE  = "StartLocationLA";
    private static final String START_LOCATION_LONGITUDE = "StartLocationLNG";
    private static final String CURRENT_LOCATION_LATITUDE  = "CurrentLocationLA";
    private static final String CURRENT_LOCATION_LONGITUDE  = "CurrentLocationLNG";
    private static final String LAST_SENT_LOCATION_LATITUDE  = "LastSentLocationLA";
    private static final String LAST_SENT_LOCATION_LONGITUDE = "LastSentLocationLNG";
    private static final String DISTANCE_START_TO_CURRENT  = "distanceStartToCurrent";
    private static final String DISTANCE_LAST_TO_CURRENT  = "distanceLastToCurrent";
    private static final String SENDED_REQUESTS_COUNTER  = "sendedRequestCounter";
    private static final double DISTANCE_LIMIT = 0.5; // distance in meters

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        prepare();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }


    private void prepare() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        sendedRequestCounter = 0;
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    startLocation = currentLocation;
                    lastSentLocation = null;
                    System.out.println("Good Location Accuracy: " + location.getAccuracy());
                } else {
                    System.out.println("Bad Location");
                }
            }
        });

        createLocationRequest();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        final SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                System.out.println("Success task");
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
                System.out.println("Failure Task");
            }
        });


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                for (Location location : locationResult.getLocations()) {
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("Country:" + addresses.get(0).getCountryName());
                    System.out.println("Precision: " + location.getAccuracy());
                    currentLocation = location;
                }


                if (startLocation == null) {
                    System.out.println("Start Location is NULL");
                }

                System.out.println("Distance (Start, Current) : " + distance(startLocation, currentLocation));
                if (lastSentLocation == null) {
                    lastSentLocation = currentLocation;
                    System.out.println("Sending Request First Time");
                    //Send Request to Server
                }

                if (lastSentLocation != null)
                    System.out.println("Distance (Last, Current) : " + distance(lastSentLocation, currentLocation));
                if (lastSentLocation != null && distance(lastSentLocation, currentLocation) > DISTANCE_LIMIT) {
                    lastSentLocation = currentLocation;
                    sendedRequestCounter++;
                    System.out.println("Sending Request After First Time");
                    //Send Request to server
                }
                Intent intent = new Intent("GEO_UPDATES");
                intent.putExtra(START_LOCATION_LATITUDE, startLocation.getLatitude());
                intent.putExtra(START_LOCATION_LONGITUDE, startLocation.getLongitude());

                intent.putExtra(CURRENT_LOCATION_LATITUDE, currentLocation.getLatitude());
                intent.putExtra(CURRENT_LOCATION_LONGITUDE, currentLocation.getLongitude());

                if (lastSentLocation != null) {
                    intent.putExtra(LAST_SENT_LOCATION_LATITUDE, lastSentLocation.getLatitude());
                    intent.putExtra(LAST_SENT_LOCATION_LONGITUDE, lastSentLocation.getLongitude());
                }else{
                    intent.putExtra(LAST_SENT_LOCATION_LATITUDE, INCORRECT_VALUE);
                    intent.putExtra(LAST_SENT_LOCATION_LONGITUDE, INCORRECT_VALUE);
                }
                intent.putExtra(DISTANCE_START_TO_CURRENT, distance(startLocation, currentLocation));
                intent.putExtra(DISTANCE_LAST_TO_CURRENT, distance(lastSentLocation, currentLocation));
                intent.putExtra(SENDED_REQUESTS_COUNTER, sendedRequestCounter);
                sendBroadcast(intent);
            }
        };


        startLocationUpdates();
    }

    protected void createLocationRequest() {
        this.locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }


    private double distance(Location first, Location second) {
        if (first == null || second == null) return -1.0;
        double lat1 = first.getLatitude();
        double lng1 = first.getLongitude();
        double lat2 = second.getLatitude();
        double lng2 = second.getLongitude();
        double earthRadius = 6371; // in kilomoeters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;
        return dist * 1000; // output distance, in meters
    }
}