package com.smapl_android.core.validation;

import android.content.Context;
import com.smapl_android.R;

public final class Validators {
    private static PhoneValidator phoneValidator;
    private static PasswordValidator passwordValidator;

    private Validators() {
    }

    public static Validator<String> getPhoneValidator(Context context) {
        if (phoneValidator == null) {
            phoneValidator = new PhoneValidator(context.getString(R.string.error_empty_phone_number),
                    context.getString(R.string.error_phone_number_short),
                    context.getString(R.string.error_wrong_phone_number));
        }
        return phoneValidator;
    }

    public static Validator<String> getPasswordValidator(Context context) {
        if (passwordValidator == null) {
            passwordValidator = new PasswordValidator(context.getString(R.string.error_empty_password),
                    context.getString(R.string.error_password_short));
        }
        return passwordValidator;
    }
}
