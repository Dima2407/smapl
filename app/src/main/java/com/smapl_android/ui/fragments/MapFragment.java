package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentMapBinding;
import com.smapl_android.model.User;
import com.smapl_android.model.UserInfo;
import com.smapl_android.net.responses.UserResponse;
import com.smapl_android.ui.base.BaseFragment;

public class MapFragment extends BaseFragment {

    private static final String TAG = MapFragment.class.getSimpleName();
    //private UserInfo user;

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

    }

    public class Presenter{

    }
}
