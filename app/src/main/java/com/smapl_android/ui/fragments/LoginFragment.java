package com.smapl_android.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.databinding.FragmentLoginBinding;
import com.smapl_android.model.AuthVM;
import com.smapl_android.ui.activities.MainActivity;
import com.smapl_android.ui.base.BaseFragment;


public class LoginFragment extends BaseFragment {

    public static final String TAG = LoginFragment.class.getSimpleName();
    private final AuthVM authVM = new AuthVM();

    private CallbackManager facebookCallbackManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLoginBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setVm(authVM);
        binding.setPresenter(new Presenter());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FacebookSdk.sdkInitialize(getCoreActivity().getApplicationContext());
        authVM.init(getContext());
    }

    private void goIntoApp() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(getCoreActivity().getApplication());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppEventsLogger.deactivateApp(getCoreActivity().getApplication());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    public class Presenter {

        public void onNextClicked() {
            if (authVM.login.get()) {
                onLoginClicked();
            } else {
                onRegistrationClicked();
            }
        }

        private void onLoginClicked() {
            final String phoneNumber = authVM.phone.get();
            final String password = authVM.password.get();

            try {
                Validators.getPhoneValidator(getContext()).validate(phoneNumber);
                Validators.getPasswordValidator(getContext()).validate(password);
            } catch (ValidationException e) {
                showMessage(e.getMessage());
                return;
            }
            hideKeyboard();

            final CoreRequest<Boolean> request = getCoreActivity().newWaitingRequest(new SuccessOutput<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    goIntoApp();
                }
            });
            getCoreService()
                    .login(authVM.phone.get(), authVM.password.get(), request);
        }

        private void onRegistrationClicked() {

            final String phoneNumber = authVM.phone.get();
            final String password = authVM.password.get();

            try {
                Validators.getPhoneValidator(getContext()).validate(phoneNumber);
                Validators.getPasswordValidator(getContext()).validate(password);
            } catch (ValidationException e) {
                showMessage(e.getMessage());
                return;
            }
            hideKeyboard();

            Fragment aboutYourselfFragment = AboutYourselfFragment.create(phoneNumber, password);


            getCoreActivity().replaceContentWithHistory(aboutYourselfFragment);
        }

        public void forgetPassword() {
            hideKeyboard();
            getCoreActivity().replaceContentWithHistory(new ForgetPasswordFragment());
        }

        public void facebookLogin() {
            hideKeyboard();
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if (accessToken != null) {
                goIntoApp();
            } else {
                if (facebookCallbackManager == null) {
                    facebookCallbackManager = CallbackManager.Factory.create();
                }
                final CoreRequest<Boolean> request = getCoreActivity().newWaitingRequest(new SuccessOutput<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if (result) {
                            goIntoApp();
                        }
                    }
                });
                getCoreService()
                        .loginFacebook(getActivity(), facebookCallbackManager, request);
            }

        }
    }
}
