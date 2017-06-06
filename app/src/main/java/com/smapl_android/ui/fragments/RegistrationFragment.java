package com.smapl_android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.smapl_android.R;
import com.smapl_android.core.CoreService;


/**
 * Created by dima on 02.06.17.
 */

public class RegistrationFragment extends BaseFragment {

    private EditText phoneNumberEditText;
    private EditText passwordEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phoneNumberEditText = (EditText) view.findViewById(R.id.edit_phone_number);
        passwordEditText = (EditText) view.findViewById(R.id.edit_password);
        view.findViewById(R.id.btn_registration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegistration();
            }
        });
    }

    private void handleRegistration() {
        String phoneNamber = phoneNumberEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        showProgress(getString(R.string.app_name), getString(R.string.wait_login));
        getCoreService().registration(phoneNamber, password, new CoreService.Callback<Boolean>() {
            @Override
            public void onError(String error) {
                hideProgress();
                showMessage(getString(R.string.app_name), error);
            }

            @Override
            public void onSuccess(Boolean result) {
                hideProgress();
            }
        });

    }
}
