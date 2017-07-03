package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.smapl_android.R;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.databinding.FragmentRegistrationBinding;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.ui.base.BaseFragment;


public class RegistrationFragment extends BaseFragment {

    public static final String TAG = RegistrationFragment.class.getSimpleName();
    private Presenter presenter = new Presenter();
    private UserInfoViewModel userInfoViewModel;
    private EditText editLogin;
    private ImageView imgLoginBg;
    private EditText editPassword;
    private ImageView imgPasswordBg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentRegistrationBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration,container,false);
        userInfoViewModel = new UserInfoViewModel(getContext());
        binding.setUser(userInfoViewModel);
        binding.setPresenter(presenter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editLogin = (EditText) view.findViewById(R.id.edit_registration_phone_number);
        imgLoginBg = (ImageView) view.findViewById(R.id.img_edit_redistration_login_bg);
        imgLoginBg.setVisibility(View.INVISIBLE);

        editPassword = (EditText) view.findViewById(R.id.edit_registration_password);
        imgPasswordBg = (ImageView) view.findViewById(R.id.img_edit_redistration_password_bg);
        imgPasswordBg.setVisibility(View.INVISIBLE);

        editLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    imgLoginBg.setVisibility(View.INVISIBLE);
                } else {
                    imgLoginBg.setVisibility(View.VISIBLE);
                }
            }
        });

        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    imgPasswordBg.setVisibility(View.INVISIBLE);
                } else {
                    imgPasswordBg.setVisibility(View.VISIBLE);
                }
            }
        });
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

            getActivity().getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .add(android.R.id.content, aboutYourselfFragment, AboutYourselfFragment.TAG)
                    .commit();
        }
    }
}
