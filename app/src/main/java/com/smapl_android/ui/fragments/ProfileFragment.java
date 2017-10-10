package com.smapl_android.ui.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentProfileBinding;
import com.smapl_android.ui.activities.AuthActivity;
import com.smapl_android.ui.base.BaseFragment;
import com.smapl_android.ui.fragments.profile.AboutMeFragment;
import com.smapl_android.ui.fragments.profile.ChangePasswordFragment;
import com.smapl_android.ui.fragments.profile.SetCarFragment;

public class ProfileFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setPresenter(new Presenter());
        binding.setUser(getCoreActivity().getUserInfo());
        return binding.getRoot();
    }


    public class Presenter {


        public void goToCampaigns() {
            getCoreActivity().replaceContentWithHistory(new CampaignListFragment());
        }

        public void goToObtainMoney() {
            getCoreActivity().replaceContentWithHistory(new MoneyWithdrawalFragment());
        }

        public void goToEditPersonalInfo() {

            getCoreActivity().replaceContentWithHistory(new AboutMeFragment());
        }

        public void goToEditCar() {
            getCoreActivity().replaceContentWithHistory(new SetCarFragment());
        }

        public void goToEditPassword() {
            getCoreActivity().replaceContentWithHistory(new ChangePasswordFragment());
        }

        public void logout() {
            final CoreRequest<Boolean> request = getCoreService().newRequest(getCoreActivity());

            request.withLoading(R.string.wait_login)
                    .handleSuccess(new SuccessOutput<Boolean>() {
                        @Override
                        public void onSuccess(Boolean result) {
                            Intent intent = new Intent(getContext(), AuthActivity.class);
                            intent.setAction(AuthActivity.LOGOUT_ACTION);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
            getCoreService().logout(request);
        }
    }
}
