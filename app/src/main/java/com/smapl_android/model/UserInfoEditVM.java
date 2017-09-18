package com.smapl_android.model;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.util.ArraySet;

import com.smapl_android.R;
import com.smapl_android.core.validation.ValidationException;
import com.smapl_android.core.validation.Validator;
import com.smapl_android.core.validation.Validators;
import com.smapl_android.net.requests.EditProfileRequest;

import org.w3c.dom.Text;

import java.util.Arrays;

public class UserInfoEditVM extends BaseObservable {

    private static final String GENDER_MAN = "male";
    private static final String GENDER_WOMAN = "female";

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<String> gender = new ObservableField<>();
    public final ObservableField<String> age = new ObservableField<>();
    public final ObservableField<String> interests = new ObservableField<>();
    public final ObservableField<Boolean> nextActive = new ObservableField<>(false);

    //region errors
    public final ObservableField<String> phoneError = new ObservableField<>();
    public final ObservableField<String> nameError = new ObservableField<>();
    public final ObservableField<String> genderError = new ObservableField<>();
    public final ObservableField<String> ageError = new ObservableField<>();
    //endregion

    private Validator<String> phoneValidator;
    private Validator<String> nameValidator;
    private Validator<String> genderValidator;
    private Validator<String> ageValidator;
    private String maleText;
    private String femaleText;
    private OnPropertyChangedCallback errorWatcher;

    public void init(Context context) {
        phoneValidator = Validators.getPhoneValidator(context);
        nameValidator = Validators.getNameValidator(context);
        genderValidator = Validators.getGenderValidator(context);
        ageValidator = Validators.getAgeValidator(context);
        maleText = context.getString(R.string.man);
        femaleText = context.getString(R.string.woman);

        phone.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

                try {
                    if (phoneValidator.validate(phone.get())) {
                        phoneError.set("");
                    }
                } catch (ValidationException e) {
                    phoneError.set(e.getMessage());
                }
            }
        });

        name.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

                try {
                    if (nameValidator.validate(name.get())) {
                        nameError.set("");
                    }
                } catch (ValidationException e) {
                    nameError.set(e.getMessage());
                }
            }
        });
        gender.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                try {
                    if (genderValidator.validate(gender.get())) {
                        genderError.set("");
                    }
                } catch (ValidationException e) {
                    genderError.set(e.getMessage());
                }
            }
        });

        age.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                try {
                    if (ageValidator.validate(age.get())) {
                        ageError.set("");
                    }
                } catch (ValidationException e) {
                    ageError.set(e.getMessage());
                }
            }
        });

        errorWatcher = new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                nextActive.set(TextUtils.isEmpty(phoneError.get())
                        && TextUtils.isEmpty(nameError.get())
                        && TextUtils.isEmpty(genderError.get())
                        && TextUtils.isEmpty(ageError.get()));
            }
        };
        phoneError.addOnPropertyChangedCallback(errorWatcher);
        nameError.addOnPropertyChangedCallback(errorWatcher);
        ageError.addOnPropertyChangedCallback(errorWatcher);
        genderError.addOnPropertyChangedCallback(errorWatcher);
    }


    public void apply(UserInfo userInfo) {
        name.set(userInfo.name.get());
        if (GENDER_MAN.equalsIgnoreCase(userInfo.getResponse().getGender())) {
            gender.set(maleText);
        } else if (GENDER_WOMAN.equalsIgnoreCase(userInfo.getResponse().getGender())) {
            gender.set(femaleText);
        } else {
            gender.set("");
        }
        phone.set(userInfo.getResponse().getMobileNumber());
        age.set(userInfo.getResponse().getAge());
        interests.set(TextUtils.join(",", userInfo.getResponse().getInterests()));
    }

    public EditProfileRequest toUpdateRequest() {
        EditProfileRequest request = new EditProfileRequest();
        request.setName(name.get());
        request.setPhone(phone.get());
        if (!TextUtils.isEmpty(age.get())) {
            request.setAge(age.get());
        }
        if (maleText.equalsIgnoreCase(gender.get())) {
            request.setGender(GENDER_MAN);
        }
        if (femaleText.equalsIgnoreCase(gender.get())) {
            request.setGender(GENDER_WOMAN);
        }
        request.setInterests(interests.get().split(","));

        return request;
    }

}
