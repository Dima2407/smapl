package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.smapl_android.R;
import com.smapl_android.databinding.FragmentLoginBinding;
import com.smapl_android.ui.activities.AuthActivity;
import com.smapl_android.ui.base.BaseFragment;


public class LoginFragment extends BaseFragment {

    public static final String TAG = LoginFragment.class.getSimpleName();
    private RelativeLayout layoutContent;
    private LoginMiniFragment loginMiniFragment;
    private RegistrationFragment registrationFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLoginBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setPresenter(new Presenter());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutContent = (RelativeLayout) view.findViewById(R.id.relative_login_content);

        loginMiniFragment = new LoginMiniFragment();
        registrationFragment = new RegistrationFragment();

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(layoutContent.getId(), loginMiniFragment, LoginMiniFragment.TAG)
                .commit();
    }

    public class Presenter {
        public void onLoginClicked() {
            getCoreActivity().replaceContentWithHistoryWithTag(layoutContent.getId(), loginMiniFragment, LoginMiniFragment.TAG);
        }

        public void onRegistrationClicked() {
            getCoreActivity().replaceContentWithHistoryWithTag(layoutContent.getId(), registrationFragment, RegistrationFragment.TAG);
        }
    }
}