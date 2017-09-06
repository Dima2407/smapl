package com.smapl_android.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smapl_android.R;
import com.smapl_android.ui.base.BaseFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HistoryFragment extends BaseFragment {

    private ListView carsHistoryListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    private void prepareCarsList() {
        carsHistoryListView = (ListView) getView().findViewById(R.id.cars_History_ListView);

        final ArrayList<CarHistory> carsList = new ArrayList<>();

        Date date1 = getDate(2008,10,23);
        Date date2 = getDate(2009,4,19);
        Date date3 = getDate(2010,11,8);
        Date date4 = getDate(2011,1,2);
        Date date5 = getDate(2012,3,25);

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


        String listItems[] = new String[carsList.size()];


        for (int i = 0; i < carsList.size(); i++) {
            CarHistory carHistory = carsList.get(i);
            listItems[i] = carHistory.companyName;
        }

        CarHistoryAdapter adapter = new CarHistoryAdapter(getContext(),carsList);
        carsHistoryListView.setAdapter(adapter);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareCarsList();
        carsHistoryListView = (ListView)getView().findViewById(R.id.cars_History_ListView);
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.car_history_header_view, null, false);
        View footerView = getActivity().getLayoutInflater().inflate(R.layout.car_history_footer_view, null, false);
        carsHistoryListView.addHeaderView(headerView);
        carsHistoryListView.addFooterView(footerView);
    }

    private final class CarHistory {
        Date date;
        String companyName;
        int distancePassed;
        int earnedPoints;

        public CarHistory() {
            this.date = null;
            this.companyName = null;
            this.distancePassed = 0;
            this.earnedPoints = 0;
        }

        public CarHistory(Date date, String companyName, int distancePassed, int earnedPoints) {
            this.date = date;
            this.companyName = companyName;
            this.distancePassed = distancePassed;
            this.earnedPoints = earnedPoints;
        }
    }

    private class CarHistoryAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private ArrayList<CarHistory> dataSource;

        public CarHistoryAdapter(Context context, ArrayList<CarHistory> items) {
            this.context = context;
            this.dataSource = items;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return this.dataSource.size();
        }

        @Override
        public Object getItem(int position) {
            return this.dataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get view for row item
            View rowView = this.inflater.inflate(R.layout.list_item_carhistory, parent, false);

            TextView dateTextView =
                    (TextView) rowView.findViewById(R.id.car_history_date_TextView);

            TextView companyNameTextView =
                    (TextView) rowView.findViewById(R.id.car_history_company_name_TextView);

            TextView distancePassedTextView =
                    (TextView) rowView.findViewById(R.id.car_history_passed_distance_TextView);

            TextView earnedPointsTextView =
                    (TextView) rowView.findViewById(R.id.car_history_earned_points_TextView);


            CarHistory carHistory = (CarHistory) getItem(position);

            SimpleDateFormat simpleDate =  new SimpleDateFormat("yyyy MMMM dd");

            String strDt = simpleDate.format(carHistory.date);
            dateTextView.setText(strDt);
            companyNameTextView.setText(carHistory.companyName);
            distancePassedTextView.setText(""+carHistory.distancePassed);
            earnedPointsTextView.setText(""+carHistory.earnedPoints);

            return rowView;
        }
    }

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
}
