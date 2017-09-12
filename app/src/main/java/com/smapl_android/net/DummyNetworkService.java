package com.smapl_android.net;

import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.net.requests.EditProfileRequest;
import com.smapl_android.net.requests.UpdateCarRequest;
import com.smapl_android.net.responses.*;

import java.util.Random;

@Deprecated
abstract class DummyNetworkService implements NetworkService{

    public static class Utils {
        public static <T> T[] arrayGenerate(int capacity) {
            T[] resultArray = (T[]) new Object[capacity];

            for (int i = 0; i < capacity - 1; i++) {
                T newObject = (T) new Object();
                resultArray[i] = newObject;
            }
            return resultArray;
        }

        public static String generateString(String init, int max) {
            Random random = new Random();
            int size = random.nextInt(max);
            return fixedString(init, size);
        }

        public static String fixedString(String init, int max) {
            StringBuilder result = new StringBuilder(init);
            for (int i = 0; i < max; i++) {
                result.append(init);
            }
            return result.toString();
        }
    }

    @Override
    public void login(final String mobileNumber, final String password,
                      final OnResultCallback<LoginResponse, Throwable> callback) {
       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);

                    if ("1111111111".equals(mobileNumber) && "qwerty".equals(password)) {

                        LoginResponse result = new LoginResponse();
                        LoginResponse.Result innerResult = new LoginResponse.Result();
                        String string = Utils.generateString("test strign", 10);
                        Random random = new Random();

                        innerResult.setCreated(string);
                        innerResult.setId(string);
                        innerResult.setTtl(random.nextInt());
                        innerResult.setUserId(0);

                        result.setResult(innerResult);
                        callback.onResult(result, null);
                    } else if (mobileNumber != null && mobileNumber.isEmpty()) {
                        callback.onResult(null, new Exception("Please provide username."));
                    } else if (password != null && password.isEmpty()) {
                        callback.onResult(null, new Exception("Please provide password."));
                    } else {
                        callback.onResult(null, new Exception("Incorrect username or password."));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }


    @Override
    public void getUserById(int id, String token, OnResultCallback<UserResponse, Throwable> callback) {

    }

  /*  @Override
    public void updateCar(int userId, String token, UpdateCarRequest updateUserRequest, OnResultCallback<UpdateCarResponse, Throwable> callback) {

    }*/

    @Override
    public void getCampaigns(String token, OnResultCallback<GetCampaignListResponse, Throwable> callback) {

    }

    @Override
    public void logout(String token, OnResultCallback<Boolean, Throwable> callback) {

    }
}
