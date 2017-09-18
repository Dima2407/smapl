package com.smapl_android.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.smapl_android.R;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.net.responses.UserResponse;

import org.w3c.dom.Text;

public class PasswordEditVM extends BaseObservable {

    public final ObservableField<String> oldPassword = new ObservableField<>();
    public final ObservableField<String> newPassword = new ObservableField<>();

    public final ObservableField<Boolean> nextActive = new ObservableField<>(false);

    //region errors
    public final ObservableField<String> oldPasswordError = new ObservableField<>();
    public final ObservableField<String> newPasswordError = new ObservableField<>();
    //endregion

    private Validator<String> passwordValidator;
    private String equalsPasswords;
    private OnPropertyChangedCallback errorsWatcher;

    public void init(Context context) {
        passwordValidator = Validators.getPasswordValidator(context);
        equalsPasswords = context.getString(R.string.error_password_same);
        oldPassword.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                try {
                    if (passwordValidator.validate(oldPassword.get())) {
                        if (oldPassword.get().equals(newPassword.get())) {
                            newPasswordError.set(equalsPasswords);
                        } else {
                            oldPasswordError.set("");
                        }
                    }
                } catch (ValidationException e) {
                    oldPasswordError.set(e.getMessage());
                }
            }
        });
        newPassword.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                try {
                    if (passwordValidator.validate(newPassword.get())) {
                        if (newPassword.get().equals(oldPassword.get())) {
                            newPasswordError.set(equalsPasswords);
                        } else {
                            newPasswordError.set("");
                        }
                    }
                } catch (ValidationException e) {
                    newPasswordError.set(e.getMessage());
                }
            }
        });

        errorsWatcher = new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                nextActive.set(TextUtils.isEmpty(oldPasswordError.get()) && TextUtils.isEmpty(newPasswordError.get()));
            }
        };
        oldPasswordError.addOnPropertyChangedCallback(errorsWatcher);
        newPasswordError.addOnPropertyChangedCallback(errorsWatcher);
    }

}
