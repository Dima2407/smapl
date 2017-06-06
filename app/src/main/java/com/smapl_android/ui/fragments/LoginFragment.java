package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.CoreService;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentLoginBinding;
import com.smapl_android.model.LoginInfo;
import com.smapl_android.ui.CoreActivity;

public class LoginFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLoginBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setUser(new LoginInfo());
        binding.setPresenter(new Presenter());
        return binding.getRoot();
    }

    public class Presenter {
        public void onLoginClicked(LoginInfo loginInfo) {

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
                    .login(loginInfo.getPhone().get(), loginInfo.getPassword().get(), request);
        }

        public void onRegistrationClicked() {
            RegistrationFragment registrationFragment = new RegistrationFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, registrationFragment)
                    .commit();
        }
    }
}
