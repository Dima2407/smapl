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
import com.smapl_android.databinding.FragmentSetCarBinding;
import com.smapl_android.model.UserInfo;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.UpdateCarResponse;
import com.smapl_android.net.responses.UserResponse;
import com.smapl_android.ui.base.BaseFragment;

public class SetCarFragment extends BaseFragment {
    private final UserInfo user = new UserInfo();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentSetCarBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_car, container, false);
        binding.setPresenter(new Presenter());
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

    public class Presenter {

        public void onBackClicked(){
            getActivity().onBackPressed();

        }

        public void onSaveClicked(){
            final UpdateCarRequest updateUserRequest = user.toUpdateCar();

            final CoreRequest<Boolean> request = getCoreService()
                    .newRequest(getCoreActivity());
            request
                    .withLoading(R.string.wait_login)
                    .handleErrorAsDialog()
                    .handleSuccess(new SuccessOutput<Boolean>() {
                        @Override
                        public void onSuccess(Boolean result) {
                            if(result){
                                getActivity().onBackPressed();
                            }
                        }
                    });
            getCoreService()
                    .updateCar(updateUserRequest, request);
        }
    }
}
