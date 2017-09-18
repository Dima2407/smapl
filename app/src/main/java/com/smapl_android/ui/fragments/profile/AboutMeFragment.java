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
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validators;
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

        public void selectGender(){
            List<String> strings = Arrays.asList(getActivity().getResources().getStringArray(R.array.gender));
            final int selectedIndex = strings.indexOf(user.gender.get());
            new MaterialDialog.Builder(getActivity())
                    .theme(Theme.LIGHT)
                    .title(R.string.select_gender)
                    .items(R.array.gender)
                    .itemsCallbackSingleChoice(selectedIndex, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            user.gender.set(text.toString());
                            return true;
                        }
                    })
                    .alwaysCallSingleChoiceCallback()
                    .negativeText(android.R.string.cancel)
                    .show();
        }

        public void selectAge(){
            List<String> strings = Arrays.asList(getActivity().getResources().getStringArray(R.array.age_entities));
            final int selectedIndex = strings.indexOf(user.age.get());
            new MaterialDialog.Builder(getActivity())
                    .theme(Theme.LIGHT)
                    .title(R.string.select_age)
                    .items(R.array.age_entities)
                    .itemsCallbackSingleChoice(selectedIndex, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            user.age.set(text.toString());
                            return true;
                        }
                    })
                    .alwaysCallSingleChoiceCallback()
                    .negativeText(android.R.string.cancel)
                    .show();
        }

        public void selectInterests(){
            List<String> strings = Arrays.asList(getActivity().getResources().getStringArray(R.array.interests));
            final String[] interests = user.interests.get().split(",");
            final Integer[] selectedIndex = new Integer[interests.length];
            for (int i = 0; i < interests.length; i++) {
                selectedIndex[i] = strings.indexOf(interests[i]);
            }
            new MaterialDialog.Builder(getActivity())
                    .theme(Theme.LIGHT)
                    .title(R.string.select_interests)
                    .items(R.array.interests)
                    .itemsCallbackMultiChoice(selectedIndex, new MaterialDialog.ListCallbackMultiChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                            user.interests.set(TextUtils.join(",", text));
                            return true;
                        }
                    })
                    .cancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            user.interests.set(TextUtils.join(",", interests));
                        }
                    })
                    .alwaysCallMultiChoiceCallback()
                    .negativeText(android.R.string.cancel)
                    .positiveText(android.R.string.ok)
                    .show();
        }

    }
}
