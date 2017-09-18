package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapl_android.R;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.databinding.FragmentAboutYourselfBinding;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.ui.base.BaseFragment;


public class AboutYourselfFragment extends BaseFragment {

    private UserInfoViewModel user;
    private Presenter presenter = new Presenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentAboutYourselfBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_yourself, container, false);
        user = getArguments().getParcelable("user");
        binding.setUser(user);
        binding.setPresenter(presenter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        Fragment loadCarPhotoFragment = LoadCarPhotoFragment.create(user);

        getCoreActivity().replaceContentWithHistory(loadCarPhotoFragment);

    }

    private boolean validation() {

        try {
            Validators.getNameValidator(getContext()).validate(user.name.get());
        } catch (ValidationException e) {
            user.nameError.set(e.getMessage());
            return false;
        }

        try {
            Validators.getEmailValidator(getContext()).validate(user.email.get());
        } catch (ValidationException e) {
            user.emailError.set(e.getMessage());
            return false;
        }

        try {
            Validators.getCarModelValidator(getContext()).validate(user.carModel.get());
        } catch (ValidationException e) {
            user.carModelError.set(e.getMessage());
            return false;
        }

        try {
            Validators.getCarYearValidator(getContext()).validate(user.carYearOfIssue.get());
        } catch (ValidationException e) {
            user.carYearOfIssueError.set(e.getMessage());
            return false;
        }

        return true;
    }

    public static Fragment create(String phoneNumber, String password) {
        Bundle bundle = new Bundle();
        UserInfoViewModel infoViewModel = new UserInfoViewModel();
        infoViewModel.phone.set(phoneNumber);
        infoViewModel.password.set(password);

        bundle.putParcelable("user", infoViewModel);

        Fragment fragment = new AboutYourselfFragment();
        fragment.setArguments(bundle);

        return fragment;
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
