package com.smapl_android.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
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
import com.smapl_android.databinding.CarHistoryFooterViewBinding;
import com.smapl_android.databinding.CarHistoryHeaderViewBinding;
import com.smapl_android.databinding.FragmentCampaignDetailsBinding;
import com.smapl_android.databinding.StickerFooterBinding;
import com.smapl_android.databinding.StickerHeaderBinding;
import com.smapl_android.model.UserInfo;
import com.smapl_android.model.CampaignVM;
import com.smapl_android.net.responses.GetCampaignListResponse;
import com.smapl_android.ui.base.BaseFragment;

import java.util.List;

public class CampaignDetailsFragment extends BaseFragment {
    private RecyclerView stickersRecycleView;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

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
        stickersRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        //stickersRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
       // stickersRecycleView.addItemDecoration();
//        stickersRecycleView.addItemDecoration(new HeaderDecoration(getContext(),
//                stickersRecycleView,  R.layout.sticker_header));

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
        private static final int HEADER = 0;
        private static final int ITEM = 1;
        private static final int FOOTER = 2;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View view;

            public ViewHolder(View v) {
                super(v);
                this.view = v;
            }

        }

        private  final class HeaderVH extends ViewHolder {

            private final StickerHeaderBinding binding;

            public HeaderVH(StickerHeaderBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            private void bind(UserInfo item) {
                binding.executePendingBindings();
            }
        }

        private  final class FooterVH extends ViewHolder {

            private final StickerFooterBinding binding;

            public FooterVH(StickerFooterBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            private void bind(UserInfo item) {
                binding.executePendingBindings();
            }
        }



        public CampaignsDetailsListAdapter(String[] myDataset) {
            dataset = myDataset;
        }

        @Override
        public CampaignDetailsFragment.CampaignsDetailsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                       int viewType) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (viewType == FOOTER) {
                StickerFooterBinding binding = DataBindingUtil.inflate(inflater, R.layout.sticker_footer, parent, false);
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                binding.getRoot().setLayoutParams(lp);
                return new FooterVH(binding);
            } else if (viewType == HEADER) {

                StickerHeaderBinding binding = DataBindingUtil.inflate(inflater, R.layout.sticker_header, parent, false);
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                binding.getRoot().setLayoutParams(lp);
                return new HeaderVH(binding);
            }

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
        public int getItemViewType(int position) {
            if (position == 0) {
                return HEADER;
            } else if (position == getItemCount() - 1) {
                return FOOTER;
            } else {
                return ITEM;
            }
        }


        @Override
        public void onBindViewHolder(CampaignDetailsFragment.CampaignsDetailsListAdapter.ViewHolder holder, int position) {


        }

        @Override
        public int getItemCount() {
            return dataset.length;
        }

    }


    public class HeaderDecoration extends RecyclerView.ItemDecoration {

        private View mLayout;

        public HeaderDecoration(final Context context, RecyclerView parent, @LayoutRes int resId) {
            // inflate and measure the layout
            mLayout = LayoutInflater.from(context).inflate(resId, parent, false);
            mLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        }


        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            // layout basically just gets drawn on the reserved space on top of the first view
            mLayout.layout(parent.getLeft(), 0, parent.getRight(), mLayout.getMeasuredHeight());
            for (int i = 0; i < parent.getChildCount(); i++) {
                View view = parent.getChildAt(i);
                if (parent.getChildAdapterPosition(view) == 0) {
                    c.save();
                    final int height = mLayout.getMeasuredHeight();
                    final int top = view.getTop() - height;
                    c.translate(0, top);
                    mLayout.draw(c);
                    c.restore();
                    break;
                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.set(0, mLayout.getMeasuredHeight(), 0, 0);
            } else {
                outRect.setEmpty();
            }
        }
    }




}
