package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.smapl_android.R;
import com.smapl_android.databinding.FragmentLoginBinding;
import com.smapl_android.model.LoginInfo;


public class LoginFragment extends BaseFragment {

    private RelativeLayout layoutContent;
    private LoginMiniFragment loginMiniFragment;
    private RegistrationFragment registrationFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLoginBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.setUser(new LoginInfo());
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
                .add(layoutContent.getId(), loginMiniFragment)
                .commit();

    }


    public class Presenter {
        public void onLoginClicked(LoginInfo loginInfo) {

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(layoutContent.getId(), loginMiniFragment)
                    .commit();
        }

        public void onRegistrationClicked() {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(layoutContent.getId(), registrationFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
