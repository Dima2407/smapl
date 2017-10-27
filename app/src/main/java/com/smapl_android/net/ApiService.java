package com.smapl_android.net;

import com.smapl_android.net.requests.*;
import com.smapl_android.net.responses.*;

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
    Call<EmptyResponse> restorePassword(@Query("login") String email);


    @GET("api/campaign/list")
    Call<GetCampaignListResponse> getCampaigns(@Query("access_token") String token);

    @POST("api/user/logout")
    Call<EmptyResponse> logout(@Query("access_token") String token);

    @POST("api/trackings/save")
    @Headers(value = "Content-Type: application/json")
    Call<TrackingResponse> tracking(@Query("access_token") String token, @Body CoordinateRequest request);

    @GET("api/trackings/user/{id}")
    Call<GetTrackingHistoryResponse> getHistory(@Path("id") int userId, @Query("access_token") String token, @Query("page") int page);

    @POST("api/user/cash")
    Call<EmptyStringResponse> withdrawMoney(@Query("access_token") String token, @Body MoneyWithdrawRequest request);

    @POST("api/user/fb_login")
    Call<LoginResponse> fbLogin(@Body FbLoginRequest request);

    @POST("api/campaign/join/{campaignId}")
    Call<EmptyResponse> joinCampaign(@Path("campaignId") int id, @Query("access_token") String token);

    @GET("api/campaign/{campaignId}")
    Call<GetCampaignResponse> getCampaign(@Path("campaignId") int id, @Query("access_token") String token);
}
