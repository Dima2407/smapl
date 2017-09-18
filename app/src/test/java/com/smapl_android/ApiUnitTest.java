package com.smapl_android;

import com.google.gson.Gson;
import com.smapl_android.net.NetworkService;
import com.smapl_android.net.NetworkServiceFactory;
import com.smapl_android.net.requests.RegistrationRequest;
import com.smapl_android.net.responses.LoginResponse;
import com.smapl_android.net.responses.RegistrationResponse;
import com.smapl_android.net.responses.UserResponse;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ApiUnitTest {

    private static final int WAIT_TIMEOUT = 10;

    private NetworkService networkService;

    @Before
    public void initNetwork() {
        networkService = NetworkServiceFactory.create(false);
    }

   // @Test
    public void checkLoginSuccess() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        networkService.login("1111111111", "qwerty", new NetworkService.OnResultCallback<LoginResponse, Throwable>() {
            @Override
            public void onResult(LoginResponse result, Throwable error) {
                assertThat(error, CoreMatchers.nullValue());
                assertThat(result, CoreMatchers.notNullValue());
                assertThat(result, CoreMatchers.allOf(CoreMatchers.notNullValue()));
            }
        });
        latch.await(WAIT_TIMEOUT, TimeUnit.SECONDS);
    }

    //@Test
    public void createUser() throws Exception {
        networkService = NetworkServiceFactory.create(false);
        final CountDownLatch latch = new CountDownLatch(1);
        RegistrationRequest request = new RegistrationRequest();
        request.setPassword("qwerty");
        request.setName("Bob");
        request.setGender("male");
        request.setEmail("goo@g.com");
        request.setAge("18-25");
        //request.setPhoneNumber("+380978742913");
        request.setPhoneNumber("+380333333333");
        request.setCarYear(2000);
        request.setCarColor("Красный");
        request.setCarMark("Aston Martin");
        request.setCarModel("Aston Martin");
        String data = new Gson().toJson(request);
        networkService.registration(request, new NetworkService.OnResultCallback<RegistrationResponse, Throwable>() {
            @Override
            public void onResult(RegistrationResponse result, Throwable error) {
                String message = error.getMessage();
                assertThat(error, CoreMatchers.nullValue());
                assertThat(result, CoreMatchers.notNullValue());
                assertThat(result, CoreMatchers.allOf(CoreMatchers.notNullValue()));
                latch.countDown();
            }
        });
        latch.await();
    }
    //@Test
    public void getUser() throws Exception{
        networkService = NetworkServiceFactory.create(false);
        final CountDownLatch latch = new CountDownLatch(1);
        networkService.login("+380333333333", "qwerty", new NetworkService.OnResultCallback<LoginResponse, Throwable>() {
            @Override
            public void onResult(LoginResponse result, Throwable error) {
                assertThat(error, CoreMatchers.nullValue());
                assertThat(result, CoreMatchers.notNullValue());
                System.out.println("user - " + result.getUserId());
                System.out.println("token - " +result.getId());
                networkService.getUserById(result.getUserId(), result.getId(), new NetworkService.OnResultCallback<UserResponse, Throwable>() {
                    @Override
                    public void onResult(UserResponse result, Throwable error) {
                        assertThat(error, CoreMatchers.nullValue());
                        latch.countDown();
                    }
                });
            }
        });
        latch.await();
    }
}