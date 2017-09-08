package com.smapl_android.model;

import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.text.TextWatcher;
import com.smapl_android.R;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.UserResponse;

public class UserInfo extends BaseObservable {

    public final ObservableField<String> name = new ObservableField<>();

    public final ObservableField<String> carBrandModel = new ObservableField<>();

    public final ObservableField<String> carBrand = new ObservableField<>();

    public final ObservableField<String> carModel = new ObservableField<>();

    public final ObservableField<String> carColor = new ObservableField<>();

    public final ObservableField<String> carYear = new ObservableField<>();

    public final ObservableField<String> carPhoto = new ObservableField<>();

    public final ObservableField<String> balance = new ObservableField<>();

    public final ObservableField<String> balanceAmount = new ObservableField<>();

    public final ObservableField<String> earn = new ObservableField<>();

    public final ObservableField<String> earnAmount = new ObservableField<>();

    public final ObservableField<String> drive = new ObservableField<>();

    public final ObservableField<String> driveAmount = new ObservableField<>();

    public final ObservableField<Boolean> inDrive = new ObservableField<>();

    private UserResponse response;

    public void apply(Resources resources, UserResponse response){
        this.response = response;
        name.set(response.getName());
        carBrandModel.set(String.format("%s %s", response.getCarMark(), response.getCarModel()));
        carBrand.set(response.getCarMark());
        carModel.set(response.getCarModel());
        carColor.set(response.getCarColor());
        carYear.set(String.valueOf(response.getCarYear()));
        balance.set(resources.getString(R.string.balance_format, 1000));
        balanceAmount.set("1000");
        final int money = 10000;
        earn.set(resources.getString(R.string.total_earn_format, money));
        final int distance = 5000;
        drive.set(resources.getString(R.string.drive_format, distance));
        driveAmount.set(String.format("%d км", distance));
        earnAmount.set(String.format("%d грн", money));
        carPhoto.set(response.getCarPhoto());
    }


    public UserResponse getResponse() {
        return response;
    }
}
