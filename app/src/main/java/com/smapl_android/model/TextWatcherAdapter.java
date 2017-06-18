package com.smapl_android.model;

import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.Objects;

class TextWatcherAdapter implements TextWatcher {

    private final ObservableField<String> field;

    TextWatcherAdapter(ObservableField<String> field){

        this.field = field;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!Objects.equals(field.get(), s.toString())) {
            field.set(s.toString());
        }
    }
}
