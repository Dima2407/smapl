package com.smapl_android.core.validation;

import android.text.TextUtils;

public class CarModelValidator extends BaseValidator<String>{

    private final String errorMessageEmpty;

    public CarModelValidator(String errorMessageEmpty) {
        this.errorMessageEmpty = errorMessageEmpty;
    }

    @Override
    public boolean validate(String value) throws ValidationException {

        if (TextUtils.isEmpty(value)) {
            throw new ValidationException(errorMessageEmpty);
        }

        return true;
    }
}
