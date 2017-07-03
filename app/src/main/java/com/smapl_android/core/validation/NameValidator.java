package com.smapl_android.core.validation;

import android.text.TextUtils;

import com.smapl_android.R;

public class NameValidator extends BaseValidator<String> {

    private final String errorMessageEmpty;

    public NameValidator(String errorMessageEmpty) {
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
