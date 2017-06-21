package com.smapl_android.core.validation;

import android.text.TextUtils;

public class PasswordValidator extends BaseValidator<String> {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final String errorMessageEmpty;
    private final String errorMessageLength;

    PasswordValidator(String errorMessageEmpty, String errorMessageLength) {
        this.errorMessageEmpty = errorMessageEmpty;
        this.errorMessageLength = errorMessageLength;
    }

    @Override
    public boolean validate(String value) throws ValidationException {
        if (TextUtils.isEmpty(value)) {
            throw new ValidationException(errorMessageEmpty);
        }
        if (value.length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException(errorMessageLength);
        }
        return true;
    }
}
