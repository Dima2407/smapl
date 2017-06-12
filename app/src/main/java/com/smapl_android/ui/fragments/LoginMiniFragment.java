package com.smapl_android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;

public class LoginMiniFragment extends BaseFragment {

    private EditText login;
    private EditText password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mini_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        login = (EditText) view.findViewById(R.id.edit_login);
        password = (EditText) view.findViewById(R.id.edit_password);

        view.findViewById(R.id.btn_login_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hanleLogin();
            }
        });

    }

    private void hanleLogin() {
        final CoreRequest<Boolean> request = getCoreService()
                .newRequest(getCoreActivity());
        request
                .withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(new SuccessOutput<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {

                    }
                });
        getCoreService()
                .login(login.getText().toString(), password.getText().toString(), request);
    }
}
