package com.smapl_android.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.core.UploadService;
import com.smapl_android.databinding.FragmentCarLoadPhotoBinding;
import com.smapl_android.model.UserInfoViewModel;
import com.smapl_android.ui.base.BaseFragment;
import com.smapl_android.ui.base.OnImageRequestListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class LoadCarPhotoFragment extends BaseFragment {

    public static final String TAG = LoadCarPhotoFragment.class.getSimpleName();
    private UserInfoViewModel user;
    private Presenter presenter;


    public static Fragment create(UserInfoViewModel user) {
        final LoadCarPhotoFragment fragment = new LoadCarPhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);

        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCarLoadPhotoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_car_load_photo, container, false);
        presenter = new Presenter();
        binding.setPresenter(presenter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = getArguments().getParcelable("user");
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

    private boolean registration() {

        final CoreRequest<Boolean> request = getCoreService()
                .newRequest(getCoreActivity());
        request
                .withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(new SuccessOutput<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {

                        if (result) {

                            if (!TextUtils.isEmpty(presenter.carPhoto.get())) {
                                UploadService.uploadCarPhoto(getActivity(), presenter.carPhoto.get());
                            }

                            MainScreenFragment mainScreenFragment = new MainScreenFragment();

                            getCoreActivity().replaceContent(mainScreenFragment);
                        }
                    }
                });
        getCoreService().registration(user.toRegistration(getContext()), request);

        return request.isError();
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
            registration();
        }

        public void onClickSkip() {
            carPhoto.set("");
            registration();
        }

        public void onClickSelectPhoto() {
            showDialog();
        }
    }

}
