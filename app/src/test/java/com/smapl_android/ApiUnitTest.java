package com.smapl_android;

import com.smapl_android.net.NetworkService;
import com.smapl_android.net.NetworkServiceFactory;
import com.smapl_android.net.responses.LoginResponse;

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

    private static final int WAIT_TIMEOUT = 2;

    private NetworkService networkService;

    @Before
    public void initNetwork() {
        networkService = NetworkServiceFactory.create(true);
    }

    @Test
    public void checkLoginSuccess() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        networkService.login("1111111111", "qwerty", new NetworkService.OnResultCallback<LoginResponse, Throwable>() {
            @Override
            public void onResult(LoginResponse result, Throwable error) {
                assertThat(error, CoreMatchers.nullValue());
                assertThat(result, CoreMatchers.notNullValue());
                assertThat(result.getResult(), CoreMatchers.allOf(CoreMatchers.notNullValue()));
            }
        });
        latch.await(WAIT_TIMEOUT, TimeUnit.SECONDS);
    }

    @Test
    public void getUserById() throws Exception {
        networkService = NetworkServiceFactory.create(false);
        CountDownLatch latch = new CountDownLatch(1);
        networkService.login("1111111111", "qwerty", new NetworkService.OnResultCallback<LoginResponse, Throwable>() {
            @Override
            public void onResult(LoginResponse result, Throwable error) {
                assertThat(error, CoreMatchers.nullValue());
                assertThat(result, CoreMatchers.notNullValue());
                assertThat(result.getResult(), CoreMatchers.allOf(CoreMatchers.notNullValue()));
            }
        });
        latch.await(WAIT_TIMEOUT, TimeUnit.SECONDS);
    }
}