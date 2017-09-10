package com.smapl_android.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.smapl_android.R;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.databinding.FragmentAboutYourselfBinding;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.ui.base.BaseFragment;
import com.smapl_android.ui.widgets.AboutYourSelfSpinner;


public class AboutYourselfFragment extends BaseFragment {

    public static final String TAG = AboutYourselfFragment.class.getSimpleName();
    private static final String GENDER_MAN = "man";
    private static final String GENDER_WOMAN = "woman";

    private UserInfoViewModel user;
    private Presenter presenter = new Presenter();

    // private RadioGroup gender;
    //private Spinner age;
    private AboutYourSelfSpinner carBrand;
    private EditText carYearOfIssue;
    private AboutYourSelfSpinner carColor;
    private EditText name;
    // private EditText email;
    private EditText carModel;
    //private EditText editGender;
    //private RelativeLayout layoutGender;
    // private Spinner spinnerGender;
    // private View viewGender;
    private TextView txtTitle;
    // private TextView txtSpinnerGenderDefault;
    private AboutYourSelfSpinner spinnerGender;
    private AboutYourSelfSpinner spinnerAge;

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

        /*Typeface face;
        face = Typeface.createFromAsset(getActivity().getAssets(), "font/text_style.otf");*/

        txtTitle = (TextView) view.findViewById(R.id.text_about_yourself);
        // txtTitle.setTypeface(face);
        name = (EditText) view.findViewById(R.id.edit_about_yourself_name);
        // email = (EditText) view.findViewById(R.id.edit_about_yourself_email);
        // gender = (RadioGroup) view.findViewById(R.id.radio_group_about_yourself_gender);
        carYearOfIssue = (EditText) view.findViewById(R.id.edit_about_yourself_car_yaer_of_issue);
        carModel = (EditText) view.findViewById(R.id.edit_about_yourself_car_model);
        // txtSpinnerGenderDefault = (TextView) view.findViewById(R.id.txt_spinner_gender_default);
        //layoutGender = (RelativeLayout) view.findViewById(R.id.layout_gender_registration);
        //  editGender = (EditText) view.findViewById(R.id.edit_gender_registration);
        spinnerGender = (AboutYourSelfSpinner) view.findViewById(R.id.spinner_gender_registration);
        spinnerGender.setParams(R.array.gender, getString(R.string.gender));
        spinnerAge = (AboutYourSelfSpinner) view.findViewById(R.id.spinner_about_yourself_age);
        spinnerAge.setParams(R.array.age, getString(R.string.age));
        carBrand = (AboutYourSelfSpinner) view.findViewById(R.id.spinner_about_yourself_car_brand);
        carBrand.setParams(R.array.car_brand, getString(R.string.hint_car_brand));
        carColor = (AboutYourSelfSpinner) view.findViewById(R.id.spinner_about_yourself_car_color);
        carColor.setParams(R.array.car_color, getString(R.string.hint_car_color));
    }

    @Override
    public void onStart() {
        super.onStart();

        hideKeyboard();

    }

    private void registration() {

        hideKeyboard();

        if (!validation()) {
            showMessage(getString(R.string.fill_all_fields));
            return;
        }

        String phoneNumber = getArguments().getString("phoneNumber");
        String password = getArguments().getString("password");

        user.phone.set(phoneNumber);
        user.password.set(password);
        user.gender.set(spinnerGender.getSelectedItem().toString().equals(getString(R.string.man)) ? GENDER_MAN : GENDER_WOMAN);
        user.age.set(spinnerAge.getSelectedItem().toString());
        user.carBrand.set(carBrand.getSelectedItem().toString());
        user.carYearOfIssue.set(carYearOfIssue.getText().toString());
        user.color.set(carColor.getSelectedItem().toString());

        Fragment loadCarPhotoFragment = LoadCarPhotoFragment.create(user);
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

      /*  try {
            Validators.getEmailValidator(getContext()).validate(email.getText().toString());
        } catch (ValidationException e) {
            email.setError(e.getMessage());
            isValidate = false;
        }*/

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
