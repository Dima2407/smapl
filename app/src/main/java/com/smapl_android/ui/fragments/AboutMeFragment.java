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
import com.smapl_android.databinding.FragmentAboutMeBinding;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.net.responses.UserResponse;
import com.smapl_android.ui.base.BaseFragment;

public class AboutMeFragment extends BaseFragment{

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

    private void save(){
        showMessage(getString(R.string.app_name), "Эта функция в процессе разработки");

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
