package com.smapl_android.model;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.text.TextUtils;
import com.smapl_android.net.requests.EditProfileRequest;

public class UserInfoEditVM extends BaseObservable {

    private static final String GENDER_MAN = "male";
    private static final String GENDER_WOMAN = "female";

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> phone = new ObservableField<>();
    public final ObservableField<Boolean> male = new ObservableField<>();
    public final ObservableField<Boolean> female = new ObservableField<>();
    public final ObservableField<String> age = new ObservableField<>();
    public final ObservableField<String> interests = new ObservableField<>();


    public void apply(UserInfo userInfo) {
        name.set(userInfo.name.get());
        male.set(GENDER_MAN.equalsIgnoreCase(userInfo.getResponse().getGender()));
        female.set(GENDER_WOMAN.equalsIgnoreCase(userInfo.getResponse().getGender()));
        phone.set(userInfo.getResponse().getMobileNumber());
        age.set(userInfo.getResponse().getAge());
        interests.set(userInfo.getResponse().getInterests());
    }

    public EditProfileRequest toUpdateRequest() {
        EditProfileRequest request = new EditProfileRequest();
        request.setName(name.get());
        request.setPhone(phone.get());
        if (!TextUtils.isEmpty(age.get())) {
            request.setAge(age.get());
        }
        if (male.get()) {
            request.setGender(GENDER_MAN);
        }
        if(female.get()){
            request.setGender(GENDER_WOMAN);
        }
        request.setInterests(interests.get());

        return request;
    }

}
