package com.smapl_android.core.validation;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

public class PhoneValidator extends BaseValidator<String> {
    private static final int PHONE_NUMBER_LENGTH = 13;
    private final String errorMessageEmpty;
    private final String errorMessageLength;
    private final String errorMessageContent;
    private static final Pattern PATTERN = Pattern.compile("^(\\+380\\d{9})$");

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

        if (value.length() != PHONE_NUMBER_LENGTH) {
            throw new ValidationException(errorMessageLength);
        }

        if (!PATTERN.matcher(value).matches()) {
            throw new ValidationException(errorMessageContent);
        }
        return true;
    }
}
