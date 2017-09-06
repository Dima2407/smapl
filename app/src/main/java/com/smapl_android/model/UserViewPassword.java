package com.smapl_android.model;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.text.TextWatcher;

import com.smapl_android.net.responses.UserResponse;

public class UserViewPassword extends BaseObservable{

    public final ObservableField<String> oldPassword = new ObservableField<>();
    public final ObservableField<String> newPassword = new ObservableField<>();

    public final TextWatcher oldPasswordTextWatcher = new TextWatcherAdapter(oldPassword);
    public final TextWatcher newPasswordTextWatcher = new TextWatcherAdapter(newPassword);

}
