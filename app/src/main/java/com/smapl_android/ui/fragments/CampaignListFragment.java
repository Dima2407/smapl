package com.smapl_android.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentCampaignListBinding;
import com.smapl_android.databinding.ListItemCampaignBinding;
import com.smapl_android.model.CampaignVM;
import com.smapl_android.net.responses.GetCampaignListResponse;
import com.smapl_android.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class CampaignListFragment extends BaseFragment {

    private RecyclerView campaignRecycleView;
    private Presenter presenter = new Presenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentCampaignListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_campaign_list, container, false);
        binding.setPresenter(presenter);
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
        final CoreRequest<List<GetCampaignListResponse.Campaign>> request = getCoreActivity().newWaitingRequest(new SuccessOutput<List<GetCampaignListResponse.Campaign>>() {
                    @Override
                    public void onSuccess(List<GetCampaignListResponse.Campaign> result) {
                        campaignRecycleView.setAdapter(new CampaignAdapter(result, presenter));
                    }
                });
        getCoreService().getCampaigns(request);
    }


    private static final class CampaignAdapter extends RecyclerView.Adapter<CampaignVH> {

        private final List<CampaignVM> items = new ArrayList<>();
        private final Presenter presenter;

        CampaignAdapter(List<GetCampaignListResponse.Campaign> result, Presenter presenter) {
            this.presenter = presenter;
            for (GetCampaignListResponse.Campaign c : result) {
                items.add(CampaignVM.forList(c));
            }
        }

        @Override
        public CampaignVH onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final ListItemCampaignBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_campaign, parent, false);

            return new CampaignVH(binding);
        }

        @Override
        public void onBindViewHolder(CampaignVH holder, int position) {
            final CampaignVM campaign = items.get(position);
            holder.bind(campaign, presenter);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private static final class CampaignVH extends RecyclerView.ViewHolder {

        private final ListItemCampaignBinding binding;

        CampaignVH(ListItemCampaignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(CampaignVM item, Presenter presenter) {
            binding.setItem(item);
            binding.setPresenter(presenter);
            binding.executePendingBindings();
        }
    }

    public class Presenter extends BasePresenter {

        public void onCampaignClicked(CampaignVM campaignVM) {
            getCoreActivity().replaceContentWithHistory(CampaignDetailsFragment.newInstance(campaignVM));
        }


    }

}
