package com.smapl_android.core.validation;


import android.text.TextUtils;

class CardNumberValidator extends BaseValidator<String> {

    private final String errorMessageEmpty;
    private final String errorMessageContent;

    public CardNumberValidator(String errorMessageEmpty, String errorMessageContent) {
        this.errorMessageEmpty = errorMessageEmpty;
        this.errorMessageContent = errorMessageContent;
    }

    @Override
    public boolean validate(String value) throws ValidationException {

        if (TextUtils.isEmpty(value)) {
            throw new ValidationException(errorMessageEmpty);
        }

        if (value.length() < 16)
            throw new ValidationException(errorMessageContent);

        return true;
    }
}
