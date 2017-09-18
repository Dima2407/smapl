package com.smapl_android.core.validation;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class GenericEmptyValidator extends BaseValidator<String> {
    private final String errorMessageEmpty;

    GenericEmptyValidator(String errorMessageEmpty) {
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
