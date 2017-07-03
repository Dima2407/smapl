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

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.databinding.FragmentAboutMeBinding;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.net.requests.EditProfileRequest;
import com.smapl_android.net.responses.EditProfileResponse;
import com.smapl_android.net.responses.UserResponse;
import com.smapl_android.ui.base.BaseFragment;

import static com.smapl_android.R.string.email;

public class AboutMeFragment extends BaseFragment {

    private static final String TAG = AboutMeFragment.class.getSimpleName();
    private static final String GENDER_MAN = "man";
    private static final String GENDER_WOMAN = "woman";

    private UserInfoViewModel user;
    private RadioGroup gender;


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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gender = (RadioGroup) view.findViewById(R.id.radio_group_about_me_gender);
    }

    private void save() {

        if (TextUtils.isEmpty(user.name.get()) && TextUtils.isEmpty(user.phone.get()) && TextUtils.isEmpty(user.interests.get())
                /*&& TextUtils.isEmpty(user.age.get())*/ /*&& gender.getCheckedRadioButtonId() < 0*/) {
            showMessage(getString(R.string.app_name), getString(R.string.about_me_no_changes));
            return;
        }

        if (!TextUtils.isEmpty(user.phone.get())) {
            try {
                Validators.getPhoneValidator(getContext()).validate(user.phone.get());
            } catch (ValidationException e) {
                showMessage(getString(R.string.app_name), e.getMessage());
                return;
            }
        }

        if (!TextUtils.isEmpty(user.age.get())){
            if (Integer.valueOf(user.age.get()) < 18 || Integer.valueOf(user.age.get()) > 110) {
                showMessage(getString(R.string.app_name), getString(R.string.wrong_age));
                return;
            }
        }

        if (gender.getCheckedRadioButtonId() >= 0)
            user.gender.set(gender.getCheckedRadioButtonId() == R.id.radio_about_me_man ? GENDER_MAN : GENDER_WOMAN);

        final EditProfileRequest editProfileRequest = user.toEditProfileRequest();

        final CoreRequest<EditProfileResponse> request = getCoreService().newRequest(getCoreActivity());

        request
                .withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(new SuccessOutput<EditProfileResponse>() {
                    @Override
                    public void onSuccess(EditProfileResponse result) {
                        if (result.getCount() > 0) {
                            showMessage(getString(R.string.app_name), getString(R.string.changes_saved));
                            getActivity().onBackPressed();
                        }
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
