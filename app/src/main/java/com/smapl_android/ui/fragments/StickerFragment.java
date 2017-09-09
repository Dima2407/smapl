package com.smapl_android.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.databinding.FragmentCampaignDetailsBinding;
import com.smapl_android.databinding.FragmentStickerBinding;
import com.smapl_android.net.responses.GetCampaignListResponse;
import com.smapl_android.ui.base.BaseFragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StickerFragment extends BaseFragment {

    private ImageView stickerView;
    private ArrayList<Uri> uriArrayList;
    private int currentIndex;

    public StickerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentStickerBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sticker, container, false);
        binding.setPresenter(new Presenter());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stickerView = (ImageView) view.findViewById(R.id.sticker_image_view);
        currentIndex = 0;
        uriArrayList = new ArrayList<>();
        uriArrayList.add(getUrl(R.drawable.audi8));
        uriArrayList.add(getUrl(R.drawable.rolls));
        uriArrayList.add(getUrl(R.drawable.buffatti));

        changeSticker(currentIndex);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void changeSticker(int index){
        stickerView.setImageURI(uriArrayList.get(index));
    }

    public static Uri getUrl(int res){
        return Uri.parse("android.resource://com.smapl_android/" + res);
    }


    public class Presenter {
        public void onClickLeftButton() {

            Toast.makeText(getContext(),"left",Toast.LENGTH_SHORT).show();

            if (currentIndex == 0) return;

            currentIndex--;
            changeSticker(currentIndex);
        }


        public void onClickRightButton() {
            Toast.makeText(getContext(),"right",Toast.LENGTH_SHORT).show();
            if (currentIndex >= uriArrayList.size() - 1) return;
            currentIndex++;
            changeSticker(currentIndex);
        }

        public void onClickCloseButton() {
            getCoreActivity().onBackPressed();
        }

    }

}
