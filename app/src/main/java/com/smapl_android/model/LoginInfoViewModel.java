package com.smapl_android.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import com.smapl_android.R;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;

import java.util.Objects;

public class LoginInfoViewModel extends BaseObservable {
    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableField<Drawable> phoneValid = new ObservableField<>();
    public final ObservableField<Drawable> passwordValid = new ObservableField<>();
    private final Context context;
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

    public LoginInfoViewModel(Context context) {
        this.context = context;
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
}
