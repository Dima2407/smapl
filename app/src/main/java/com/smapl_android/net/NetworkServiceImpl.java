package com.smapl_android.net;

import android.util.Log;

import com.smapl_android.model.User;
import com.smapl_android.model.UserInfoViewModel;
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
                .baseUrl("http://adrider.pg-dev.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public void login(String login, String password, final OnResultCallback<LoginResponse, Throwable> callback) {
        final Call<LoginResponse> responseCall = apiService.login(new LoginRequest(login, password));
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
    public void registration(UserInfoViewModel user, final OnResultCallback<RegistrationResponse, Throwable> callback) {

        RegistrationRequest registrationRequest = new RegistrationRequest(user.email.get(), user.password.get(), user.name.get(),
               user.phone.get(), Integer.parseInt(user.carYearOfIssue.get()), user.carBrand.get(), user.carModel.get(), user.color.get());
        final Call<RegistrationResponse> responseCall = apiService.registration(registrationRequest);
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
    public void restorePassword(String login, final OnResultCallback<RestorePasswordResponse, Throwable> callback) {

        final Call<RestorePasswordResponse> responseCall = apiService.restorePassword(login);
        responseCall.enqueue(new Callback<RestorePasswordResponse>() {
            @Override
            public void onResponse(Call<RestorePasswordResponse> call, Response<RestorePasswordResponse> response) {
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
                            Log.e(TAG, "onResponse: ", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RestorePasswordResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(null, t);
                }
            }
        });
    }

    @Override
    public void getAdvCompanies(final OnResultCallback<AdvCompaniesResponse, Throwable> callback) {

        final Call<AdvCompaniesResponse> responseCall = apiService.advCompanies();
        responseCall.enqueue(new Callback<AdvCompaniesResponse>() {
            @Override
            public void onResponse(Call<AdvCompaniesResponse> call, Response<AdvCompaniesResponse> response) {
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
                            Log.e(TAG, "onResponse: ", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AdvCompaniesResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(null, t);
                }
            }
        });
    }

    @Override
    public void getNews(final OnResultCallback<GetNewsResponse, Throwable> callback) {

        final Call<GetNewsResponse> responseCall = apiService.getNews();
        responseCall.enqueue(new Callback<GetNewsResponse>() {
            @Override
            public void onResponse(Call<GetNewsResponse> call, Response<GetNewsResponse> response) {
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
                            Log.e(TAG, "onResponse: ", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetNewsResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(null, t);
                }
            }
        });
    }

    @Override
    public void getCompanyHistory(final OnResultCallback<GetCompanyHistoryResponse, Throwable> callback) {
        final Call<GetCompanyHistoryResponse> responseCall = apiService.getCompanyHistory();
        responseCall.enqueue(new Callback<GetCompanyHistoryResponse>() {
            @Override
            public void onResponse(Call<GetCompanyHistoryResponse> call, Response<GetCompanyHistoryResponse> response) {
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
                            Log.e(TAG, "onResponse: ", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCompanyHistoryResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(null, t);
                }
            }
        });

    }

    @Override
    public void editProfile(String phone, String name, String gender, Integer age, String hobby, OnResultCallback<EditProfileResponse, Throwable> callback) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getLastMessages(final OnResultCallback<GetLastMessagesResponse, Throwable> callback) {
        Call<GetLastMessagesResponse> responseCall = apiService.getLastMessages();
        responseCall.enqueue(new Callback<GetLastMessagesResponse>() {
            @Override
            public void onResponse(Call<GetLastMessagesResponse> call, Response<GetLastMessagesResponse> response) {
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
                            Log.e(TAG, "onResponse: ", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetLastMessagesResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(null, t);
                }
            }
        });
    }

    @Override
    public void getBeforeMessages(final OnResultCallback<GetBeforeMessagesResponse, Throwable> callback) {
        final Call<GetBeforeMessagesResponse> responseCall = apiService.getBeforeMessages();
        responseCall.enqueue(new Callback<GetBeforeMessagesResponse>() {
            @Override
            public void onResponse(Call<GetBeforeMessagesResponse> call, Response<GetBeforeMessagesResponse> response) {
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
                            Log.e(TAG, "onResponse: ", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetBeforeMessagesResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(null, t);
                }
            }
        });
    }

    @Override
    public void sendMessage(String message, String senderId, String receiverId, String date, final OnResultCallback<SendMessageResponse, Throwable> callback) {
        Call<SendMessageResponse> responseCall = apiService.sendMessage(message, senderId, receiverId, date);
        responseCall.enqueue(new Callback<SendMessageResponse>() {
            @Override
            public void onResponse(Call<SendMessageResponse> call, Response<SendMessageResponse> response) {
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
                            Log.e(TAG, "onResponse: ", e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SendMessageResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(null, t);
                }
            }
        });
    }

    @Override
    public void getUserById(int id, String token, final OnResultCallback<UserResponse, Throwable> callback) {
        final Call<UserResponse> userByIdCall = apiService.getUserById(id, token);
        userByIdCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
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
                        callback.onResult(null, new Exception(errorMessage));
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(null, t);
                }
            }
        });
    }

    @Override
    public void updateCar(int userId, String token, UpdateCarRequest updateUserRequest, final OnResultCallback<UpdateCarResponse, Throwable> callback) {
        final Call<UpdateCarResponse> userByIdCall = apiService.updateCar(userId, token, updateUserRequest);
        userByIdCall.enqueue(new Callback<UpdateCarResponse>() {
            @Override
            public void onResponse(Call<UpdateCarResponse> call, Response<UpdateCarResponse> response) {
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
                        callback.onResult(null, new Exception(errorMessage));
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateCarResponse> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(null, t);
                }
            }
        });
    }

    @Override
    public void editPassword(String token, String oldPassword, String newPassword, final OnResultCallback<Boolean, Throwable> callback) {
        final Call<ResponseBody> responseCall = apiService.editPassword(token, oldPassword, newPassword);
        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_NO_CONTENT) {
                    if (callback != null) {
                        callback.onResult(true, null);
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
                        callback.onResult(false, new Exception(errorMessage));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (callback != null) {
                    callback.onResult(false, t);
                }
            }
        });
    }
}
