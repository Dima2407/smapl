package com.smapl_android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapl_android.R;
import com.smapl_android.ui.base.BaseFragment;

public class ProfileFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
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
    }
}
