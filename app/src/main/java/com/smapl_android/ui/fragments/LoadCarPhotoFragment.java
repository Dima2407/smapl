package com.smapl_android.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smapl_android.R;
import com.smapl_android.core.UploadService;
import com.smapl_android.databinding.FragmentCarLoadPhotoBinding;
import com.smapl_android.net.ApiService;
import com.smapl_android.storage.SessionStorage;
import com.smapl_android.ui.activities.MainActivity;
import com.smapl_android.ui.base.BaseFragment;
import com.smapl_android.ui.base.OnImageRequestListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoadCarPhotoFragment extends BaseFragment {

    public static final String TAG = LoadCarPhotoFragment.class.getSimpleName();
    public static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
   // public static final String EXTRA_PATH = "path";

    private Presenter presenter;


    public static Fragment create() {
        return new LoadCarPhotoFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCarLoadPhotoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_car_load_photo, container, false);
        presenter = new Presenter();
        binding.setPresenter(presenter);
        return binding.getRoot();
    }


    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);
        builder.setNegativeButton(getString(R.string.btn_text_from_gallery), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadPhotoFromGallery();
            }
        });
        builder.setPositiveButton(getString(R.string.btn_text_make_photo), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makePhoto();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void makePhoto() {
        getCoreActivity().takePhoto(new OnImageRequestListener() {
            @Override
            public void onResult(Uri data) {
                Log.d(TAG, "onResult: " + data);
                if (data != null) {
                    presenter.carPhoto.set(createFileFromUri(data).getAbsolutePath());
                }
            }
        });
    }

    private void loadPhotoFromGallery() {
        getCoreActivity().selectPhoto(new OnImageRequestListener() {
            @Override
            public void onResult(Uri data) {
                Log.d(TAG, "onResult: " + data);
                if (data != null) {
                    presenter.carPhoto.set(getRealPathFromURI(getContext(), data));
                }
            }
        });
    }

    private void uploadPhoto() {

        if (!TextUtils.isEmpty(presenter.carPhoto.get())) {
            uploadCarPhoto(presenter.carPhoto.get());
        }
//TODO wait result
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void uploadCarPhoto(String path) {
        final SessionStorage sessionStorage = new SessionStorage(getContext());
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

        OkHttpClient client = new OkHttpClient.Builder().build();


        Response response = null;
        try {
            response = client.newCall(request).execute();
            String res = response.body().string();
            Log.d(TAG, res);
        } catch (IOException e) {
            Log.e(TAG, "uploadCarPhoto: ", e);
        }

    }

   /* public static void uploadCarPhoto(Context context, String filePath) {

        context.startService(new Intent(context, UploadService.class)
                .putExtra(EXTRA_PATH, filePath));
    }*/

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private File createFileFromUri(Uri uri) {

        Bitmap bitmap = null;
        try (InputStream is = getContext().getContentResolver().openInputStream(uri)) {
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            Log.e(TAG, "createFileFromUri: ", e);
        }

        File file = new File(getContext().getCacheDir(), "photo");
        try {
            file.createNewFile();
        } catch (Exception e) {
            Log.e(TAG, "createFileFromUri: ", e);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bitmapdata);
            fos.flush();
        } catch (Exception e) {
            Log.e(TAG, "createFileFromUri: ", e);
        }

        return file;
    }

    public class Presenter extends BasePresenter {

        public ObservableField<String> carPhoto = new ObservableField<>();

        public void onClickForward() {
            uploadPhoto();
        }

        public void onClickSkip() {
            carPhoto.set("");
            uploadPhoto();
        }

        public void onClickSelectPhoto() {
            showDialog();
        }
    }

}
