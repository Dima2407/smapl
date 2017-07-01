package com.smapl_android.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.smapl_android.R;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.databinding.FragmentAboutYourselfBinding;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.ui.activities.AuthActivity;
import com.smapl_android.ui.base.BaseFragment;


public class AboutYourselfFragment extends BaseFragment {

    public static final String TAG = AboutYourselfFragment.class.getSimpleName();

    private UserInfoViewModel user;
    private Presenter presenter = new Presenter();

    private RadioGroup gender;
    private Spinner age;
    private Spinner carBrand;
    private EditText carYearOfIssue;
    private Spinner carColor;
    private EditText name;
    private EditText email;
    private EditText carModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentAboutYourselfBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_yourself, container, false);
        user = new UserInfoViewModel(getContext());
        binding.setUser(user);
        binding.setPresenter(presenter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = (EditText) view.findViewById(R.id.edit_about_yourself_name);
        email = (EditText) view.findViewById(R.id.edit_about_yourself_email);
        gender = (RadioGroup) view.findViewById(R.id.radio_group_about_yourself_gender);
        age = (Spinner) view.findViewById(R.id.spinner_about_yourself_age);
        carBrand = (Spinner) view.findViewById(R.id.spinner_about_yourself_car_brand);
        carYearOfIssue = (EditText) view.findViewById(R.id.edit_about_yourself_car_yaer_of_issue);
        carColor = (Spinner) view.findViewById(R.id.spinner_about_yourself_car_color);
        carModel = (EditText) view.findViewById(R.id.edit_about_yourself_car_model);

    }

    private void registration() {

        hideKeyboard();

        if (!validation()) {
            showMessage(getString(R.string.app_name), getString(R.string.fill_all_fields));
            return;
        }

        String phoneNumber = getArguments().getString("phoneNumber");
        String password = getArguments().getString("password");

        user.phone.set(phoneNumber);
        user.password.set(password);
        user.gender.set(gender.getCheckedRadioButtonId() == R.id.radio_about_yourself_man);
        user.age.set(age.getSelectedItem().toString());
        user.carBrand.set(carBrand.getSelectedItem().toString());
        user.carYearOfIssue.set(carYearOfIssue.getText().toString());
        user.color.set(carColor.getSelectedItem().toString());

        LoadCarPhotoFragment loadCarPhotoFragment = new LoadCarPhotoFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);

        loadCarPhotoFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(android.R.id.content, loadCarPhotoFragment, LoadCarPhotoFragment.TAG)
                .commit();

    }

    private boolean validation() {

        boolean isValidate = true;

        try {
            Validators.getNameValidator(getContext()).validate(name.getText().toString());
        } catch (ValidationException e) {
            name.setError(e.getMessage());
            isValidate = false;
        }

        try {
            Validators.getEmailValidator(getContext()).validate(email.getText().toString());
        } catch (ValidationException e) {
            email.setError(e.getMessage());
            isValidate = false;
        }

        try {
            Validators.getCarModelValidator(getContext()).validate(carModel.getText().toString());
        } catch (ValidationException e) {
            carModel.setError(e.getMessage());
            isValidate = false;
        }

        try {
           Validators.getCarYearValidator(getContext()).validate(carYearOfIssue.getText().toString());
        } catch (ValidationException e) {
            carYearOfIssue.setError(e.getMessage());
            isValidate = false;
        }

        return isValidate;
    }

    public class Presenter {

        public void onClickBack() {
            getActivity().onBackPressed();
        }

        public void onClickForward() {
            registration();
        }

    }
}
