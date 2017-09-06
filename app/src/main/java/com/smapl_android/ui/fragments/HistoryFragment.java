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
import com.smapl_android.databinding.CarHistoryFooterViewBinding;
import com.smapl_android.databinding.CarHistoryHeaderViewBinding;
import com.smapl_android.databinding.ListItemHistoryBinding;
import com.smapl_android.model.UserInfo;
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

        final ArrayList<CarHistory> carsList = new ArrayList<>();

        Date date1 = getDate(2008, 10, 23);
        Date date2 = getDate(2009, 4, 19);
        Date date3 = getDate(2010, 11, 8);
        Date date4 = getDate(2011, 1, 2);
        Date date5 = getDate(2012, 3, 25);

        CarHistory carHistory1 = new CarHistory(date1, "Armani", 123, 678);
        CarHistory carHistory2 = new CarHistory(date2, "Nike", 3, 100);
        CarHistory carHistory3 = new CarHistory(date3, "Adidas", 2000, 13);
        CarHistory carHistory4 = new CarHistory(date4, "Puma", 2000, 12);
        CarHistory carHistory5 = new CarHistory(date5, "Reebok", 2000, 14);


        carsList.add(carHistory1);
        carsList.add(carHistory2);
        carsList.add(carHistory3);
        carsList.add(carHistory4);
        carsList.add(carHistory5);

        HistoryAdapter adapter = new HistoryAdapter(getCoreActivity().getUserInfo(), carsList);
        carsHistoryListView.setAdapter(adapter);
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
        private final List<CarHistory> items;

        private HistoryAdapter(UserInfo userInfo, List<CarHistory> items) {
            this.userInfo = userInfo;
            this.items = items;
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
        public final ObservableField<String> distancePassed = new ObservableField<>();
        public final ObservableField<String> earnedPoints = new ObservableField<>();


        public CarHistory(Date date, String companyName, int distancePassed, int earnedPoints) {
            this.date.set(dateFormat.format(date));
            this.companyName.set(companyName);
            this.distancePassed.set(String.format("%d км", distancePassed));
            this.earnedPoints.set(String.format("%d грн", earnedPoints));
        }
    }
}
