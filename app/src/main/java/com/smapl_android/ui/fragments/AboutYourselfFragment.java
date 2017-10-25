package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentAboutYourselfBinding;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.ui.base.BaseFragment;

import java.util.UUID;


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

        if (TextUtils.isEmpty(user.phone.get())) {
            view.findViewById(R.id.layout_phone_number_about_yourself).setVisibility(View.VISIBLE);
        }
    }

    public String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 7);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user.init(getActivity());
    }

    private void registration() {

        hideKeyboard();

        final CoreRequest<Boolean> request = getCoreService()
                .newRequest(getCoreActivity());
        request
                .withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(new SuccessOutput<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {

                        if (result) {
                            Fragment loadCarPhotoFragment = LoadCarPhotoFragment.create();

                            getCoreActivity().replaceContentWithHistory(loadCarPhotoFragment);
                        }
                    }
                });
        if (TextUtils.isEmpty(user.password.get())) {
            user.password.set(generateRandomPassword());
        }
        getCoreService().registration(user.toRegistration(getContext()), request);
    }

    public static Fragment create(UserInfoViewModel userInfoViewModel) {
        Bundle bundle = new Bundle();

        bundle.putParcelable("user", userInfoViewModel);

        Fragment fragment = new AboutYourselfFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    public class Presenter extends BasePresenter {

        public void onClickForward() {
            registration();
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

        public void selectCarBrand(){
            selectCarBrand(user.carBrand);
        }

        public void selectCarColor(){
            selectCarColor(user.carColor);
        }

    }
}
