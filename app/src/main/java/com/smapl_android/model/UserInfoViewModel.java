package com.smapl_android.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseBooleanArray;

import com.smapl_android.R;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.net.requests.RegistrationRequest;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class UserInfoViewModel extends BaseObservable implements Parcelable {

    private static final String GENDER_MAN = "male";
    private static final String GENDER_WOMAN = "female";

    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> gender = new ObservableField<>();
    public final ObservableField<String> age = new ObservableField<>();
    public final ObservableField<String> carBrand = new ObservableField<>();
    public final ObservableField<String> carModel = new ObservableField<>();
    public final ObservableField<String> carYear = new ObservableField<>();
    public final ObservableField<String> carColor = new ObservableField<>();
    public final ObservableField<String> interests = new ObservableField<>();

    public final ObservableField<String> carModelError = new ObservableField<>();
    public final ObservableField<String> nameError = new ObservableField<>();
    public final ObservableField<String> carYearError = new ObservableField<>();
    public final ObservableField<String> emailError = new ObservableField<>();
    public final ObservableField<String> genderError = new ObservableField<>();
    public final ObservableField<String> ageError = new ObservableField<>();
    public final ObservableField<String> carBrandError = new ObservableField<>();
    public final ObservableField<String> carColorError = new ObservableField<>();

    public final ObservableField<Boolean> nextActive = new ObservableField<>(false);
    private Validator<String> nameValidator;
    private Validator<String> emailValidator;
    private Validator<String> carModelValidator;
    private Validator<String> carYearValidator;
    private Validator<String> carBrandValidator;
    private Validator<String> carColorValidator;
    private Validator<String> genderValidator;
    private Validator<String> ageValidator;

    private SparseBooleanArray errorSum = new SparseBooleanArray();

    public UserInfoViewModel() {
    }

    protected UserInfoViewModel(Parcel in) {
        phone.set(in.readString());
        password.set(in.readString());
        name.set(in.readString());
        email.set(in.readString());
        gender.set(in.readString());
        age.set(in.readString());
        carBrand.set(in.readString());
        carModel.set(in.readString());
        carYear.set(in.readString());
        carColor.set(in.readString());
        interests.set(in.readString());
    }

    public void init(Context context) {
        nameValidator = Validators.getNameValidator(context);
        emailValidator = Validators.getEmailValidator(context);
        genderValidator = Validators.getGenderValidator(context);
        ageValidator = Validators.getAgeValidator(context);
        carModelValidator = Validators.getCarModelValidator(context);
        carYearValidator = Validators.getCarYearValidator(context);
        carBrandValidator = Validators.getCarBrandValidator(context);
        carColorValidator = Validators.getCarColorValidator(context);

        addErrorWatching(name, nameError, nameValidator);
        addErrorWatching(email, emailError, emailValidator);
        addErrorWatching(gender, genderError, genderValidator);
        addErrorWatching(age, ageError, ageValidator);

        addErrorWatching(carBrand, carBrandError, carBrandValidator);
        addErrorWatching(carModel, carModelError, carModelValidator);
        addErrorWatching(carYear, carYearError, carYearValidator);
        addErrorWatching(carColor, carColorError, carColorValidator);

    }

    private void addErrorWatching(final ObservableField<String> field, final ObservableField<String> fieldError, final Validator<String> fieldValidator) {
        final int key = errorSum.size();
        errorSum.append(key, false);
        field.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

                try {
                    if (fieldValidator.validate(field.get())) {
                        fieldError.set("");
                        errorSum.put(key, true);
                        updateErrorSum();
                    }
                } catch (ValidationException e) {
                    fieldError.set(e.getMessage());
                    errorSum.put(key, false);
                    updateErrorSum();
                }
            }
        });
    }

    private void updateErrorSum(){
        for(int i = 0; i < errorSum.size(); i++){
            int key = errorSum.keyAt(i);
            if(!errorSum.get(key)){
                nextActive.set(false);
                return;
            }
        }
        nextActive.set(true);
    }

    public static final Creator<UserInfoViewModel> CREATOR = new Creator<UserInfoViewModel>() {
        @Override
        public UserInfoViewModel createFromParcel(Parcel in) {
            return new UserInfoViewModel(in);
        }

        @Override
        public UserInfoViewModel[] newArray(int size) {
            return new UserInfoViewModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(phone.get());
        dest.writeString(password.get());
        dest.writeString(name.get());
        dest.writeString(email.get());
        dest.writeString(gender.get());
        dest.writeString(age.get());
        dest.writeString(carBrand.get());
        dest.writeString(carModel.get());
        dest.writeString(carYear.get());
        dest.writeString(carColor.get());
        dest.writeString(interests.get());
    }

    public RegistrationRequest toRegistration(Context context) {

        RegistrationRequest request = new RegistrationRequest();
        request.setEmail(email.get());
        request.setName(name.get());
        request.setPassword(password.get());
        request.setPhoneNumber(phone.get());
        if (!TextUtils.isEmpty(age.get())) {
            request.setAge(age.get());
        }
        if (context.getString(R.string.man).equalsIgnoreCase(gender.get())) {
            request.setGender(GENDER_MAN);
        }else {
            request.setGender(GENDER_WOMAN);
        }
        if(!TextUtils.isEmpty(interests.get())){
            request.setInterests(interests.get());
        }else {
            request.setInterests("");
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfoViewModel", Context.MODE_PRIVATE);
        String registration_id = sharedPreferences.getString("registration_id", "Bad RegistationID");

        request.setCarMark(carBrand.get());
        request.setCarColor(carColor.get());
        request.setCarYear(Integer.parseInt(carYear.get()));
        request.setCarModel(carModel.get());
        request.setRegistrationId(registration_id);

        return request;
    }
}