package com.smapl_android.net;

import com.smapl_android.net.requests.LoginRequest;
import com.smapl_android.net.requests.RegistrationRequest;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.*;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    String API_KEY = "keykey";

    @POST("api/user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/user")
    Call<RegistrationResponse> registration(@Body RegistrationRequest registrationRequest);

    @GET("api/user/{id}")
    Call<UserResponse> getUserById(@Path("id") int id, @Query("access_token") String token);

    @PATCH("api/user/edit/car/{id}")
    Call<UpdateCarResponse> updateCar(@Path("id") int userId, @Query("access_token") String token, @Body UpdateCarRequest updateUserRequest);

    @POST("api/restore")
    @FormUrlEncoded
    @Headers("api_key: " + API_KEY)
    Call<RestorePasswordResponse> restorePassword(@Query("login") String login);

    @GET("api/advCompanies/userId")
    @Headers("api_key: " + API_KEY)
    Call<AdvCompaniesResponse> advCompanies();

    @GET("api/news")
    @Headers("api_key: " + API_KEY)
    Call<GetNewsResponse> getNews();

    @GET("api/history/company_id")
    @Headers("api_key: " + API_KEY)
    Call<GetCompanyHistoryResponse> getCompanyHistory();

    @POST("api/edit/password/userId")
    @FormUrlEncoded
    @Headers("api_key: " + API_KEY)
    Call<EditPasswordResponse> editPassword(@Query("oldPassword") String oldPassword,
                                            @Query("newPassword") String newPassword);

    @POST("api/edit/profile/userId")
    @FormUrlEncoded
    @Headers("api_key: " + API_KEY)
    Call<EditProfileResponse> editProfile(@Query("phone") String phone,
                                          @Query("name") String name,
                                          @Query("gender") String gender,
                                          @Query("age") Integer age,
                                          @Query("hobby") String hobby);

    @POST("api/edit/car/userId")
    @FormUrlEncoded
    @Headers("api_key: " + API_KEY)
    Call<EditCarResponse> editCar(@Query("car/year") Integer carYear,
                                  @Query("car/mark") String carMark,
                                  @Query("car/model") String carModel,
                                  @Query("car/color") String carColor,
                                  @Query("car/photo") String carPhoto);

    @GET("api/messages/last/user_id")
    @Headers("api_key: " + API_KEY)
    Call<GetLastMessagesResponse> getLastMessages();

    @GET("api/messages/before/date/user_id")
    @Headers("api_key: " + API_KEY)
    Call<GetBeforeMessagesResponse> getBeforeMessages();

    @POST("api/messages/send/user_id")
    @FormUrlEncoded
    @Headers("api_key: " + API_KEY)
    Call<SendMessageResponse> sendMessage(@Query("message") String message,
                                          @Query("sender_id") String sender_id,
                                          @Query("receiver_id") String receiver_id,
                                          @Query("date") String date);


}
