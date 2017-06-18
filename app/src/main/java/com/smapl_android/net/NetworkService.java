package com.smapl_android.net;

import com.smapl_android.model.User;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.*;

public interface NetworkService {

    void login(String login, String password, final NetworkServiceImpl.OnResultCallback<LoginResponse, Throwable> callback);

    void registration(User user, final NetworkService.OnResultCallback<RegistrationResponse, Throwable> callback);

    void getUserById(int id, String token, NetworkService.OnResultCallback<UserResponse, Throwable> callback);

    void restorePassword(String login, final NetworkService.OnResultCallback<RestorePasswordResponse, Throwable> callback);

    void getAdvCompanies(final NetworkService.OnResultCallback<AdvCompaniesResponse, Throwable> callback);

    void getNews(final NetworkService.OnResultCallback<GetNewsResponse, Throwable> callback);

    void getCompanyHistory(final NetworkService.OnResultCallback<GetCompanyHistoryResponse, Throwable> callback);

    void editPassword(String oldPassword, String newPassword,
                      final NetworkService.OnResultCallback<EditPasswordResponse, Throwable> callback);

    void editProfile(String phone, String name, String gender, Integer age, String hobby,
                     final NetworkService.OnResultCallback<EditProfileResponse, Throwable> callback);

    void editCar(Integer carYear, String carMark, String carModel, String carColor, String carPhoto,
                 OnResultCallback<EditCarResponse, Throwable> callback);

    void getLastMessages(final NetworkService.OnResultCallback<GetLastMessagesResponse, Throwable> callback);

    void getBeforeMessages(final NetworkService.OnResultCallback<GetBeforeMessagesResponse, Throwable> callback);

    void sendMessage(String message, String senderId, String receiverId, String date,
                     NetworkService.OnResultCallback<SendMessageResponse, Throwable> callback);

    void updateCar(int userId, String token, UpdateCarRequest updateUserRequest, OnResultCallback<UpdateCarResponse, Throwable> callback);

    interface OnResultCallback<T, E> {
        void onResult(T result, E error);
    }

}
