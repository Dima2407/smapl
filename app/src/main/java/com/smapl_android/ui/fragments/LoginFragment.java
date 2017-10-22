package com.smapl_android.ui.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.net.requests.FbLoginRequest;
import com.smapl_android.ui.activities.MainActivity;
import com.smapl_android.ui.base.BaseFragment;


public class LoginFragment extends BaseFragment {

    public static final String TAG = LoginFragment.class.getSimpleName();
    private final AuthVM authVM = new AuthVM();

    public static CallbackManager facebookCallbackManager;

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
        //AppEventsLogger.deactivateApp(getCoreActivity().getApplication());
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

            login(authVM.phone.get(), authVM.password.get());
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

            UserInfoViewModel user = new UserInfoViewModel();
            user.phone.set(phoneNumber);
            user.password.set(password);

            Fragment aboutYourselfFragment = AboutYourselfFragment.create(user);


            getCoreActivity().replaceContentWithHistory(aboutYourselfFragment);
        }

        public void forgetPassword() {
            hideKeyboard();
            getCoreActivity().replaceContentWithHistory(new ForgetPasswordFragment());
        }

        public void facebookLogin() {
            hideKeyboard();
            final CoreRequest<Boolean> request = getCoreService().newRequest(getCoreActivity());

            request.handleErrorAsDialog()
                    .handleSuccess(new SuccessOutput<Boolean>() {
                        @Override
                        public void onSuccess(Boolean result) {
                            if (result) {
                                goIntoApp();
                            } else {
                                Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if (accessToken != null) {
                getCoreService()
                        .loginWithFacebook(getCoreActivity(), new FbLoginRequest(accessToken.getUserId()), request);
            } else {
                if (facebookCallbackManager == null) {
                    facebookCallbackManager = CallbackManager.Factory.create();
                }
                getCoreService()
                        .loginFacebook(getActivity(), facebookCallbackManager, request);
            }

        }
    }

    private void login(String phone, String password) {
        final CoreRequest<Boolean> request = getCoreActivity().newWaitingRequest(new SuccessOutput<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    goIntoApp();
                } else {
                    Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }
        });
        getCoreService()
                .login(phone, password, request);
    }

}
