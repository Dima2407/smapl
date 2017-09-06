package com.smapl_android.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentChangePasswordBinding;
import com.smapl_android.model.UserViewPassword;
import com.smapl_android.net.responses.UserResponse;
import com.smapl_android.ui.base.BaseFragment;

public class ChangePasswordFragment extends BaseFragment {

    private static final String TAG = ChangePasswordFragment.class.getSimpleName();
    private UserViewPassword user = new UserViewPassword();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentChangePasswordBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false);
        binding.setUser(user);
        binding.setPresenter(new Presenter());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final CoreRequest<UserResponse> request = getCoreService()
                .newRequest(getCoreActivity());
        request
                .withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(new SuccessOutput<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse result) {
                        user.apply(result);
                    }
                });
        getCoreService()
                .getUser(request);
    }

    private void changePassword() {

        String oldPasswordStr = user.oldPassword.get();
        String newPasswordStr = user.newPassword.get();

        if (!TextUtils.isEmpty(oldPasswordStr) && !TextUtils.isEmpty(newPasswordStr)) {

            final CoreRequest<Boolean> request = getCoreService().newRequest(getCoreActivity());

            request
                    .withLoading(R.string.wait_login)
                    .handleErrorAsDialog()
                    .handleSuccess(new SuccessOutput<Boolean>() {
                        @Override
                        public void onSuccess(Boolean result) {
                            if (result) {
                                showMessage(getString(R.string.app_name), getString(R.string.password_changed));
                                getActivity().onBackPressed();
                            }
                        }
                    });
            getCoreService().changePassword(oldPasswordStr, newPasswordStr, request);
        } else {
            showMessage(getString(R.string.app_name), getString(R.string.error_empty_password));
        }

    }

    public class Presenter {

        public void onClickForward() {
            hideKeyboard();
            changePassword();
        }

        public void onClickBack() {
            getActivity().onBackPressed();
        }

    }
}
