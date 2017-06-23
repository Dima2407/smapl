package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapl_android.R;
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

   /* private EditText phoneNumberEditText;
    private EditText passwordEditText;



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phoneNumberEditText = (EditText) view.findViewById(R.id.edit_registration_phone_number);
        passwordEditText = (EditText) view.findViewById(R.id.edit_registration_password);
        view.findViewById(R.id.btn_registration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegistration();
            }
        });
    }

    private void handleRegistration() {
        String phoneNamber = phoneNumberEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        AboutYourselfFragment aboutYourselfFragment = new AboutYourselfFragment();

        Bundle bundle = new Bundle();
        bundle.putString("phoneNumber", phoneNamber);
        bundle.putString("password", password);
        aboutYourselfFragment.setArguments(bundle);

        getCoreActivity().replaceContentWithHistory(aboutYourselfFragment);

    }*/

    public class Presenter {

        public void onRegistrationClicked(UserInfoViewModel userInfoViewModel) {

            AboutYourselfFragment aboutYourselfFragment = new AboutYourselfFragment();
            Bundle bundle = new Bundle();

            bundle.putString("phoneNumber", userInfoViewModel.phone.get());
            bundle.putString("password", userInfoViewModel.password.get());
            aboutYourselfFragment.setArguments(bundle);

            getCoreActivity().replaceContentWithHistory(aboutYourselfFragment);
        }
    }
}
