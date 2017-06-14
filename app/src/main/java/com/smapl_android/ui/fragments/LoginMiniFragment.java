package com.smapl_android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;

public class LoginMiniFragment extends BaseFragment {

    private EditText login;
    private EditText password;
    private ImageView imgLogin;
    private ImageView imgPassword;

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

        imgLogin = (ImageView) view.findViewById(R.id.img_login_chek_mark);
        imgPassword = (ImageView) view.findViewById(R.id.img_password_check_mark);

        imgLogin.setImageResource(R.drawable.check_mark);
        imgPassword.setImageResource(R.drawable.transparent_shape);



        view.findViewById(R.id.btn_login_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hanleLogin();
            }
        });

        login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imgLogin.setImageResource(R.drawable.check_mark);
                } else
                    imgLogin.setImageResource(R.drawable.transparent_shape);
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imgPassword.setImageResource(R.drawable.check_mark);
                    imgLogin.setImageResource(R.drawable.transparent_shape);
                } else
                    imgPassword.setImageResource(R.drawable.transparent_shape);
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
