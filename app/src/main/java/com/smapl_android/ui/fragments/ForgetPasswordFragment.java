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
import com.smapl_android.databinding.FragmentForgetPasswordBinding;
import com.smapl_android.model.PasswordForgetVM;
import com.smapl_android.ui.base.BaseFragment;

public class ForgetPasswordFragment extends BaseFragment {
    private PasswordForgetVM viewPassword = new PasswordForgetVM();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentForgetPasswordBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forget_password, container, false);
        binding.setPassword(viewPassword);
        binding.setPresenter(new Presenter());
        return binding.getRoot();
    }

    public class Presenter extends BasePresenter {

        public void sendRequest() {
            hideKeyboard();

            String email = viewPassword.email.get();
            try {
                final Validator<String> emailValidator = Validators.getEmailValidator(getContext());
                emailValidator.validate(email);
            } catch (ValidationException e) {
                showMessage(e.getMessage());
                return;
            }


            final CoreRequest<Boolean> request = getCoreActivity().newWaitingRequest(new SuccessOutput<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    if (result) {
                        getCoreActivity().showMessage(getString(R.string.app_name), getString(R.string.password_rest_send), new Runnable() {
                            @Override
                            public void run() {
                                onClickBack();
                            }
                        });
                    }else {
                        getCoreActivity().showMessage(getString(R.string.app_name), getString(R.string.password_rest_not_send));
                    }
                }
            });
            getCoreService().restorePassword(email, request);
        }

    }
}
