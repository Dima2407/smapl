package com.smapl_android.ui.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapl_android.R;
import com.smapl_android.databinding.FragmentMapBinding;
import com.smapl_android.services.GeolocationService;
import com.smapl_android.ui.base.BaseFragment;

public class MapFragment extends BaseFragment {

    private static final String TAG = MapFragment.class.getSimpleName();

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
            getCoreActivity().getUserInfo().inDrive.set(true);
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
                return;
            }
            getCoreActivity().startService(new Intent(getActivity(), GeolocationService.class));
        }


        public void stopGeolocationService() {
            getCoreActivity().getUserInfo().inDrive.set(false);

            getCoreActivity().stopService(new Intent(getActivity(), GeolocationService.class));
        }
    }
}
