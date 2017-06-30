package com.smapl_android.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;

import com.smapl_android.R;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.net.responses.UserResponse;

import java.util.Objects;

public class UserInfoViewModel extends BaseObservable implements Parcelable {
    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<Boolean> gender = new ObservableField<>();
    public final ObservableField<String> age = new ObservableField<>();
    public final ObservableField<String> carBrand = new ObservableField<>();
    public final ObservableField<String> carModel = new ObservableField<>();
    public final ObservableField<String> carYearOfIssue = new ObservableField<>();
    public final ObservableField<String> color = new ObservableField<>();
    public final ObservableField<String> interests = new ObservableField<>();
    public final ObservableField<Bitmap> carPhoto = new ObservableField<>();
    public final ObservableField<Drawable> phoneValid = new ObservableField<>();
    public final ObservableField<Drawable> passwordValid = new ObservableField<>();

    public final ObservableField<String> oldPhone = new ObservableField<>();
    public final ObservableField<String> oldName = new ObservableField<>();
    public final ObservableField<String> oldAge = new ObservableField<>();
    public final ObservableField<String> oldInterests = new ObservableField<>();

    //public final TextWatcher phoneTextWatcher = new TextWatcherAdapter(phone);
    //public final TextWatcher passwordTextWatcher = new TextWatcherAdapter(password);
    public final TextWatcher nameTextWatcher = new TextWatcherAdapter(name);
    public final TextWatcher emailTextWatcher = new TextWatcherAdapter(email);
   // public final TextWatcher genderTextWatcher = new TextWatcherAdapter(gender);
    public final TextWatcher ageTextWatcher = new TextWatcherAdapter(age);
    public final TextWatcher carBrandTextWatcher = new TextWatcherAdapter(carBrand);
    public final TextWatcher carModelTextWatcher = new TextWatcherAdapter(carModel);
    public final TextWatcher carColorTextWatcher = new TextWatcherAdapter(color);
    public final TextWatcher carYearTextWatcher = new TextWatcherAdapter(carYearOfIssue);
    public final TextWatcher interestsTextWatcher = new TextWatcherAdapter(interests);

    private Context context;

    private Drawable markDrawable;

    public final TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updatePasswordValidationMark(s);
            if (!Objects.equals(password.get(), s.toString())) {
                password.set(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public final TextWatcher phoneTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updatePhoneValidationMark(s);
            if (!Objects.equals(phone.get(), s.toString())) {
                phone.set(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    public UserInfoViewModel(Context context) {
        this.context = context;
    }

    protected UserInfoViewModel(Parcel in) {
    }

    private Drawable getMarkDrawable() {
        if (markDrawable == null) {
            markDrawable = context.getResources().getDrawable(R.drawable.edit_text_mark);
        }
        return markDrawable;
    }

    private void updatePasswordValidationMark(CharSequence s){
        updateValidationMark(s, Validators.getPasswordValidator(context), passwordValid);
    }

    private void updatePhoneValidationMark(CharSequence s){
        updateValidationMark(s, Validators.getPhoneValidator(context), phoneValid);
    }

    private void updateValidationMark(CharSequence field, Validator<String> validator, ObservableField<Drawable> mark){
        try {
            boolean correct = validator.validate(field.toString());
            if(correct){
                mark.set(getMarkDrawable());
            }else {
                mark.set(null);
            }
        } catch (ValidationException e) {
            mark.set(null);
        }
    }

    public void apply(UserResponse response){
        oldName.set(response.getName());
        carBrand.set(response.getCarMark());
        carModel.set(response.getCarModel());
        color.set(response.getCarColor());
        carYearOfIssue.set(String.valueOf(response.getCarYear()));
        oldPhone.set(response.getMobileNumber());
        oldInterests.set(response.getInterests());
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
    }
}