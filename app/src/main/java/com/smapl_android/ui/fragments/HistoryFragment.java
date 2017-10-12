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
import com.smapl_android.databinding.FragmentHistoryBinding;
import com.smapl_android.databinding.ListItemHistoryBinding;
import com.smapl_android.net.responses.GetTrackingHistoryResponse;
import com.smapl_android.ui.base.BaseFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryFragment extends BaseFragment {

    private static final int AMOUNT_OF_ITEMS_ON_PAGE = 10;

    private RecyclerView carsHistoryListView;
    private List<GetTrackingHistoryResponse.TrackingHistory> historyList = new ArrayList<>();
    private int currentPage = 1;
    private boolean isLastPage = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHistoryBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        dataBinding.setUser(getCoreActivity().getUserInfo());
        return dataBinding.getRoot();
    }


    private void prepareCarsList() {

        final CoreRequest<List<GetTrackingHistoryResponse.TrackingHistory>> request = getCoreActivity().newWaitingRequest(new SuccessOutput<List<GetTrackingHistoryResponse.TrackingHistory>>() {
            @Override
            public void onSuccess(List<GetTrackingHistoryResponse.TrackingHistory> result) {
                if (result.size() > 0) {
                    historyList.addAll(result);
                    HistoryAdapter adapter = new HistoryAdapter(historyList);
                    carsHistoryListView.setAdapter(adapter);
                } else {
                    isLastPage = true;
                    return;
                }
            }
        });
        getCoreService().getHistory(currentPage, request);
        currentPage++;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carsHistoryListView = (RecyclerView) view.findViewById(android.R.id.list);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        carsHistoryListView.setLayoutManager(layoutManager);
        prepareCarsList();
        carsHistoryListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!isLastPage) {
                    int currentPosition = ((HistoryAdapter) recyclerView.getAdapter()).getMaxScrolledPosition();
                    int positionOnPage = currentPosition - (currentPage - 2) * AMOUNT_OF_ITEMS_ON_PAGE;

                    if (positionOnPage == AMOUNT_OF_ITEMS_ON_PAGE - 3) {
                        prepareCarsList();
                    }
                }
            }
        });
    }

    private static final class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final List<CarHistory> items = new ArrayList<>();
        private int maxScrolledPosition = 0;

        private HistoryAdapter(List<GetTrackingHistoryResponse.TrackingHistory> items) {
            for (GetTrackingHistoryResponse.TrackingHistory item : items) {
                this.items.add(new CarHistory(item.getDate(), item.getCompanyName(), item.getTotalDistance(), item.getTotalAmount()));
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            ListItemHistoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_history, parent, false);

            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            binding.getRoot().setLayoutParams(lp);

            return new HistoryVH(binding);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            HistoryVH vh = (HistoryVH) holder;
            CarHistory carHistory = items.get(position);

            if (position > maxScrolledPosition) {
                maxScrolledPosition = position;
            }

            vh.bind(carHistory);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public int getMaxScrolledPosition() {
            return maxScrolledPosition;
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

    public static final class CarHistory {
        private static final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        public final ObservableField<String> date = new ObservableField<>();
        public final ObservableField<String> companyName = new ObservableField<>();
        public final ObservableField<Double> distancePassed = new ObservableField<>();
        public final ObservableField<Double> earnedPoints = new ObservableField<>();


        public CarHistory(Date date, String companyName, double distancePassed, double earnedPoints) {
            this.date.set(dateFormat.format(date));
            this.companyName.set(companyName);
            this.distancePassed.set(distancePassed);
            this.earnedPoints.set(earnedPoints);
        }
    }
}
