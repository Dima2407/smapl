package com.smapl_android.ui.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentMapBinding;
import com.smapl_android.model.User;
import com.smapl_android.model.UserInfo;
import com.smapl_android.net.responses.UserResponse;
import com.smapl_android.services.GeolocationService;
import com.smapl_android.ui.activities.AuthActivity;
import com.smapl_android.ui.activities.MainActivity;
import com.smapl_android.ui.base.BaseFragment;
import com.smapl_android.ui.base.CoreActivity;

public class MapFragment extends BaseFragment {

    private static final String TAG = MapFragment.class.getSimpleName();

    private Button startButton, stopButton;
    private RelativeLayout relativeLayout1,relativeLayout2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMapBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        binding.setUser(getUser());
        binding.setPresenter(new Presenter());

        return binding.getRoot();
    }

    private UserInfo getUser(){
        final UserInfo userInfo = new UserInfo();
        final CoreRequest<UserResponse> request = getCoreService().newRequest(getCoreActivity());
        request.withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(new SuccessOutput<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse result) {
                        userInfo.name.set(result.getName());
                        userInfo.carBrand.set(result.getCarMark());
                        userInfo.carModel.set(result.getCarModel());
                        userInfo.carColor.set(result.getCarColor());
                        userInfo.carYear.set(result.getCarYear() != null ? result.getCarYear().toString() : null);
                    }
                });

        getCoreService().getUser(request);

        return userInfo;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startButton = (Button) getView().findViewById(R.id.btn_map_start);
        stopButton = (Button) getView().findViewById(R.id.btn_map_stop);
        relativeLayout1 = (RelativeLayout)getView().findViewById(R.id.relativeLayout);
        relativeLayout2 = (RelativeLayout)getView().findViewById(R.id.relativeLayout2);

        relativeLayout1.setVisibility(View.GONE);
        relativeLayout2.setVisibility(View.GONE);
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
    }

    public class Presenter{
        public void startGeolocationService() {
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
                return;
            }
            (getActivity()).startService(new Intent(getActivity(), GeolocationService.class));

            startButton.setVisibility(View.GONE);
            stopButton.setVisibility(View.VISIBLE);
            relativeLayout1.setVisibility(View.VISIBLE);
            relativeLayout2.setVisibility(View.VISIBLE);
        }


        public void stopGeolocationService() {

            (getActivity()).stopService(new Intent(getActivity(), GeolocationService.class));
            stopButton.setVisibility(View.GONE);
            relativeLayout1.setVisibility(View.GONE);
            relativeLayout2.setVisibility(View.GONE);
            startButton.setVisibility(View.VISIBLE);
        }
    }
}
