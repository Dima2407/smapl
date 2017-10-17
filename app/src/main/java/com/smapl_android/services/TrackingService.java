package com.smapl_android.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

import com.smapl_android.SmaplApplication;
import com.smapl_android.core.CoreService;

import java.util.ArrayList;
import java.util.List;


public class TrackingService extends Service {

    private static final String EXTRA_LATITUDE = "_lat";
    private static final String EXTRA_LONGITUDE = "_long";

    private static final List<Pair<Double, Double>> locations = new ArrayList<>();
    static final String TAG = TrackingService.class.getSimpleName();

    public static void submitLocation(Context context, Location location){
        Intent intent = new Intent(context, TrackingService.class);
        intent.putExtra(EXTRA_LATITUDE, location.getLatitude());
        intent.putExtra(EXTRA_LONGITUDE, location.getLongitude());
        context.startService(intent);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        double lat = intent.getDoubleExtra(EXTRA_LATITUDE, 0);
        double lon = intent.getDoubleExtra(EXTRA_LONGITUDE, 0);
        processLocation(lat, lon);
        return super.onStartCommand(intent, flags, startId);
    }

    private void processLocation(double lat, double lon) {
        Log.d(TAG, "processLocation: " + lat + " " + lon);
        locations.add(new Pair<>(lat, lon));
        if(locations.size() > 3){
            getCoreService().updateTracking(new ArrayList<>(locations));
            locations.clear();
        }
    }

    public CoreService getCoreService() {
        final SmaplApplication application = (SmaplApplication) getApplication();
        return application.getCoreService();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locations.clear();
    }

    public static List<Pair<Double, Double>> getLastLocations(){
        return new ArrayList<>(locations);
    }
}
