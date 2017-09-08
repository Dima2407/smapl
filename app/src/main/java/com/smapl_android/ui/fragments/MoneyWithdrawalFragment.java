package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smapl_android.R;

import com.smapl_android.core.CoreRequest;
import com.smapl_android.databinding.FragmentMoneyWithdrawBinding;
import com.smapl_android.net.responses.GetCampaignListResponse;
import com.smapl_android.ui.base.BaseFragment;

import java.util.List;


public class MoneyWithdrawalFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMoneyWithdrawBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_money_withdraw, container, false);
        binding.setPresenter(new Presenter());
        binding.setUser(getCoreActivity().getUserInfo());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //linearContent = (LinearLayout) view.findViewById(R.id.linear_main_screen);


    }

    public class Presenter{

        public void onClickBack(){
            getCoreActivity().onBackPressed();
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}