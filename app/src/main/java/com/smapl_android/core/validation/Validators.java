package com.smapl_android.core.validation;

import android.content.Context;
import com.smapl_android.R;

public final class Validators {
    private static PhoneValidator phoneValidator;
    private static PasswordValidator passwordValidator;
    private static NameValidator nameValidator;
    private static EmailValidator emailValidator;
    private static CarModelValidator carModelValidator;
    private static CarYearValidator carYearValidator;
    private static GenericEmptyValidator genderValidator;
    private static GenericEmptyValidator ageValidator;

    private Validators() {
    }

    public static Validator<String> getPhoneValidator(Context context) {
        if (phoneValidator == null) {
            phoneValidator = new PhoneValidator(context.getString(R.string.error_empty_phone_number),
                    context.getString(R.string.error_phone_number_lenght),
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

    public static Validator<String> getNameValidator(Context context){
        if (nameValidator == null){
            nameValidator = new NameValidator(context.getString(R.string.error_empty_name));
        }
        return nameValidator;
    }

    public static Validator<String> getEmailValidator(Context context){
        if (emailValidator == null){
            emailValidator = new EmailValidator(context.getString(R.string.error_empty_email),
                    context.getString(R.string.error_incorrect_email));
        }
        return emailValidator;
    }

    public static Validator<String> getCarModelValidator(Context context){
        if (carModelValidator == null){
            carModelValidator = new CarModelValidator(context.getString(R.string.error_empty_car_model));
        }
        return carModelValidator;
    }

    public static Validator<String> getCarYearValidator(Context context){
        if (carYearValidator == null) {
            carYearValidator = new CarYearValidator(context.getString(R.string.error_empty_car_year),
                    context.getString(R.string.error_incorrect_car_year));
        }
        return carYearValidator;
    }

    public static Validator<String> getGenderValidator(Context context){
        if(genderValidator == null){
            genderValidator = new GenericEmptyValidator(context.getString(R.string.error_empty_gender));
        }
        return genderValidator;
    }

    public static Validator<String> getAgeValidator(Context context) {
        if( ageValidator== null){
            ageValidator = new GenericEmptyValidator(context.getString(R.string.error_empty_age));
        }
        return ageValidator;
    }
}
