package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.smapl_android.R;
import com.smapl_android.databinding.FragmentMainScreenBinding;
import com.smapl_android.model.MainScreenVM;
import com.smapl_android.ui.base.BaseFragment;

public class MainScreenFragment extends BaseFragment {

    private final MainScreenVM mainScreenVM = new MainScreenVM();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMainScreenBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_screen, container, false);
        binding.setVm(mainScreenVM);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainScreenVM.mapActive.addOnPropertyChangedCallback(getCallback(R.id.linear_main_screen, MapFragment.class));

        mainScreenVM.historyActive.addOnPropertyChangedCallback(getCallback(R.id.linear_main_screen, HistoryFragment.class));

        mainScreenVM.profileActive.addOnPropertyChangedCallback(getCallback(R.id.linear_main_screen, ProfileFragment.class));

        mainScreenVM.init();
    }

    @NonNull
    private Observable.OnPropertyChangedCallback getCallback(final int contentId, final Class<? extends Fragment> fr) {
        return new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                ObservableField<Boolean> field = (ObservableField<Boolean>) sender;
                if (field.get()) {
                    getCoreActivity().replaceContentNoHistory(contentId, getChildFragmentManager(), fr);
                }

            }
        };
    }
}
