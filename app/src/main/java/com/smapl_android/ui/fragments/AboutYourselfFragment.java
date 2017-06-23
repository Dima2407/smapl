package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.smapl_android.R;
import com.smapl_android.databinding.FragmentAboutYourselfBinding;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.ui.base.BaseFragment;


public class AboutYourselfFragment extends BaseFragment {

    private static final String TAG = AboutYourselfFragment.class.getSimpleName();

    private UserInfoViewModel user;
    private Presenter presenter = new Presenter();

    private RadioGroup gender;
    private Spinner age;
    private Spinner carBrand;
    private EditText carYearOfIssue;
    private Spinner carColor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentAboutYourselfBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_yourself,container,false);
        user = new UserInfoViewModel(getContext());
        binding.setUser(user);
        binding.setPresenter(presenter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        gender = (RadioGroup) view.findViewById(R.id.radio_group_about_yourself_gender);
        age = (Spinner) view.findViewById(R.id.spinner_about_yourself_age);
        carBrand = (Spinner) view.findViewById(R.id.spinner_about_yourself_car_brand);
        carYearOfIssue = (EditText) view.findViewById(R.id.edit_about_yourself_car_yaer_of_issue);
        carColor = (Spinner) view.findViewById(R.id.spinner_about_yourself_car_color);

    }

    private void registration() {
        String phoneNumber = getArguments().getString("phoneNumber");
        String password = getArguments().getString("password");

        user.phone.set(phoneNumber);
        user.password.set(password);
        user.gender.set(gender.getCheckedRadioButtonId() == R.id.radio_about_yourself_man);
        user.age.set(age.getSelectedItem().toString());
        user.carBrand.set(carBrand.getSelectedItem().toString());
        final String yearStr = carYearOfIssue.getText().toString();
        if(!TextUtils.isEmpty(yearStr))
        user.carYearOfIssue.set(yearStr);
        user.color.set(carColor.getSelectedItem().toString());

        LoadCarPhotoFragment loadCarPhotoFragment = new LoadCarPhotoFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);

        loadCarPhotoFragment.setArguments(bundle);
        getCoreActivity().replaceContentWithHistory(loadCarPhotoFragment);

    }

    public class Presenter {

        public void onClickBack(){
            getActivity().onBackPressed();
        }

        public void onClickForward(){
            registration();
        }

    }
}
