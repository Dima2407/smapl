package com.smapl_android.core;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.smapl_android.net.ApiService;
import com.smapl_android.storage.SessionStorage;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.File;
import java.io.IOException;

public class UploadService extends IntentService {
    public static final String IMAGE_MEDIA_TYPE = "image/*";
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";

    public static final String EXTRA_PATH = "path";
    private static final String TAG = UploadService.class.getSimpleName();


    public UploadService() {
        super(TAG);
    }

    public static void uploadCarPhoto(Context context, String filePath) {

        context.startService(new Intent(context, UploadService.class)
                .putExtra(EXTRA_PATH, filePath));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String path = intent.getStringExtra(EXTRA_PATH);
        if (path != null) {
            uploadCarPhoto(path);
        }
    }

    private void uploadCarPhoto(String path) {
        final SessionStorage sessionStorage = new SessionStorage(this);
        File file = new File(path);
        final String name = file.getName();
        final String extension = name.substring(name.lastIndexOf(".") + 1);

        MultipartBody.Builder requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);


        String filePartHeader = String.format("form-data; name=\"image\"; filename=\"%s\"", file.getName(), file.getName());

        requestBody.addPart(
                Headers.of(HEADER_CONTENT_DISPOSITION, filePartHeader),
                RequestBody.create(okhttp3.MediaType.parse("image/" + extension), file));


        final Request request = new Request.Builder()
                .url(ApiService.DEV_URL + "api/user/upload_image/" + sessionStorage.getUserId())
                .addHeader("Authorization", sessionStorage.getAuthKey())
                .post(requestBody.build())
                .build();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Response response = null;
        try {
            response = client.newCall(request).execute();
            String res = response.body().string();
            Log.d(TAG, res);
        } catch (IOException e) {
            Log.e(TAG, "uploadCarPhoto: ", e);
        }

    }
}
