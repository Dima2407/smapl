package com.smapl_android.net;

import com.smapl_android.net.requests.*;
import com.smapl_android.net.responses.*;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    String DEV_URL = "http://adrider.pg-dev.com/";

    String API_KEY = "keykey";

    @POST("api/user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/user")
    Call<RegistrationResponse> registration(@Body RegistrationRequest registrationRequest);

    @GET("api/user/{id}")
    Call<UserResponse> getUserById(@Path("id") int id, @Query("access_token") String token);

    @POST("api/user/change-password")
    @FormUrlEncoded
    Call<EmptyResponse> editPassword(@Query("access_token") String token,
                                    @Field("oldPassword") String oldPassword,
                                    @Field("newPassword") String newPassword);

    @PATCH("api/user/{id}")
    Call<UserResponse> editProfile(@Path("id") int userId, @Query("access_token") String token, @Body EditProfileRequest editProfileRequest);

    @PATCH("api/user/{id}")
    Call<UserResponse> editProfile(@Path("id") int userId, @Query("access_token") String token, @Body UpdateCarRequest editProfileRequest);



    @POST("api/user/forgot_password")
    @FormUrlEncoded
    @Headers("api_key: " + API_KEY)
    Call<EmptyResponse> restorePassword(@Query("login") String email);


    @GET("api/campaign/list")
    Call<GetCampaignListResponse> getCampaigns(@Query("access_token") String token);

    @POST("api/user/logout")
    Call<EmptyResponse> logout(@Query("access_token") String token);
}
