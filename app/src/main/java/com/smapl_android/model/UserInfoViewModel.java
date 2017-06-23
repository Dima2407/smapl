package com.smapl_android.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextWatcher;

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

    public final TextWatcher phoneTextWatcher = new TextWatcherAdapter(phone);
    public final TextWatcher passwordTextWatcher = new TextWatcherAdapter(password);
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

    public UserInfoViewModel(Context context) {
        this.context = context;
    }

    protected UserInfoViewModel(Parcel in) {
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