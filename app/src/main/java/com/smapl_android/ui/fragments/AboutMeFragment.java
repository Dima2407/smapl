package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentAboutMeBinding;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.net.requests.EditProfileRequest;
import com.smapl_android.net.responses.EditProfileResponse;
import com.smapl_android.net.responses.UserResponse;
import com.smapl_android.ui.base.BaseFragment;

public class AboutMeFragment extends BaseFragment {

    private static final String TAG = AboutMeFragment.class.getSimpleName();
    private UserInfoViewModel user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentAboutMeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_me, container, false);
        binding.setPresenter(new Presenter());
        user = new UserInfoViewModel(getActivity());
        binding.setUser(user);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final CoreRequest<UserResponse> request = getCoreService()
                .newRequest(getCoreActivity());
        request
                .withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(new SuccessOutput<UserResponse>() {
                    @Override
                    public void onSuccess(UserResponse result) {
                        user.apply(result);
                    }
                });
        getCoreService()
                .getUser(request);
    }

    private void save() {

        if (TextUtils.isEmpty(user.name.get()) && TextUtils.isEmpty(user.phone.get()) && TextUtils.isEmpty(user.interests.get())
                && TextUtils.isEmpty(user.age.get())/* && TextUtils.isEmpty(user.gender.get())*/) {
            showMessage(getString(R.string.app_name), getString(R.string.about_me_no_changes));
            return;
        }

        final EditProfileRequest editProfileRequest = user.toEditProfileRequest();

        final CoreRequest<EditProfileResponse> request = getCoreService().newRequest(getCoreActivity());

        request
                .withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(new SuccessOutput<EditProfileResponse>() {
                    @Override
                    public void onSuccess(EditProfileResponse result) {
                        if (result.getCount() > 0) {
                            getActivity().onBackPressed();
                            Log.i(TAG, "onSucces, result = " + result.getCount());
                        }
                    }
                });

        getCoreService().editProfile(editProfileRequest, request);
    }

    public class Presenter {

        public void onClickForward() {
            save();
        }

        public void onClickBack() {
            getActivity().onBackPressed();
        }


    }
}
