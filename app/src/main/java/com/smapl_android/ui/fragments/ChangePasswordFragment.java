package com.smapl_android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.ui.base.BaseFragment;

public class ChangePasswordFragment extends BaseFragment {

    private static final String TAG = ChangePasswordFragment.class.getSimpleName();
    private EditText oldPassword;
    private EditText newPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        oldPassword = (EditText) view.findViewById(R.id.edit_change_password_old);
        newPassword = (EditText) view.findViewById(R.id.edit_change_password_new);

        view.findViewById(R.id.btn_change_password_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        view.findViewById(R.id.btn_change_password_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void changePassword() {

        String oldPasswordStr = oldPassword.getText().toString();
        String newPasswordStr = newPassword.getText().toString();

        if (!TextUtils.isEmpty(oldPasswordStr) && !TextUtils.isEmpty(newPasswordStr)){

            final CoreRequest<Boolean> request = getCoreService().newRequest(getCoreActivity());

            request
                    .withLoading(R.string.wait_login)
                    .handleErrorAsDialog()
                    .handleSuccess(new SuccessOutput<Boolean>() {
                        @Override
                        public void onSuccess(Boolean result) {
                            if(result){
                                getActivity().onBackPressed();
                            }
                        }
                    });
            getCoreService().changePassword(oldPasswordStr, newPasswordStr, request);
        }
    }
}
