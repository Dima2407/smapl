package com.smapl_android;

import com.smapl_android.net.NetworkService;
import com.smapl_android.net.NetworkServiceFactory;
import com.smapl_android.net.responses.LoginResponse;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        NetworkService networkService = NetworkServiceFactory.create(true);
        final CountDownLatch latch = new CountDownLatch(1);
        networkService.login("bob", "qwerty", new NetworkService.OnResultCallback<LoginResponse, Throwable>() {
            @Override
            public void onResult(LoginResponse result, Throwable error) {

                assertNotNull(error);
                latch.countDown();
            }
        });
        latch.await(3, TimeUnit.SECONDS);
    }
}