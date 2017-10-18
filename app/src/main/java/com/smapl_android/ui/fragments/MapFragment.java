package com.smapl_android.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentMapBinding;
import com.smapl_android.net.responses.TrackingResponse;
import com.smapl_android.services.GeolocationService;
import com.smapl_android.services.TrackingService;
import com.smapl_android.ui.base.BaseFragment;
import com.smapl_android.ui.base.OnPermissionsRequestListener;

import java.util.Collections;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MapFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMapBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        binding.setUser(getCoreActivity().getUserInfo());
        binding.setPresenter(new Presenter());

        return binding.getRoot();
    }


    public class Presenter{

        public void startGeolocationService() {

            LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch(Exception ex) {}



            if(!gps_enabled) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle(getContext().getResources().getString(R.string.app_name));
                dialog.setMessage(getContext().getResources().getString(R.string.gpsOffMessage));
                dialog.setPositiveButton(getContext().getResources().getString(R.string.enableGps), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub
                        Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getContext().startActivity(myIntent);
                        //get gps
                    }
                });
                dialog.setNegativeButton(getContext().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub

                    }
                });
                dialog.show();
            return;
            }
            getCoreActivity().runWithPermissions(new OnPermissionsRequestListener() {
                @Override
                public void onSuccess() {
                    CoreRequest<TrackingResponse> request = getCoreActivity().newWaitingRequest(new SuccessOutput<TrackingResponse>() {
                        @Override
                        public void onSuccess(TrackingResponse result) {
                            getCoreActivity().getUserInfo().inDrive.set(true);
                            getCoreActivity().getUserInfo().currentDrive.set(result.getTotalDistance());
                            getCoreActivity().getUserInfo().currentEarn.set(result.getTotalAmount());
                            getCoreActivity().startService(new Intent(getActivity(), GeolocationService.class));
                        }
                    });
                    getCoreService().startTracking(request);
                }

                @Override
                public void onFail() {

                }
            }, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }


        public void stopGeolocationService() {
            List<Pair<Double, Double>> lastLocations = TrackingService.getLastLocations();
            getCoreActivity().stopService(new Intent(getActivity(), GeolocationService.class));
            getCoreActivity().stopService(new Intent(getActivity(), TrackingService.class));
            CoreRequest<TrackingResponse> request = getCoreActivity().newWaitingRequest(new SuccessOutput<TrackingResponse>() {
                @Override
                public void onSuccess(TrackingResponse result) {
                    getCoreActivity().getUserInfo().inDrive.set(false);
                    getCoreActivity().getUserInfo().currentDrive.set(result.getTotalDistance());
                    getCoreActivity().getUserInfo().currentEarn.set(result.getTotalAmount());
                }
            });
            getCoreService().stopTracking(request, lastLocations);


        }
    }
}
