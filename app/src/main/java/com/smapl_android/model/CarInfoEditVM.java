package com.smapl_android.model;

import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.text.TextWatcher;
import com.smapl_android.R;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.UserResponse;

public class CarInfoEditVM extends BaseObservable {

    public final ObservableField<String> carBrand = new ObservableField<>();

    public final ObservableField<String> carModel = new ObservableField<>();

    public final ObservableField<String> carColor = new ObservableField<>();

    public final ObservableField<String> carYear = new ObservableField<>();

    public void apply(UserInfo userInfo){
        carBrand.set(userInfo.carBrand.get());
        carModel.set(userInfo.carModel.get());
        carColor.set(userInfo.carColor.get());
        carYear.set(userInfo.carYear.get());
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
