package com.smapl_android.model;

import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import com.smapl_android.R;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;

public class AuthVM {

    public final ObservableField<Boolean> login = new ObservableField<>(true);

    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();


    public final ObservableField<Drawable> phoneValid = new ObservableField<>();
    public final ObservableField<Drawable> passwordValid = new ObservableField<>();

    public final ObservableField<Boolean> phoneFocused = new ObservableField<>();
    public final ObservableField<Boolean> passwordFocused = new ObservableField<>();

    public final ObservableField<Boolean> inputsValid = new ObservableField<>();

    private Drawable markDrawable;
    private Validator<String> phoneValidator;
    private Validator<String> passwordValidator;

    {
        phone.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                ObservableField<String> phone = (ObservableField<String>) sender;
                updatePhoneValidationMark(phone.get());
            }
        });

        password.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                ObservableField<String> password = (ObservableField<String>) sender;
                updatePasswordValidationMark(password.get());
            }
        });
        login.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                phone.set("");
                password.set("");
            }
        });
    }


    private void updatePasswordValidationMark(CharSequence s) {
        updateValidationMark(s, passwordValidator, passwordValid);
    }

    private void updatePhoneValidationMark(CharSequence s) {
        updateValidationMark(s, phoneValidator, phoneValid);
    }

    private void updateValidationMark(CharSequence field, Validator<String> validator, ObservableField<Drawable> mark) {
        try {
            boolean correct = validator.validate(field.toString());
            if (correct) {
                mark.set(markDrawable);
            } else {
                mark.set(null);
            }
        } catch (ValidationException e) {
            mark.set(null);
        }
        inputsValid.set(phoneValid.get() == markDrawable && passwordValid.get() == markDrawable);
    }

    public void init(Context context) {
        passwordValidator = Validators.getPasswordValidator(context);
        phoneValidator = Validators.getPhoneValidator(context);
        markDrawable = context.getResources().getDrawable(R.drawable.edit_text_mark);
    }

}
