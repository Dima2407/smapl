package com.smapl_android.model;


import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;

public class CardEditVM {

    public final ObservableField<String> user = new ObservableField<>();

    public final ObservableField<String> number = new ObservableField<>();

    public final ObservableField<String> bank = new ObservableField<>();


    public final ObservableField<String> userError = new ObservableField<>();

    public final ObservableField<String> numberError = new ObservableField<>();

    public final ObservableField<String> bankError = new ObservableField<>();

    public final ObservableField<Boolean> nextActive = new ObservableField<>(false);

    private Observable.OnPropertyChangedCallback errorWatcher;
    private Validator<String> cardUserValidator;
    private Validator<String> cardBankValidator;
    private Validator<String> cardNumberValidator;

    public void init(Context context) {
        cardUserValidator = Validators.getCardUserValidator(context);
        cardNumberValidator = Validators.getCardNumberValidator(context);
        cardBankValidator = Validators.getCardBankValidator(context);
        user.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                try {
                    if (cardUserValidator.validate(user.get())) {
                        userError.set("");
                    }
                } catch (ValidationException e) {
                    userError.set(e.getMessage());
                }
            }
        });
        number.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                try {
                    if (cardNumberValidator.validate(number.get())) {
                        numberError.set("");
                    }
                } catch (ValidationException e) {
                    numberError.set(e.getMessage());
                }
            }
        });
        bank.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                try {
                    if (cardBankValidator.validate(bank.get())) {
                        bankError.set("");
                    }
                } catch (ValidationException e) {
                    bankError.set(e.getMessage());
                }
            }
        });

        errorWatcher = new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                nextActive.set(TextUtils.isEmpty(userError.get())
                        && TextUtils.isEmpty(numberError.get())
                        && TextUtils.isEmpty(bankError.get()));
            }
        };
        userError.addOnPropertyChangedCallback(errorWatcher);
        numberError.addOnPropertyChangedCallback(errorWatcher);
        bankError.addOnPropertyChangedCallback(errorWatcher);

    }

}
