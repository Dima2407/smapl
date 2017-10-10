package com.smapl_android.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapl_android.R;
import com.smapl_android.databinding.FragmentCampaignDetailsBinding;
import com.smapl_android.databinding.ListItemCampaignDetailsHeaderBinding;
import com.smapl_android.databinding.ListItemStickerBinding;
import com.smapl_android.model.StickerVM;
import com.smapl_android.model.CampaignVM;
import com.smapl_android.net.responses.GetCampaignListResponse;
import com.smapl_android.ui.base.BaseFragment;

import java.util.List;

public class CampaignDetailsFragment extends BaseFragment {
    private RecyclerView stickersRecycleView;
    private final static String EXTRA_CAMPAIGN = GetCampaignListResponse.Campaign.class.getName();
    private Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentCampaignDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_campaign_details, container, false);
        presenter = new Presenter();
        binding.setPresenter(presenter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stickersRecycleView = (RecyclerView) view.findViewById(R.id.stickers_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        stickersRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));


        GetCampaignListResponse.Campaign campaign = getCampaign();

        CampaignsDetailsListAdapter campaignsDetailsListAdapter = new CampaignsDetailsListAdapter(campaign, presenter);

        stickersRecycleView.setAdapter(campaignsDetailsListAdapter);
    }

    private GetCampaignListResponse.Campaign getCampaign() {
        return (GetCampaignListResponse.Campaign) getArguments().getSerializable(EXTRA_CAMPAIGN);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static Fragment newInstance(GetCampaignListResponse.Campaign campaign) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CAMPAIGN, campaign);
        CampaignDetailsFragment fragment = new CampaignDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Fragment newInstance(CampaignVM campaign) {
        return newInstance(campaign.getCampaign());
    }


    public class Presenter extends BasePresenter {

        public void onLeftStickerClicked(StickerVM sticker) {
            getCoreActivity().replaceContentWithHistory(StickerFragment.newInstance(getCampaign(), sticker.photoLeft.get()));
        }

        public void onRightStickerClicked(StickerVM sticker) {
            getCoreActivity().replaceContentWithHistory(StickerFragment.newInstance(getCampaign(), sticker.photoRight.get()));
        }
    }

    private class CampaignsDetailsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int HEADER = 0;
        private static final int ITEM = 1;
        private GetCampaignListResponse.Campaign campaign;
        private Presenter presenter;


        public CampaignsDetailsListAdapter(GetCampaignListResponse.Campaign campaign, Presenter presenter) {

            this.campaign = campaign;
            this.presenter = presenter;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (viewType == HEADER) {

                ListItemCampaignDetailsHeaderBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_campaign_details_header, parent, false);
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                binding.getRoot().setLayoutParams(lp);
                return new HeaderVH(binding);
            }
            ListItemStickerBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_sticker, parent, false);

            return new ItemVH(binding);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return HEADER;
            } else {
                return ITEM;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == HEADER) {
                HeaderVH headerVH = (HeaderVH) holder;
                headerVH.bind(new CampaignVM(campaign));
            } else {
                StickerVM vm = new StickerVM();
                int stickersStart = (position - 1) * 2;
                List<String> stickers = campaign.getStickers();
                vm.photoLeft.set(stickers.get(stickersStart));
                if (stickersStart + 1 < stickers.size())
                    vm.photoRight.set(stickers.get(stickersStart + 1));
                ItemVH itemVH = (ItemVH) holder;
                itemVH.bind(vm, presenter);
            }

        }

        @Override
        public int getItemCount() {
            int stickers = campaign.getStickers().size();
            return 1 + stickers / 2 + stickers % 2;
        }

    }

    public static class ItemVH extends RecyclerView.ViewHolder {

        private ListItemStickerBinding binding;

        public ItemVH(ListItemStickerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StickerVM vm, Presenter presenter) {
            binding.setItem(vm);
            binding.setPresenter(presenter);
            binding.executePendingBindings();
        }

    }

    private static final class HeaderVH extends RecyclerView.ViewHolder {

        private final ListItemCampaignDetailsHeaderBinding binding;

        public HeaderVH(ListItemCampaignDetailsHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(CampaignVM item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }

}
