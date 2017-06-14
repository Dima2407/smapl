package com.smapl_android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.smapl_android.R;
import com.smapl_android.model.User;
import com.smapl_android.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainScreenFragment extends BaseFragment {

    private static final String TAG = MainScreenFragment.class.getSimpleName();
    private LinearLayout linearContent;
    private List<BaseFragment> fragmentsTabItems;
    private TabLayout tabLayout;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearContent = (LinearLayout) view.findViewById(R.id.linear_main_screen);

        fragmentsTabItems = new ArrayList<>();

        fragmentsTabItems.add(new NewsFragment());
        fragmentsTabItems.add(new MapFragment());
        fragmentsTabItems.add(new ProfileFragment());

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_main_screen);      

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(linearContent.getId(), fragmentsTabItems.get(0))
                .commit();        
        
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        
    }

    private void setFragment(int position) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(linearContent.getId(), fragmentsTabItems.get(position))
                .commit();
    }
}
