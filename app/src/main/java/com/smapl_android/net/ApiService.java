package com.smapl_android.net;

import com.smapl_android.net.requests.LoginRequest;
import com.smapl_android.net.requests.RegistrationRequest;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.*;

import okhttp3.ResponseBody;
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
    Call<ResponseBody> updateCar(@Path("id") int userId, @Query("access_token") String token, @Body UpdateCarRequest updateUserRequest);

    @POST("api/user/change-password")
    @FormUrlEncoded
    Call<ResponseBody> editPassword(@Query("access_token") String token,
                                    @Field("oldPassword") String oldPassword,
                                    @Field("newPassword") String newPassword);


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

    @POST("api/edit/profile/userId")
    @FormUrlEncoded
    @Headers("api_key: " + API_KEY)
    Call<EditProfileResponse> editProfile(@Query("phone") String phone,
                                          @Query("name") String name,
                                          @Query("gender") String gender,
                                          @Query("age") Integer age,
                                          @Query("hobby") String hobby);


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
