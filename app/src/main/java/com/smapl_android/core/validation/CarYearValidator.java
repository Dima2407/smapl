package com.smapl_android.core.validation;

import android.text.TextUtils;

public class CarYearValidator extends BaseValidator<String>{

    private static final int MIN_YEAR_ISSUE = 1900;
    private static final int CURRENT_YEAR = 2017;

    private final String errorMessageEmpty;
    private final String errorMessageContent;

    public CarYearValidator(String errorMessageEmpty, String errorMessageContent) {
        this.errorMessageEmpty = errorMessageEmpty;
        this.errorMessageContent = errorMessageContent;
    }

    @Override
    public boolean validate(String value) throws ValidationException {

        if (TextUtils.isEmpty(value)) {
            throw new ValidationException(errorMessageEmpty);
        }

        int year = Integer.parseInt(value);
        if (year < MIN_YEAR_ISSUE || year > CURRENT_YEAR)
            throw new ValidationException(errorMessageContent);

        return true;
    }
}
