package com.smapl_android.core.validation;

import android.text.TextUtils;

class CardAmountValidator extends BaseValidator<String> {
    private final String errorMessageEmpty;

    CardAmountValidator(String errorMessageEmpty) {
        this.errorMessageEmpty = errorMessageEmpty;
    }

    @Override
    public boolean validate(String value) throws ValidationException {
        if (TextUtils.isEmpty(value)) {
            throw new ValidationException(errorMessageEmpty);
        }
        try {
            Double data = Double.valueOf(value);
            if (data <= 0) {
                throw new ValidationException(errorMessageEmpty);
            }
        } catch (NumberFormatException e) {
            throw new ValidationException(errorMessageEmpty);
        }
        return true;
    }
}
