package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentProfileBinding;
import com.smapl_android.model.UserInfo;
import com.smapl_android.net.responses.UserResponse;
import com.smapl_android.ui.base.BaseFragment;

public class ProfileFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setUser(getUser());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_to_set_car_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCoreActivity().replaceContentWithHistory(new SetCarFragment());
            }
        });

        view.findViewById(R.id.btn_edit_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCoreActivity().replaceContentWithHistory(new ChangePasswordFragment());
            }
        });

        view.findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCoreService().logout();
                getCoreActivity().replaceContent(new LoginFragment());
            }
        });

        view.findViewById(R.id.btn_about_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCoreActivity().replaceContentWithHistory(new AboutMeFragment());
            }
        });
    }

    private UserInfo getUser(){
        final UserInfo userInfo = new UserInfo();
        final CoreRequest<UserResponse> request = getCoreService().newRequest(getCoreActivity());
        request.withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(new SuccessOutput<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse result) {
                        userInfo.apply(getResources(), result);
                    }
                });

        getCoreService().getUser(request);

        return userInfo;
    }
}
