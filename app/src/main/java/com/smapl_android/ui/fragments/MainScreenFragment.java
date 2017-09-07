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
import com.smapl_android.databinding.FragmentMainScreenBinding;
import com.smapl_android.model.User;
import com.smapl_android.ui.base.BaseFragment;

public class MainScreenFragment extends BaseFragment {

    private static final String TAG = MainScreenFragment.class.getSimpleName();
    private LinearLayout linearContent;
    private ImageView imageItem1;
    private ImageView imageItem2;
    private ImageView imageItem3;

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

        imageItem1 = (ImageView) view.findViewById(R.id.img_main_screen_triangle_1);
        imageItem2 = (ImageView) view.findViewById(R.id.img_main_screen_triangle_2);
        imageItem3 = (ImageView) view.findViewById(R.id.img_main_screen_triangle_3);
    }



    public class Presenter{


        public void onHistoryClicked(){
            imageItem1.setVisibility(View.VISIBLE);
            imageItem2.setVisibility(View.GONE);
            imageItem3.setVisibility(View.GONE);
            getCoreActivity().replaceContentWithHistory(linearContent.getId(), new HistoryFragment());
        }

        public void onMapClicked(){
            imageItem1.setVisibility(View.GONE);
            imageItem2.setVisibility(View.VISIBLE);
            imageItem3.setVisibility(View.GONE);
            getCoreActivity().replaceContentWithHistory(linearContent.getId(), new MapFragment());
        }

        public void onProfileClicked(){
            imageItem1.setVisibility(View.GONE);
            imageItem2.setVisibility(View.GONE);
            imageItem3.setVisibility(View.VISIBLE);
            getCoreActivity().replaceContentWithHistory(linearContent.getId(), new ProfileFragment());
        }

    }
}
