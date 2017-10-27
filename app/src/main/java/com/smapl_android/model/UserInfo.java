package com.smapl_android.model;

import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import com.smapl_android.net.responses.UserResponse;

public class UserInfo extends BaseObservable {

    public final ObservableField<String> name = new ObservableField<>();

    public final ObservableField<String> carBrandModel = new ObservableField<>();

    public final ObservableField<String> carBrand = new ObservableField<>();

    public final ObservableField<String> carModel = new ObservableField<>();

    public final ObservableField<String> carColor = new ObservableField<>();

    public final ObservableField<String> carYear = new ObservableField<>();

    public final ObservableField<String> carPhoto = new ObservableField<>();

    public final ObservableField<Double> earn = new ObservableField<>(0.0);

    public final ObservableField<Double> balance = new ObservableField<>(0.0);

    public final ObservableField<Double> drive = new ObservableField<>(0.0);

    public final ObservableField<Double> currentEarn = new ObservableField<>(0.0);

    public final ObservableField<Double> currentDrive = new ObservableField<>(0.0);

    public final ObservableField<Boolean> inDrive = new ObservableField<>(false);

    public final ObservableField<Double> price = new ObservableField<Double>(0.0);

    private UserResponse response;

    public void apply(Resources resources, UserResponse response){
        this.response = response;
        name.set(response.getName());
        carBrandModel.set(String.format("%s %s", response.getCarMark(), response.getCarModel()));
        carBrand.set(response.getCarMark());
        carModel.set(response.getCarModel());
        carColor.set(response.getCarColor());
        carYear.set(String.valueOf(response.getCarYear()));
        earn.set(response.getTotalAmount());
        balance.set(response.getBalance());
        drive.set(response.getTotalDistance());
        carPhoto.set(response.getCarPhoto());
    }


    public UserResponse getResponse() {
        return response;
    }
}
