package com.smapl_android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.smapl_android.R;
import com.smapl_android.model.User;
import com.smapl_android.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


public class AboutYourselfFragment extends BaseFragment {

    private static final String TAG = AboutYourselfFragment.class.getSimpleName();

    private EditText name;
    private EditText lastName;
    private RadioGroup gender;
    private Spinner age;
    private Spinner carBrand;
    private EditText carModel;
    private EditText carYearOfIssue;
    private Spinner carColor;
    private EditText interests;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_yourself, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = (EditText) view.findViewById(R.id.edit_about_yourself_first_name);
        lastName = (EditText) view.findViewById(R.id.edit_about_yourself_last_name);
        gender = (RadioGroup) view.findViewById(R.id.radio_group_about_yourself_gender);
        age = (Spinner) view.findViewById(R.id.spinner_about_yourself_age);
        carBrand = (Spinner) view.findViewById(R.id.spinner_about_yourself_car_brand);
        carModel = (EditText) view.findViewById(R.id.edit_about_yourself_car_model);
        carYearOfIssue = (EditText) view.findViewById(R.id.edit_about_yourself_car_yaer_of_issue);
        carColor = (Spinner) view.findViewById(R.id.spinner_about_yourself_car_color);
        interests = (EditText) view.findViewById(R.id.edit_about_yourself_interests);

        view.findViewById(R.id.btn_about_yourself_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration();
            }
        });

    }

    private void registration() {
        String phoneNumber = getArguments().getString("phoneNumber");
        String password = getArguments().getString("password");

        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setFirstName(name.getText().toString());
        user.setLastName(lastName.getText().toString());
        user.setGender(gender.getCheckedRadioButtonId() == R.id.radio_about_yourself_man);
    /*    String strAge = age.getSelectedItem().toString();
        User.Age ageEnum = null;
        for (int i = 0; i < getResources().getStringArray(R.array.age).length; i++){
            if (strAge.equals(getResources().getStringArray(R.array.age)[i])){
                ageEnum = User.Age.values()[i];
            }
        }*/
        user.setAge(Integer.parseInt(age.getSelectedItem().toString()));
        user.setCarBrand(carBrand.getSelectedItem().toString());
        user.setCarModel(carModel.getText().toString());
        final String yearStr = carYearOfIssue.getText().toString();
        if(!TextUtils.isEmpty(yearStr))
        user.setCarYearOfIssue(Integer.parseInt(yearStr));
        user.setColor(carColor.getSelectedItem().toString());
    /*    List<String> interests = new ArrayList<>();
        SparseBooleanArray interestsArray = this.interests.getCheckedItemPositions();
        for (int i = 0; i < interestsArray.size(); i++){
            int key = interestsArray.keyAt(i);
            if (interestsArray.get(key)){
                interests.add(getResources().getStringArray(R.array.interests)[key]);
            }
        }
        String[] stringInterests = new String[interests.size()];
        for (int i = 0; i < interests.size(); i++){
            stringInterests[i] = interests.get(i);
        }*/
        user.setInterests(interests.getText().toString());

        LoadCarPhotoFragment loadCarPhotoFragment = new LoadCarPhotoFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);

        loadCarPhotoFragment.setArguments(bundle);

        getCoreActivity().replaceContentWithHistory(loadCarPhotoFragment);

    }
}
