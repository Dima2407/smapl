package com.smapl_android.ui.fragments;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
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
import com.smapl_android.databinding.CarHistoryFooterViewBinding;
import com.smapl_android.databinding.CarHistoryHeaderViewBinding;
import com.smapl_android.databinding.ListItemHistoryBinding;
import com.smapl_android.model.UserInfo;
import com.smapl_android.net.responses.GetCampaignListResponse;
import com.smapl_android.net.responses.GetTrackingHistoryResponse;
import com.smapl_android.ui.base.BaseFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryFragment extends BaseFragment {

    private RecyclerView carsHistoryListView;

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }


    private void prepareCarsList() {
        carsHistoryListView.setLayoutManager(new LinearLayoutManager(getContext()));

        final CoreRequest<List<GetTrackingHistoryResponse.TrackingHistory>> request = getCoreActivity().newWaitingRequest(new SuccessOutput<List<GetTrackingHistoryResponse.TrackingHistory>>() {
            @Override
            public void onSuccess(List<GetTrackingHistoryResponse.TrackingHistory> result) {
                HistoryAdapter adapter = new HistoryAdapter(getCoreActivity().getUserInfo(), result);
                carsHistoryListView.setAdapter(adapter);
            }
        });
        getCoreService().getHistory(request);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carsHistoryListView = (RecyclerView) view.findViewById(android.R.id.list);
        prepareCarsList();
    }

    private static final class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int HEADER = 0;
        private static final int ITEM = 1;
        private static final int FOOTER = 2;


        private final UserInfo userInfo;
        private final List<CarHistory> items = new ArrayList<>();

        private HistoryAdapter(UserInfo userInfo, List<GetTrackingHistoryResponse.TrackingHistory> items) {
            this.userInfo = userInfo;
            for (GetTrackingHistoryResponse.TrackingHistory item : items){
                this.items.add(new CarHistory(item.getDate(), item.getCompanyName(), item.getTotalDistance(), item.getTotalAmount()));
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (viewType == FOOTER) {
                CarHistoryFooterViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.car_history_footer_view, parent, false);
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                binding.getRoot().setLayoutParams(lp);
                return new FooterVH(binding);
            } else if (viewType == HEADER) {

                CarHistoryHeaderViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.car_history_header_view, parent, false);
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                binding.getRoot().setLayoutParams(lp);
                return new HeaderVH(binding);
            }


            ListItemHistoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_history, parent, false);

            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            binding.getRoot().setLayoutParams(lp);

            return new HistoryVH(binding);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == HEADER) {
                HeaderVH vh = (HeaderVH) holder;
                vh.bind(userInfo);
                return;
            } else if (getItemViewType(position) == FOOTER) {
                FooterVH vh = (FooterVH) holder;
                vh.bind(userInfo);
                return;
            }
            HistoryVH vh = (HistoryVH) holder;
            CarHistory carHistory = items.get(position - 1);

            vh.bind(carHistory);
        }

        @Override
        public int getItemCount() {
            return items.size() + 2;
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
    }

    private static final class HistoryVH extends RecyclerView.ViewHolder {
        private final ListItemHistoryBinding binding;

        private HistoryVH(ListItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(CarHistory item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }

    private static final class HeaderVH extends RecyclerView.ViewHolder {

        private final CarHistoryHeaderViewBinding binding;

        public HeaderVH(CarHistoryHeaderViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(UserInfo item) {
            binding.setUser(item);
            binding.executePendingBindings();
        }
    }

    private static final class FooterVH extends RecyclerView.ViewHolder {

        private final CarHistoryFooterViewBinding binding;

        public FooterVH(CarHistoryFooterViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(UserInfo item) {
            binding.setUser(item);
            binding.executePendingBindings();
        }
    }

    public static final class CarHistory {
        private static final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        public final ObservableField<String> date = new ObservableField<>();
        public final ObservableField<String> companyName = new ObservableField<>();
        public final ObservableField<Double> distancePassed = new ObservableField<>();
        public final ObservableField<Integer> earnedPoints = new ObservableField<>();


        public CarHistory(Date date, String companyName, double distancePassed, int earnedPoints) {
            this.date.set(dateFormat.format(date));
            this.companyName.set(companyName);
            this.distancePassed.set(distancePassed);
            this.earnedPoints.set(earnedPoints);
        }
    }
}
