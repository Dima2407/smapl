package com.smapl_android.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentCampaignListBinding;
import com.smapl_android.net.responses.GetCampaignListResponse;
import com.smapl_android.ui.base.BaseFragment;

import java.util.List;

public class CampaignListFragment extends BaseFragment {

    private RecyclerView campaignRecycleView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentCampaignListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_campaign_list, container, false);
        binding.setPresenter(new Presenter());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        campaignRecycleView = (RecyclerView) view.findViewById(R.id.campaign_recycle_view);
        campaignRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final CoreRequest<List<GetCampaignListResponse.Campaign>> request = getCoreService()
                .newRequest(getCoreActivity());
        request
                .withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(new SuccessOutput<List<GetCampaignListResponse.Campaign>>() {
                    @Override
                    public void onSuccess(List<GetCampaignListResponse.Campaign> result) {
                        final CampaignAdapter adapter = new CampaignAdapter(result);
                        campaignRecycleView.setAdapter(adapter);
                    }
                });
        getCoreService().getCampaigns(request);
    }


    private static final class CampaignAdapter extends RecyclerView.Adapter<CampaignVH>{

        private final List<GetCampaignListResponse.Campaign> items;

        CampaignAdapter(List<GetCampaignListResponse.Campaign> result) {
            items = result;
        }

        @Override
        public CampaignVH onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();

            View itemView = View.inflate(context, R.layout.item_compaign, null);

            return new CampaignVH(itemView);
        }

        @Override
        public void onBindViewHolder(CampaignVH holder, int position) {
            final GetCampaignListResponse.Campaign campaign = items.get(position);
            holder.title.setText(campaign.getName());
            holder.description.setText(campaign.getDescription());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private static final class CampaignVH extends RecyclerView.ViewHolder{

        private final ImageView image;
        private final TextView title;
        private final TextView description;

        CampaignVH(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(android.R.id.icon);
            title = (TextView) itemView.findViewById(android.R.id.text1);
            description = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }

    public class Presenter {
        public void onClickBack(){

        }
    }
}
