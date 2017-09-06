package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.databinding.FragmentSetCarBinding;
import com.smapl_android.model.UserInfo;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.UpdateCarResponse;
import com.smapl_android.net.responses.UserResponse;
import com.smapl_android.ui.base.BaseFragment;

public class SetCarFragment extends BaseFragment {

    private static final String TAG = SetCarFragment.class.getSimpleName();
    private UserInfoViewModel user;
    private Spinner carBrandSpinner;
    private EditText carYear;
    String carBrandStr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentSetCarBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_car, container, false);
        binding.setPresenter(new Presenter());
        user = new UserInfoViewModel(getContext());
        binding.setUser(user);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carYear = (EditText) view.findViewById(R.id.edit_set_car_year_issue);
        carBrandSpinner = (Spinner) view.findViewById(R.id.spinner_set_car_brand);
        setUserFromRequest();

        carBrandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ((TextView) parentView.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUserFromRequest() {
        final CoreRequest<UserResponse> request = getCoreService()
                .newRequest(getCoreActivity());
        request
                .withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(new SuccessOutput<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse result) {
                        user.apply(result);
                        carBrandStr = user.carBrand.get();
                        setSpinnerPosition();
                    }
                });
        getCoreService()
                .getUser(request);
    }

    private void setSpinnerPosition() {
        if (carBrandStr != null) {
            String[] strCarYearArray = getResources().getStringArray(R.array.car_brand);
            for (int i = 0; i < strCarYearArray.length; i++) {
                if (strCarYearArray[i].equals(carBrandStr))
                    carBrandSpinner.setSelection(i);
            }
        }
    }


    public class Presenter {

        public void onBackClicked() {
            getActivity().onBackPressed();

        }

        public void onSaveClicked() {

            try {
                Validators.getCarYearValidator(getContext()).validate(carYear.getText().toString());
            } catch (ValidationException e) {
                showMessage(getString(R.string.app_name), e.getMessage());
                return;
            }

            try {
                Validators.getCarModelValidator(getContext()).validate(user.carModel.get());
            } catch (ValidationException e) {
                showMessage(getString(R.string.app_name), e.getMessage());
                return;
            }

            user.carBrand.set(carBrandSpinner.getSelectedItem().toString());

            final UpdateCarRequest updateUserRequest = user.toUpdateCar();

            final CoreRequest<Boolean> request = getCoreService()
                    .newRequest(getCoreActivity());
            request
                    .withLoading(R.string.wait_login)
                    .handleErrorAsDialog()
                    .handleSuccess(new SuccessOutput<Boolean>() {
                        @Override
                        public void onSuccess(Boolean result) {
                            if (result) {
                                showMessage(getString(R.string.app_name), getString(R.string.changes_saved));
                                getActivity().onBackPressed();
                            }
                        }
                    });
            getCoreService()
                    .updateCar(updateUserRequest, request);
        }
    }
}
