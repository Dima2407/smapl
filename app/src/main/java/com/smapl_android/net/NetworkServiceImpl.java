package com.smapl_android.net;

import android.util.Log;

import com.smapl_android.model.User;
import com.smapl_android.net.responses.AdvCompaniesResponse;
import com.smapl_android.net.responses.EditCarResponse;
import com.smapl_android.net.responses.EditPasswordResponse;
import com.smapl_android.net.responses.EditProfileResponse;
import com.smapl_android.net.responses.GetBeforeMessagesResponse;
import com.smapl_android.net.responses.GetCompanyHistoryResponse;
import com.smapl_android.net.responses.GetLastMessagesResponse;
import com.smapl_android.net.responses.GetNewsResponse;
import com.smapl_android.net.responses.LoginResponse;
import com.smapl_android.net.responses.RegistrationResponse;
import com.smapl_android.net.responses.RestorePasswordResponse;
import com.smapl_android.net.responses.SendMessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

class NetworkServiceImpl implements NetworkService {

    private final static String TAG = NetworkServiceImpl.class.getSimpleName();
    private static final String GENDER_MAN = "man";
    private static final String GENDER_WOMAN = "woman";

    private final ApiService apiService;

    public NetworkServiceImpl() {
        //TODO: wait fro real API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public void login(String login, String password, final OnResultCallback<LoginResponse, Throwable> callback) {
        final Call<LoginResponse> responseCall = apiService.login(login, password);
        responseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if(callback != null){
                        callback.onResult(response.body(), null);
                    }
                } else {
                    if (callback != null) {
                        String errorMessage = "error";
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            errorMessage = e.getMessage();
                            Log.e(TAG, "onResponse: ", e);
                        }
                        callback.onResult(null, new Exception(errorMessage));
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(null, t);
                }
            }
        });
    }

    @Override
    public void registration(User user, final OnResultCallback<RegistrationResponse, Throwable> callback) {

        int age = 18; //dummy

        String interests = user.getInterests().toString(); // dummy

        final Call<RegistrationResponse> responseCall = apiService.registration(user.getPhoneNumber(), user.getPassword(),
                user.getName(), user.isGender() ? GENDER_MAN : GENDER_WOMAN, age, user.getCarBrand(), user.getCarModel(),
                user.getCarYearOfIssue(), user.getColor(), interests);
        responseCall.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()){
                    if (callback != null){
                        callback.onResult(response.body(), null);
                    }
                } else {
                    if (callback != null) {
                        String errorMessage = "error";
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                           errorMessage = e.getMessage();
                            Log.e(TAG,"onResponse: ", e);
                        }
                        callback.onResult(null, new Exception(errorMessage));
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                if (callback != null){
                    callback.onResult(null, t);
                }
            }
        });
    }

    @Override
    public void restorePassword(String login, OnResultCallback<RestorePasswordResponse, Throwable> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getAdvCompanies(OnResultCallback<AdvCompaniesResponse, Throwable> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getNews(OnResultCallback<GetNewsResponse, Throwable> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getCompanyHistory(OnResultCallback<GetCompanyHistoryResponse, Throwable> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void editPassword(String oldPassword, String newPassword, OnResultCallback<EditPasswordResponse, Throwable> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void editProfile(String phone, String name, String gender, Integer age, String hobby, OnResultCallback<EditProfileResponse, Throwable> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void editCar(Integer carYear, String carMark, String carModel, String carColor, String carPhoto, OnResultCallback<EditCarResponse, Throwable> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getLastMessages(OnResultCallback<GetLastMessagesResponse, Throwable> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getBeforeMessages(OnResultCallback<GetBeforeMessagesResponse, Throwable> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendMessage(String message, String senderId, String receiverId, String date, OnResultCallback<SendMessageResponse, Throwable> callback) {
        throw new UnsupportedOperationException();
    }


}
