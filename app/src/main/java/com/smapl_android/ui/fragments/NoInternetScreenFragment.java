package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapl_android.R;
import com.smapl_android.databinding.FragmentNoInternetScreenBinding;
import com.smapl_android.ui.base.BaseFragment;

public class NoInternetScreenFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentNoInternetScreenBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_no_internet_screen, container, false);
        dataBinding.setPresenter(new Presenter());
        return dataBinding.getRoot();
    }

    public class Presenter extends BasePresenter{

    }
}
