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
import com.smapl_android.databinding.FragmentSetCarBinding;
import com.smapl_android.model.CarInfoEditVM;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.UserResponse;
import com.smapl_android.ui.base.BaseFragment;

public class SetCarFragment extends BaseFragment {

    private CarInfoEditVM carInfo = new CarInfoEditVM();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentSetCarBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_car, container, false);
        binding.setPresenter(new Presenter());
        binding.setUser(getCoreActivity().getUserInfo());
        binding.setCar(carInfo);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        carInfo.apply(getCoreActivity().getUserInfo());
    }

    public class Presenter {

        public void onBackClicked() {
            getActivity().onBackPressed();

        }

        public void onSaveClicked() {

            try {
                Validators.getCarYearValidator(getContext()).validate(carInfo.carYear.get());
                Validators.getCarModelValidator(getContext()).validate(carInfo.carModel.get());
            } catch (ValidationException e) {
                showMessage(e.getMessage());
                return;
            }

            final UpdateCarRequest updateUserRequest = carInfo.toUpdateCar();

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
            getCoreService()
                    .updateCar(updateUserRequest, request);
        }
    }
}
