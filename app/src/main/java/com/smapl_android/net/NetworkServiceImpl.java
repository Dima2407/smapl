package com.smapl_android.net;

import android.util.Log;
import com.google.gson.Gson;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.net.requests.UserRequestBody;
import com.smapl_android.net.requests.EditProfileRequest;
import com.smapl_android.net.requests.LoginRequest;
import com.smapl_android.net.requests.RegistrationRequest;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.net.HttpURLConnection;

class NetworkServiceImpl implements NetworkService {

    private final static String TAG = NetworkServiceImpl.class.getSimpleName();
    private static final String GENDER_MAN = "man";
    private static final String GENDER_WOMAN = "woman";

    private final ApiService apiService;

    public NetworkServiceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.DEV_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private static <T> Callback<T> createCallback(final OnResultCallback<T, Throwable> callback) {
        final Callback<T> apiCallback = new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                processResponse(response, callback);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(null, t);
                }
            }
        };
        return apiCallback;
    }

    private static Callback<ResponseBody> createBoolean(final OnResultCallback<Boolean, Throwable> callback) {
        final Callback<ResponseBody> apiCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Response<Boolean> converted = null;
                if (response.isSuccessful()) {
                    converted = Response.success(true);
                } else {
                    converted = Response.error(response.code(), response.errorBody());
                }
                processResponse(converted, callback);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(null, t);
                }
            }
        };
        return apiCallback;
    }

    private static Callback<EmptyResponse> createBooleanEmptyBody(final OnResultCallback<Boolean, Throwable> callback) {
        final Callback<EmptyResponse> apiCallback = new Callback<EmptyResponse>() {
            @Override
            public void onResponse(Call<EmptyResponse> call, Response<EmptyResponse> response) {
                Response<Boolean> converted = null;
                if (response.isSuccessful()) {
                    converted = Response.success(response.body().isSuccess());
                } else {
                    converted = Response.error(response.code(), response.errorBody());
                }
                processResponse(converted, callback);
            }

            @Override
            public void onFailure(Call<EmptyResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(null, t);
                }
            }
        };
        return apiCallback;
    }


    private static <T> void processResponse(Response<T> response, OnResultCallback<T, Throwable> callback) {
        if (response.isSuccessful()) {
            if (callback != null) {
                callback.onResult(response.body(), null);
            }
        } else {
            if (callback != null) {
                String errorMessage = "error";
                try {
                    errorMessage = response.errorBody().string();
                } catch (IOException e) {
                    errorMessage = e.getMessage();
                }
                callback.onResult(response.body(), new Exception(errorMessage));
            }
        }
    }

    @Override
    public void login(String login, String password, final OnResultCallback<LoginResponse, Throwable> callback) {
        final Call<LoginResponse> responseCall = apiService.login(new LoginRequest(login, password));
        responseCall.enqueue(createCallback(callback));
    }

    @Override
    public void registration(RegistrationRequest registrationRequest, final OnResultCallback<RegistrationResponse, Throwable> callback) {

        final Call<RegistrationResponse> responseCall = apiService.registration(registrationRequest);
        responseCall.enqueue(createCallback(callback));
    }

    @Override
    public void getUserById(int id, String token, final OnResultCallback<UserResponse, Throwable> callback) {
        final Call<UserResponse> userByIdCall = apiService.getUserById(id, token);
        Log.d(TAG, token);
        userByIdCall.enqueue(createCallback(callback));
    }

    @Override
    public void updateCar(int userId, String token, UpdateCarRequest updateUserRequest, final OnResultCallback<UserResponse, Throwable> callback) {
        final Call<UserResponse> userByIdCall = apiService.editProfile(userId, token, updateUserRequest);
        userByIdCall.enqueue(createCallback(callback));
    }

    @Override
    public void editProfile(int userId, String token, EditProfileRequest request, final OnResultCallback<UserResponse, Throwable> callback) {
        UserRequestBody userRequestBody = new UserRequestBody();
        userRequestBody.setId(userId);
        final Call<UserResponse> responseCall = apiService.editProfile(userId, token, request);
        responseCall.enqueue(createCallback(callback));

    }

    @Override
    public void editPassword(String token, String oldPassword, String newPassword, final OnResultCallback<Boolean, Throwable> callback) {
        final Call<EmptyResponse> responseCall = apiService.editPassword(token, oldPassword, newPassword);
        responseCall.enqueue(createBooleanEmptyBody(callback));
    }

    @Override
    public void restorePassword(String login, final OnResultCallback<Boolean, Throwable> callback) {

        final Call<EmptyResponse> responseCall = apiService.restorePassword(login);
        responseCall.enqueue(createBooleanEmptyBody(callback));
    }

    @Override
    public void getCampaigns(String token, OnResultCallback<GetCampaignListResponse, Throwable> callback) {
        final Call<GetCampaignListResponse> campaigns = apiService.getCampaigns(token);
        campaigns.enqueue(createCallback(callback));
    }

    @Override
    public void logout(String token, OnResultCallback<Boolean, Throwable> callback) {
        final Call<EmptyResponse> logout = apiService.logout(token);
        logout.enqueue(createBooleanEmptyBody(callback));
    }
}
