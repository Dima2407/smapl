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
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.databinding.FragmentCarLoadPhotoBinding;
import com.smapl_android.ui.activities.MainActivity;
import com.smapl_android.ui.base.BaseFragment;
import com.smapl_android.ui.base.OnImageRequestListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class LoadCarPhotoFragment extends BaseFragment {

    public static final String TAG = LoadCarPhotoFragment.class.getSimpleName();

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
            CoreRequest<Boolean> request = getCoreActivity().newWaitingRequest(new SuccessOutput<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            getCoreService().uploadCarPhoto(presenter.carPhoto.get(), request);
        }else {
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

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
