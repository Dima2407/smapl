package com.smapl_android.core.validation;

import android.text.TextUtils;
import android.util.Patterns;

public class EmailValidator extends BaseValidator<String> {

    private final String errorMessageEmpty;
    private final String errorMessageContent;

    public EmailValidator(String errorMessageEmpty, String errorMessageContent) {
        this.errorMessageEmpty = errorMessageEmpty;
        this.errorMessageContent = errorMessageContent;
    }

    @Override
    public boolean validate(String value) throws ValidationException {
        if (TextUtils.isEmpty(value)) {
            throw new ValidationException(errorMessageEmpty);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            throw new ValidationException(errorMessageContent);
        }

        return true;
    }
}
