package com.smapl_android.model;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.text.TextWatcher;

import com.smapl_android.net.responses.UserResponse;

public class PasswordEditVM extends BaseObservable{

    public final ObservableField<String> oldPassword = new ObservableField<>();
    public final ObservableField<String> newPassword = new ObservableField<>();

}
