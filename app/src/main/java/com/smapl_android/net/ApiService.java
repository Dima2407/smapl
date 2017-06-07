package com.smapl_android.net;

import com.smapl_android.net.responses.LoginResponse;
import com.smapl_android.net.responses.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    String API_KEY = "keykey";

    @POST
    @FormUrlEncoded
    @Headers("api_key: " + API_KEY)
    Call<LoginResponse> login(@Query("phone") String login,
                              @Query("password") String password);

    @POST
    @FormUrlEncoded
    @Headers("api_key: " + API_KEY)
    Call<RegistrationResponse> registration(@Query("phone") String phoneNumber,
                                            @Query("password") String password,
                                            @Query("name") String name,
                                            @Query("gender") String gender,
                                            @Query("age") int age,
                                            @Query("car/mark") String carBrand,
                                            @Query("car/model") String carModel,
                                            @Query("car/year") int carYearOfIssue,
                                            @Query("car/color") String color,
                                            @Query("hobby") String interests);
}
