package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class AboutYourselfFragment extends BaseFragment {

    public static final String TAG = AboutYourselfFragment.class.getSimpleName();
    private static final String GENDER_MAN = "man";
    private static final String GENDER_WOMAN = "woman";

    private UserInfoViewModel user;
    private Presenter presenter = new Presenter();

    // private RadioGroup gender;
    private Spinner age;
    private Spinner carBrand;
    private EditText carYearOfIssue;
    private Spinner carColor;
    private EditText name;
    // private EditText email;
    private EditText carModel;
    //private EditText editGender;
    //private RelativeLayout layoutGender;
    private Spinner spinnerGender;
    // private View viewGender;
    private TextView txtTitle;
    private TextView txtSpinnerGenderDefault;

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
        age = (Spinner) view.findViewById(R.id.spinner_about_yourself_age);
        carBrand = (Spinner) view.findViewById(R.id.spinner_about_yourself_car_brand);
        carYearOfIssue = (EditText) view.findViewById(R.id.edit_about_yourself_car_yaer_of_issue);
        carColor = (Spinner) view.findViewById(R.id.spinner_about_yourself_car_color);
        carModel = (EditText) view.findViewById(R.id.edit_about_yourself_car_model);
        txtSpinnerGenderDefault = (TextView) view.findViewById(R.id.txt_spinner_gender_default);
        //layoutGender = (RelativeLayout) view.findViewById(R.id.layout_gender_registration);
        //  editGender = (EditText) view.findViewById(R.id.edit_gender_registration);
        spinnerGender = (Spinner) view.findViewById(R.id.spinner_gender_registration);
        //ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(), R.layout.row_text_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.gender, R.layout.row_text_spinner);
        adapter.setDropDownViewResource(R.layout.row_text_spinner_down);
        spinnerGender.setAdapter(adapter);
        txtSpinnerGenderDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSpinnerGenderDefault.setVisibility(View.GONE);
                spinnerGender.performClick();
            }
        });
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
        //if (gender.getCheckedRadioButtonId() >= 0)
        //    user.gender.set(gender.getCheckedRadioButtonId() == R.id.radio_about_yourself_man ? GENDER_MAN : GENDER_WOMAN);
        user.age.set(age.getSelectedItem().toString());
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
