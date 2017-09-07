package com.smapl_android.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.databinding.FragmentCampaignDetailsBinding;
import com.smapl_android.model.CampaignVM;
import com.smapl_android.net.responses.GetCampaignListResponse;
import com.smapl_android.ui.base.BaseFragment;

import java.util.List;

public class CampaignDetailsFragment extends BaseFragment {
    private RecyclerView stickersRecycleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentCampaignDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_campaign_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stickersRecycleView = (RecyclerView) view.findViewById(R.id.stickers_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        stickersRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        String[] strArray = new String[]{"One", "Two", "Three","One", "Two", "Three"};

        CampaignDetailsFragment.CampaignsDetailsListAdapter campaignsDetailsListAdapter = new CampaignDetailsFragment.CampaignsDetailsListAdapter(strArray);

        stickersRecycleView.setAdapter(campaignsDetailsListAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final CoreRequest<List<GetCampaignListResponse.Campaign>> request = getCoreService()
                .newRequest(getCoreActivity());



        getCoreService().getCampaigns(request);
    }

    public static Fragment newInstance(GetCampaignListResponse.Campaign campaign) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(campaign.getClass().getName(), campaign);
        CampaignDetailsFragment fragment = new CampaignDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Fragment newInstance(CampaignVM campaign) {
        return newInstance(campaign.getCampaign());
    }


    private class CampaignsDetailsListAdapter extends RecyclerView.Adapter<CampaignDetailsFragment.CampaignsDetailsListAdapter.ViewHolder> {
        private String[] dataset;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View view;

            public ViewHolder(View v) {
                super(v);
                this.view = v;
            }

        }

        public CampaignsDetailsListAdapter(String[] myDataset) {
            dataset = myDataset;
        }

        @Override
        public CampaignDetailsFragment.CampaignsDetailsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                       int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sticker_list_item, parent, false);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getCoreActivity().replaceContentWithHistory(new StickerFragment());
                    Toast.makeText(getContext(), "Open Sticker...", Toast.LENGTH_SHORT).show();
                }
            });

            CampaignDetailsFragment.CampaignsDetailsListAdapter.ViewHolder vh = new CampaignDetailsFragment.CampaignsDetailsListAdapter.ViewHolder(view);
            return vh;
        }


        @Override
        public void onBindViewHolder(CampaignDetailsFragment.CampaignsDetailsListAdapter.ViewHolder holder, int position) {


        }

        @Override
        public int getItemCount() {
            return dataset.length;
        }
    }

}
