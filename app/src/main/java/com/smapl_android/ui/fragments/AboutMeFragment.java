package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.databinding.FragmentAboutMeBinding;
import com.smapl_android.model.UserInfoEditVM;
import com.smapl_android.net.requests.EditProfileRequest;
import com.smapl_android.net.responses.UserResponse;
import com.smapl_android.ui.base.BaseFragment;

public class AboutMeFragment extends BaseFragment {


    private UserInfoEditVM user = new UserInfoEditVM();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentAboutMeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_me, container, false);
        binding.setPresenter(new Presenter());
        binding.setUserEdit(user);
        binding.setUser(getCoreActivity().getUserInfo());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user.apply(getCoreActivity().getUserInfo());
    }

    private void save() {

        try {
            Validators.getNameValidator(getContext()).validate(user.name.get());
            Validators.getPhoneValidator(getContext()).validate(user.phone.get());
        } catch (ValidationException e) {
            showMessage(e.getMessage());
            return;
        }

        final EditProfileRequest editProfileRequest = user.toUpdateRequest();

        final CoreRequest<UserResponse> request = getCoreActivity().newWaitingRequest(new SuccessOutput<UserResponse>() {
            @Override
            public void onSuccess(UserResponse result) {
                getCoreActivity().getUserInfo().apply(getResources(), result);
                showMessage(getString(R.string.changes_saved), new Runnable() {
                    @Override
                    public void run() {
                        getActivity().onBackPressed();
                    }
                });

            }
        });

        getCoreService().editProfile(editProfileRequest, request);
    }

    public class Presenter {

        public void onClickForward() {
            hideKeyboard();
            save();
        }

        public void onClickBack() {
            hideKeyboard();
            getActivity().onBackPressed();
        }


    }
}
