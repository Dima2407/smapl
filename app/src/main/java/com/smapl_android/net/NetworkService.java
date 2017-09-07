package com.smapl_android.net;

import com.smapl_android.model.User;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.net.requests.EditProfileRequest;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.*;

public interface NetworkService {

    //region auth

    void login(String login, String password, final NetworkServiceImpl.OnResultCallback<LoginResponse, Throwable> callback);

    void registration(UserInfoViewModel user, final NetworkService.OnResultCallback<RegistrationResponse, Throwable> callback);

    void logout(String token, OnResultCallback<Boolean, Throwable> callback);

    void editPassword(String token, String oldPassword, String newPassword,
                      final NetworkService.OnResultCallback<Boolean, Throwable> callback);

    //endregion

    void getUserById(int id, String token, NetworkService.OnResultCallback<UserResponse, Throwable> callback);

    void restorePassword(String login, final NetworkService.OnResultCallback<Boolean, Throwable> callback);

    void getAdvCompanies(final NetworkService.OnResultCallback<AdvCompaniesResponse, Throwable> callback);

    void getNews(final NetworkService.OnResultCallback<GetNewsResponse, Throwable> callback);

    void getCompanyHistory(final NetworkService.OnResultCallback<GetCompanyHistoryResponse, Throwable> callback);



    void editProfile(int userId, String token, EditProfileRequest request, final NetworkService.OnResultCallback<EditProfileResponse, Throwable> callback);

    void getLastMessages(final NetworkService.OnResultCallback<GetLastMessagesResponse, Throwable> callback);

    void getBeforeMessages(final NetworkService.OnResultCallback<GetBeforeMessagesResponse, Throwable> callback);

    void sendMessage(String message, String senderId, String receiverId, String date,
                     NetworkService.OnResultCallback<SendMessageResponse, Throwable> callback);

    void updateCar(int userId, String token, UpdateCarRequest updateUserRequest, OnResultCallback<Boolean, Throwable> callback);

    void getCampaigns(String token, OnResultCallback<GetCampaignListResponse, Throwable> callback);

    interface OnResultCallback<T, E> {
        void onResult(T result, E error);
    }

}
