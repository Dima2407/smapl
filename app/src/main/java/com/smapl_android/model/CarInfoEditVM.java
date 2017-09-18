package com.smapl_android.model;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.text.TextWatcher;

import com.smapl_android.R;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.UserResponse;

public class CarInfoEditVM extends BaseObservable {

    public final ObservableField<String> carBrand = new ObservableField<>();

    public final ObservableField<String> carModel = new ObservableField<>();

    public final ObservableField<String> carColor = new ObservableField<>();

    public final ObservableField<String> carYear = new ObservableField<>();


    //region errors
    public final ObservableField<String> carBrandError = new ObservableField<>();
    public final ObservableField<String> carModelError = new ObservableField<>();
    public final ObservableField<String> carColorError = new ObservableField<>();
    public final ObservableField<String> carYearError = new ObservableField<>();
    private Validator<String> carModelValidator;
    private Validator<String> carYearValidator;
    private Validator<String> carBrandValidator;
    private Validator<String> carColorValidator;

    //endregion
    public void init(Context context) {
        carModelValidator = Validators.getCarModelValidator(context);
        carYearValidator = Validators.getCarYearValidator(context);
        carBrandValidator = Validators.getCarBrandValidator(context);
        carColorValidator = Validators.getCarColorValidator(context);
        carModel.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                try {
                    if (carModelValidator.validate(carModel.get())) {
                        carModelError.set("");
                    }
                } catch (ValidationException e) {
                    carModelError.set(e.getMessage());
                }
            }
        });
        carYear.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                try {
                    if (carYearValidator.validate(carYear.get())) {
                        carYearError.set("");
                    }
                } catch (ValidationException e) {
                    carYearError.set(e.getMessage());
                }
            }
        });
        carBrand.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                try {
                    if (carBrandValidator.validate(carBrand.get())) {
                        carBrandError.set("");
                    }
                } catch (ValidationException e) {
                    carBrandError.set(e.getMessage());
                }
            }
        });
        carColor.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                try {
                    if (carColorValidator.validate(carColor.get())) {
                        carColorError.set("");
                    }
                } catch (ValidationException e) {
                    carColorError.set(e.getMessage());
                }
            }
        });

    }

    public void apply(UserInfo userInfo) {
        carBrand.set(userInfo.carBrand.get());
        carModel.set(userInfo.carModel.get());
        carColor.set(userInfo.carColor.get());
        carYear.set(userInfo.carYear.get());
    }

    public UpdateCarRequest toUpdateCar() {
        UpdateCarRequest request = new UpdateCarRequest();
        request.setCarMark(carBrand.get());
        request.setCarColor(carColor.get());
        request.setCarYear(Integer.parseInt(carYear.get()));
        request.setCarModel(carModel.get());
        return request;
    }

}
