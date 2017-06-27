package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapl_android.R;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.databinding.FragmentRegistrationBinding;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.ui.base.BaseFragment;


public class RegistrationFragment extends BaseFragment {

    private Presenter presenter = new Presenter();
    private UserInfoViewModel userInfoViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentRegistrationBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration,container,false);
        userInfoViewModel = new UserInfoViewModel(getContext());
        binding.setUser(userInfoViewModel);
        binding.setPresenter(presenter);
        return binding.getRoot();
    }


    public class Presenter {

        public void onRegistrationClicked(UserInfoViewModel userInfoViewModel) {

            final String phoneNumber = userInfoViewModel.phone.get();
            final String password = userInfoViewModel.password.get();

            try {
                Validators.getPhoneValidator(getContext()).validate(phoneNumber);
                Validators.getPasswordValidator(getContext()).validate(password);
            } catch (ValidationException e) {
                showMessage(getString(R.string.app_name), e.getMessage());
                return;
            }

            AboutYourselfFragment aboutYourselfFragment = new AboutYourselfFragment();
            Bundle bundle = new Bundle();
            bundle.putString("phoneNumber", phoneNumber);
            bundle.putString("password", password);
            aboutYourselfFragment.setArguments(bundle);

            getCoreActivity().replaceContentWithHistory(aboutYourselfFragment);
        }
    }
}
