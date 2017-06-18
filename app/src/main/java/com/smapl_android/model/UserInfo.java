package com.smapl_android.model;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.text.TextWatcher;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.UserResponse;

public class UserInfo extends BaseObservable {

    public final ObservableField<String> name = new ObservableField<>();

    public final ObservableField<String> carBrandModel = new ObservableField<>();

    public final ObservableField<String> carBrand = new ObservableField<>();

    public final ObservableField<String> carModel = new ObservableField<>();

    public final ObservableField<String> carColor = new ObservableField<>();

    public final ObservableField<String> carYear = new ObservableField<>();

    public final TextWatcher carBrandTextWatcher = new TextWatcherAdapter(carBrand);

    public final TextWatcher carModelTextWatcher = new TextWatcherAdapter(carModel);

    public final TextWatcher carColorTextWatcher = new TextWatcherAdapter(carColor);

    public final TextWatcher carYearTextWatcher = new TextWatcherAdapter(carYear);

    public void apply(UserResponse response){
        carBrandModel.set(String.format("%s %s", response.getCarMark(), response.getCarModel()));
        carBrand.set(response.getCarMark());
        carModel.set(response.getCarModel());
        carColor.set(response.getCarColor());
        carYear.set(String.valueOf(response.getCarYear()));
    }

    public UpdateCarRequest toUpdateCar(){
        UpdateCarRequest request = new UpdateCarRequest();
        request.setCarMark(carBrand.get());
        request.setCarColor(carColor.get());
        request.setCarYear(Integer.parseInt(carYear.get()));
        request.setCarModel(carModel.get());
        return request;
    }

}
