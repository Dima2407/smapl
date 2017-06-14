package com.smapl_android.net;

import com.smapl_android.net.requests.LoginRequest;
import com.smapl_android.net.requests.RegistrationRequest;
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
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static com.smapl_android.net.ApiService.API_KEY;

public interface ApiService {

    String API_KEY = "keykey";

    @POST("api/user/login")
    @Headers("api_key: " + API_KEY)
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/user")
    @Headers("api_key: " + API_KEY)
    Call<RegistrationResponse> registration(@Body RegistrationRequest registrationRequest);

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
