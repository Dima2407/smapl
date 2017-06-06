package com.smapl_android.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.Objects;

public class LoginInfo extends BaseObservable {
    private ObservableField<String> phone = new ObservableField<>();
    private TextWatcher phoneTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!Objects.equals(phone.get(), s.toString())) {
                phone.set(s.toString());
            }
        }
    };
    private ObservableField<String> password = new ObservableField<>();
    private TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!Objects.equals(password.get(), s.toString())) {
                password.set(s.toString());
            }
        }
    };

    @Bindable
    public ObservableField<String> getPhone() {
        return phone;
    }

    @Bindable
    public ObservableField<String> getPassword() {
        return password;
    }

    @Bindable
    public TextWatcher getPhoneTextWatcher() {
        return phoneTextWatcher;
    }

    @Bindable
    public TextWatcher getPasswordTextWatcher() {
        return passwordTextWatcher;
    }
}
