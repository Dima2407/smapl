package com.smapl_android.model;


import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;

public class CardEditVM {

    public final ObservableField<String> amount = new ObservableField<>();

    public final ObservableField<String> number = new ObservableField<>();

    public final ObservableField<String> bank = new ObservableField<>();


    public final ObservableField<String> amountError = new ObservableField<>();

    public final ObservableField<String> numberError = new ObservableField<>();

    public final ObservableField<String> bankError = new ObservableField<>();

    public final ObservableField<Boolean> nextActive = new ObservableField<>(false);

    private Observable.OnPropertyChangedCallback errorWatcher;
    private Validator<String> cardAmountValidator;
    private Validator<String> cardBankValidator;
    private Validator<String> cardNumberValidator;

    public void init(Context context) {
        cardAmountValidator = Validators.getCardAmountValidator(context);
        cardNumberValidator = Validators.getCardNumberValidator(context);
        cardBankValidator = Validators.getCardBankValidator(context);
        amount.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                try {
                    if (cardAmountValidator.validate(amount.get())) {
                        amountError.set("");
                    }
                } catch (ValidationException e) {
                    amountError.set(e.getMessage());
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
                nextActive.set(TextUtils.isEmpty(amountError.get())
                        && TextUtils.isEmpty(numberError.get())
                        && TextUtils.isEmpty(bankError.get()));
            }
        };
        amountError.addOnPropertyChangedCallback(errorWatcher);
        numberError.addOnPropertyChangedCallback(errorWatcher);
        bankError.addOnPropertyChangedCallback(errorWatcher);

    }


    public final double obtainAmount() {
        return Double.valueOf(amount.get());
    }

}
