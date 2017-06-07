package com.smapl_android.core.validation;

import android.text.TextUtils;
import android.util.Patterns;

public class PhoneValidator extends BaseValidator<String> {
    private static final int MIN_PHONE_NUMBER_LENGTH = 4;
    private final String errorMessageEmpty;
    private final String errorMessageLength;
    private final String errorMessageContent;

    PhoneValidator(String errorMessageEmpty, String errorMessageLength, String errorMessageContent) {
        this.errorMessageEmpty = errorMessageEmpty;
        this.errorMessageLength = errorMessageLength;
        this.errorMessageContent = errorMessageContent;
    }

    @Override
    public boolean validate(String value) throws ValidationException {
        if (TextUtils.isEmpty(value)) {
            throw new ValidationException(errorMessageEmpty);
        }
        if (value.length() < MIN_PHONE_NUMBER_LENGTH) {
            throw new ValidationException(errorMessageLength);
        }

        if (!Patterns.PHONE.matcher(value).matches()) {
            throw new ValidationException(errorMessageContent);
        }
        return true;
    }
}
