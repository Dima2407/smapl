package com.smapl_android.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.smapl_android.R;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.net.requests.EditProfileRequest;
import com.smapl_android.net.requests.RegistrationRequest;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.UserResponse;

import java.util.Objects;

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
    public final ObservableField<String> carYearOfIssue = new ObservableField<>();
    public final ObservableField<String> carColor = new ObservableField<>();
    public final ObservableField<String> interests = new ObservableField<>();

    public final ObservableField<String> carModelError = new ObservableField<>();
    public final ObservableField<String> nameError = new ObservableField<>();
    public final ObservableField<String> carYearOfIssueError = new ObservableField<>();

    protected UserInfoViewModel(Parcel in) {
        phone.set(in.readString());
        password.set(in.readString());
        name.set(in.readString());
        email.set(in.readString());
        gender.set(in.readString());
        age.set(in.readString());
        carBrand.set(in.readString());
        carModel.set(in.readString());
        carYearOfIssue.set(in.readString());
        carColor.set(in.readString());
        interests.set(in.readString());
    }

    public UserInfoViewModel() {
        carModel.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                carModelError.set("");
            }
        });

        name.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                nameError.set("");
            }
        });

        carYearOfIssue.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                carYearOfIssueError.set("");
            }
        });
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
        dest.writeString(carYearOfIssue.get());
        dest.writeString(carColor.get());
        dest.writeString(interests.get());
    }

    public RegistrationRequest toRegistration(Context context) {

        RegistrationRequest request = new RegistrationRequest();
        request.setName(name.get());
        request.setPhoneNumber(phone.get());
        if (!TextUtils.isEmpty(age.get())) {
            request.setAge(age.get());
        }
        if (context.getString(R.string.man).equalsIgnoreCase(gender.get())) {
            request.setGender(GENDER_MAN);
        }else {
            request.setGender(GENDER_WOMAN);
        }
        request.setInterests(interests.get());

        request.setCarMark(carBrand.get());
        request.setCarColor(carColor.get());
        request.setCarYear(Integer.parseInt(carYearOfIssue.get()));
        request.setCarModel(carModel.get());

        return request;
    }
}