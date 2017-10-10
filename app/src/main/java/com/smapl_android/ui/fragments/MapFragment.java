package com.smapl_android.ui.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import com.smapl_android.ui.base.BaseFragment;
import com.smapl_android.ui.base.OnPermissionsRequestListener;

import java.util.Collections;

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
            getCoreActivity().runWithPermissions(new OnPermissionsRequestListener() {
                @Override
                public void onSuccess() {
                    CoreRequest<TrackingResponse> request = getCoreActivity().newWaitingRequest(new SuccessOutput<TrackingResponse>() {
                        @Override
                        public void onSuccess(TrackingResponse result) {
                            getCoreActivity().getUserInfo().inDrive.set(true);
                            getCoreActivity().getUserInfo().drive.set(result.getTotalDistance());
                            getCoreActivity().getUserInfo().earn.set(result.getTotalAmount());
                            getCoreActivity().startService(new Intent(getActivity(), GeolocationService.class));
                        }
                    });
                    getCoreService().startTracking(request, Collections.<Pair<Double,Double>>emptyList());
                }

                @Override
                public void onFail() {

                }
            }, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }


        public void stopGeolocationService() {
            getCoreActivity().getUserInfo().inDrive.set(false);

            getCoreActivity().stopService(new Intent(getActivity(), GeolocationService.class));
        }
    }
}
