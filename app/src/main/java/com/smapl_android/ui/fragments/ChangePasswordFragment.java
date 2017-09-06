package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.databinding.FragmentChangePasswordBinding;
import com.smapl_android.model.PasswordEditVM;
import com.smapl_android.ui.base.BaseFragment;

public class ChangePasswordFragment extends BaseFragment {
    private PasswordEditVM viewPassword = new PasswordEditVM();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentChangePasswordBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false);
        binding.setUser(getCoreActivity().getUserInfo());
        binding.setPassword(viewPassword);
        binding.setPresenter(new Presenter());
        return binding.getRoot();
    }

    private void changePassword() {

        String oldPasswordStr = viewPassword.oldPassword.get();
        String newPasswordStr = viewPassword.newPassword.get();
        try {
            final Validator<String> passwordValidator = Validators.getPasswordValidator(getContext());
            passwordValidator.validate(oldPasswordStr);
            passwordValidator.validate(newPasswordStr);
        } catch (ValidationException e) {
            showMessage(e.getMessage());
            return;
        }

        final CoreRequest<Boolean> request = getCoreActivity().newWaitingRequest(new SuccessOutput<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    showMessage(getString(R.string.password_changed), new Runnable() {
                        @Override
                        public void run() {
                            getCoreActivity().onBackPressed();
                        }
                    });
                }
            }
        });
        getCoreService().changePassword(oldPasswordStr, newPasswordStr, request);
    }

    public class Presenter {

        public void onClickForward() {
            hideKeyboard();
            changePassword();
        }

        public void onClickBack() {
            getCoreActivity().onBackPressed();
        }

    }
}
