package com.smapl_android.net;

import com.smapl_android.net.responses.LoginResponse;
import com.smapl_android.net.responses.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    String API_KEY = "keykey";

    @POST
    @FormUrlEncoded
    @Headers("api_key: " + API_KEY)
    Call<LoginResponse> login(String login, String password);

    @POST
    @FormUrlEncoded
    @Headers("api_key: " + API_KEY)
    Call<RegistrationResponse> registration(String phoneNumber, String password);
}
