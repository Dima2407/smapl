package com.smapl_android.net;

import com.smapl_android.net.requests.CoordinateRequest;
import com.smapl_android.net.requests.EditProfileRequest;
import com.smapl_android.net.requests.RegistrationRequest;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.*;

public interface NetworkService {

    //region auth

    void login(String login, String password, final NetworkServiceImpl.OnResultCallback<LoginResponse, Throwable> callback);

    void registration(RegistrationRequest request, final NetworkService.OnResultCallback<RegistrationResponse, Throwable> callback);

    void logout(String token, OnResultCallback<Boolean, Throwable> callback);

    void editPassword(String token, String oldPassword, String newPassword,
                      final NetworkService.OnResultCallback<Boolean, Throwable> callback);

    //endregion

    void getUserById(int id, String token, NetworkService.OnResultCallback<UserResponse, Throwable> callback);

    void restorePassword(String login, final NetworkService.OnResultCallback<Boolean, Throwable> callback);


    void editProfile(int userId, String token, EditProfileRequest request, final NetworkService.OnResultCallback<UserResponse, Throwable> callback);

    void updateCar(int userId, String token, UpdateCarRequest updateUserRequest, OnResultCallback<UserResponse, Throwable> callback);

    void getCampaigns(String token, OnResultCallback<GetCampaignListResponse, Throwable> callback);

    void startTracking(String token, CoordinateRequest request, OnResultCallback<TrackingResponse, Throwable> callback);

    void updateTracking(String token, CoordinateRequest request, OnResultCallback<TrackingResponse, Throwable> callback);

    void stopTracking(String token, CoordinateRequest request, OnResultCallback<TrackingResponse, Throwable> callback);

    void getHistory(String token, int userId, int page, OnResultCallback<GetTrackingHistoryResponse, Throwable> callback);

    interface OnResultCallback<T, E> {
        void onResult(T result, E error);
    }

}
