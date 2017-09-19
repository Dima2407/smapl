package com.smapl_android.ui.fragments.profile;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentAboutMeBinding;
import com.smapl_android.model.UserInfoEditVM;
import com.smapl_android.net.requests.EditProfileRequest;
import com.smapl_android.net.responses.UserResponse;
import com.smapl_android.ui.base.BaseFragment;

import java.util.Arrays;
import java.util.List;

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
        user.init(getActivity());
        user.apply(getCoreActivity().getUserInfo());
    }

    public class Presenter extends BasePresenter {

        public void onClickForward() {
            hideKeyboard();
            final EditProfileRequest editProfileRequest = user.toUpdateRequest();

            final CoreRequest<UserResponse> request = getCoreActivity().newWaitingRequest(new SuccessOutput<UserResponse>() {
                @Override
                public void onSuccess(UserResponse result) {
                    getCoreActivity().getUserInfo().apply(getResources(), result);
                    showMessage(getString(R.string.changes_saved), new Runnable() {
                        @Override
                        public void run() {
                            onClickBack();
                        }
                    });

                }
            });

            getCoreService().editProfile(editProfileRequest, request);
        }

        public void selectGender(){
            selectGender(user.gender);
        }

        public void selectAge(){
            selectAge(user.age);
        }

        public void selectInterests(){
            selectInterests(user.interests);
        }

    }
}
