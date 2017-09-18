package com.smapl_android;

import android.location.Location;

import com.google.gson.Gson;
import com.smapl_android.net.NetworkService;
import com.smapl_android.net.NetworkServiceFactory;
import com.smapl_android.net.requests.CoordinateRequest;
import com.smapl_android.net.requests.RegistrationRequest;
import com.smapl_android.net.responses.LoginResponse;
import com.smapl_android.net.responses.RegistrationResponse;
import com.smapl_android.net.responses.TrackingResponse;
import com.smapl_android.net.responses.UserResponse;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;

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

    @Test
    public void checkLoginSuccess() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        networkService.login("+380333333333", "qwerty", new NetworkService.OnResultCallback<LoginResponse, Throwable>() {
            @Override
            public void onResult(LoginResponse result, Throwable error) {
                assertThat(error, CoreMatchers.nullValue());
                assertThat(result, CoreMatchers.notNullValue());
                System.out.println(result.getId());
                System.out.println(result.getUserId());
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
    public void getUser() throws Exception {
        networkService = NetworkServiceFactory.create(false);
        final CountDownLatch latch = new CountDownLatch(1);
        networkService.login("+380333333333", "qwerty", new NetworkService.OnResultCallback<LoginResponse, Throwable>() {
            @Override
            public void onResult(LoginResponse result, Throwable error) {
                assertThat(error, CoreMatchers.nullValue());
                assertThat(result, CoreMatchers.notNullValue());
                System.out.println("user - " + result.getUserId());
                System.out.println("token - " + result.getId());
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

    @Test
    public void startTracking() throws Exception {
        String token = "EReDjOvsWXagMTv4bmShkqO23oBeB8cs8jbGA1EGE30GwZBZBUTUyQfBH2wA6ffK";
        final CoordinateRequest request = CoordinateRequest.start();

        request.addCoordinate(30.525339, 50.413468);
        request.addCoordinate(30.523450, 50.416177);
        request.addCoordinate(30.522033, 50.418434);
        request.addCoordinate(30.520695, 50.420690);
        request.addCoordinate(30.519672, 50.420690);
        request.addCoordinate(30.519672, 50.422345);
        request.addCoordinate(30.520695, 50.423398);
        request.addCoordinate(30.525103, 50.424802);

        System.out.println(new Gson().toJson(request));

        final CountDownLatch latch = new CountDownLatch(1);
        networkService.startTracking(token, request, new NetworkService.OnResultCallback<TrackingResponse, Throwable>() {
            @Override
            public void onResult(TrackingResponse result, Throwable error) {
                try {
                    if (error != null) {
                        System.out.println(error.getMessage());
                    }
                    assertThat(result, CoreMatchers.notNullValue());
                    assertThat(result.getStatus(), CoreMatchers.is("start"));
                    System.out.println(result.getTotalAmount());
                    System.out.println(result.getTotalDistance());
                } finally {
                    latch.countDown();
                }

            }
        });
        latch.await();
    }

    @Test
    public void stopTracking() throws Exception {
        String token = "EReDjOvsWXagMTv4bmShkqO23oBeB8cs8jbGA1EGE30GwZBZBUTUyQfBH2wA6ffK";
        final CoordinateRequest request = CoordinateRequest.stop();
        request.addCoordinate(30.539685, 50.445396);
        request.addCoordinate(30.536980, 50.446891);
        request.addCoordinate(30.533101, 50.448679);
        request.addCoordinate(30.529682, 50.450336);
        request.addCoordinate(30.527232, 50.452156);
        request.addCoordinate(30.525242, 50.450791);
        request.addCoordinate(30.522486, 50.448451);
        final CountDownLatch latch = new CountDownLatch(1);
        System.out.println(new Gson().toJson(request));
        networkService.stopTracking(token, request, new NetworkService.OnResultCallback<TrackingResponse, Throwable>() {
            @Override
            public void onResult(TrackingResponse result, Throwable error) {
                try {
                    if (error != null) {
                        System.out.println(error.getMessage());
                    }
                    assertThat(result, CoreMatchers.notNullValue());
                    assertThat(result.getStatus(), CoreMatchers.is("stop"));
                    System.out.println(result.getTotalAmount());
                    System.out.println(result.getTotalDistance());

                } finally {
                    latch.countDown();
                }
            }
        });
        latch.await();
    }

    @Test
    public void updateTracking() throws Exception {
        String token = "EReDjOvsWXagMTv4bmShkqO23oBeB8cs8jbGA1EGE30GwZBZBUTUyQfBH2wA6ffK";
        final CoordinateRequest request = CoordinateRequest.inProgress();
        request.addCoordinate(30.521618, 50.423681);
        request.addCoordinate(30.525344, 50.424884);
        request.addCoordinate(30.528865, 50.426770);
        request.addCoordinate(30.534122, 50.427453);
        request.addCoordinate(30.535653, 50.426348);
        request.addCoordinate(30.538715, 50.427973);
        request.addCoordinate(30.541216, 50.429241);
        final CountDownLatch latch = new CountDownLatch(1);
        networkService.updateTracking(token, request, new NetworkService.OnResultCallback<TrackingResponse, Throwable>() {
            @Override
            public void onResult(TrackingResponse result, Throwable error) {
                try {
                    if (error != null) {
                        System.out.println(error.getMessage());
                    }
                    assertThat(result, CoreMatchers.notNullValue());
                    assertThat(result.getStatus(), CoreMatchers.is("in_progress"));
                    System.out.println(result.getTotalAmount());
                    System.out.println(result.getTotalDistance());
                } finally {
                    latch.countDown();
                }

            }
        });
        latch.await();
    }
}