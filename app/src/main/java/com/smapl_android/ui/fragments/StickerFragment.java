package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.smapl_android.R;
import com.smapl_android.databinding.FragmentStickerBinding;
import com.smapl_android.net.responses.GetCampaignListResponse;
import com.smapl_android.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StickerFragment extends BaseFragment {

    private static final String EXTRA_CAMPAIGN = "campaign";
    private static final String EXTRA_URL = "url";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentStickerBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sticker, container, false);
        binding.setPresenter(new Presenter(getCampaign(),getSelectedUrl()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private GetCampaignListResponse.Campaign getCampaign() {
        return (GetCampaignListResponse.Campaign) getArguments().getSerializable(EXTRA_CAMPAIGN);
    }


    private String getSelectedUrl() {
        return getArguments().getString(EXTRA_URL);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static Fragment newInstance(GetCampaignListResponse.Campaign campaign, String url) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CAMPAIGN, campaign);
        bundle.putString(EXTRA_URL, url);
        StickerFragment fragment = new StickerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    public class Presenter {

        public final ObservableField<String> currentPhoto = new ObservableField<>();

        private int currentIndex;
        private List<String> stickers;

        public Presenter(GetCampaignListResponse.Campaign campaign, String url) {
            stickers = campaign.getStickers();
            currentIndex = stickers.indexOf(url);
            if(currentIndex < 0) {
                currentPhoto.set(stickers.get(0));
            }else {
                currentPhoto.set(stickers.get(currentIndex));
            }
        }

        public void onClickLeftButton() {

            if (currentIndex == 0) return;

            currentIndex--;
            currentPhoto.set(stickers.get(currentIndex));
        }


        public void onClickRightButton() {
            if (currentIndex >= stickers.size() - 1) return;
            currentIndex++;
            currentPhoto.set(stickers.get(currentIndex));
        }

        public void onClickCloseButton() {
            getCoreActivity().onBackPressed();
        }

    }

}
