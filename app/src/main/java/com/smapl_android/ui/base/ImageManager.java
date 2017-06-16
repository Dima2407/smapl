package com.smapl_android.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class ImageManager {

    private static final String CAPTURE_MEDIA_FILE_PROVIDER = "com.smapl_android.fileprovider";
    private static final String CAMERA_DIR = "camera";

    private AtomicInteger requestId = new AtomicInteger(10);

    private Map<Integer, SimpleOnImageRequestListener> requestsRunners = new HashMap<>();

    public void takePhoto(Activity activity, OnImageRequestListener listener) {
        File cameraDir = new File(activity.getCacheDir(), CAMERA_DIR);
        if (!cameraDir.exists()) {
            if (!cameraDir.mkdirs()) {
                if (listener != null) {
                    listener.onResult(null);
                }
                return;
            }

        }
        int requestId = this.requestId.getAndIncrement();
        try {
            File photoFile = File.createTempFile("photo_", ".jpg", cameraDir);
            Uri photoUri = FileProvider.getUriForFile(activity, CAPTURE_MEDIA_FILE_PROVIDER, photoFile);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

            List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                activity.grantUriPermission(packageName, photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            requestsRunners.put(requestId, new SimpleOnImageRequestListener(photoUri,true, listener));

            activity.startActivityForResult(intent, requestId);
        } catch (IOException e) {
            requestsRunners.remove(requestId);
            if (listener != null) {
                listener.onResult(null);
            }
        }
    }

    public void selectImage(Activity activity, OnImageRequestListener listener){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        int requestId = this.requestId.getAndIncrement();
        requestsRunners.put(requestId, new SimpleOnImageRequestListener(listener));
        activity.startActivityForResult(photoPickerIntent, requestId);
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        try {
            SimpleOnImageRequestListener runnable = requestsRunners.get(requestCode);
            if (runnable != null) {
                if (resultCode == Activity.RESULT_OK) {
                    final OnImageRequestListener listener = runnable.listener;
                    if (listener != null) {
                        if (runnable.camera) {
                            listener.onResult(runnable.photoUri);
                            activity.revokeUriPermission(runnable.photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        } else {
                            Uri selectedImage = data.getData();
                            listener.onResult(selectedImage);
                        }

                    }
                } else {
                    final OnImageRequestListener listener = runnable.listener;
                    if (listener != null) {
                        listener.onResult(null);
                    }
                }
            }
        } finally {
            requestsRunners.remove(requestCode);
        }
    }


    private static class SimpleOnImageRequestListener {

        private final OnImageRequestListener listener;
        private Uri photoUri;
        private boolean camera;

        SimpleOnImageRequestListener(Uri photoUri, boolean camera, OnImageRequestListener listener) {
            this.camera = camera;
            this.listener = listener;
            this.photoUri = photoUri;
        }

        SimpleOnImageRequestListener(OnImageRequestListener listener) {
            this(null, false, listener);
        }

    }

}
