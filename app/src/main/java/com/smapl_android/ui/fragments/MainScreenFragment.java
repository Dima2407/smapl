package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.smapl_android.R;
import com.smapl_android.databinding.FragmentMainScreenBinding;
import com.smapl_android.model.User;
import com.smapl_android.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainScreenFragment extends BaseFragment {

    private static final String TAG = MainScreenFragment.class.getSimpleName();
    private LinearLayout linearContent;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMainScreenBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_screen, container, false);
        binding.setPresenter(new Presenter());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearContent = (LinearLayout) view.findViewById(R.id.linear_main_screen);
    }



    public class Presenter{

        public void onNewsClicked(){
            getCoreActivity().replaceContent(linearContent.getId(), new NewsFragment());
        }

        public void onMapClicked(){
            getCoreActivity().replaceContent(linearContent.getId(), new MapFragment());
        }

        public void onProfileClicked(){
            getCoreActivity().replaceContent(linearContent.getId(), new ProfileFragment());
        }

    }
}
