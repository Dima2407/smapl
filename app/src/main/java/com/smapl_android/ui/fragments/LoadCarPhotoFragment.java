package com.smapl_android.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.model.User;
import com.smapl_android.ui.base.BaseFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.smapl_android.ui.base.OnImageRequestListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class LoadCarPhotoFragment extends BaseFragment {

    private static final String TAG = LoadCarPhotoFragment.class.getSimpleName();
    private RelativeLayout imgLayout;
    private User user;
    private CircleImageView circleImageView;
    private Bitmap bitmap;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_load_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = getArguments().getParcelable("user");

        circleImageView = (CircleImageView) view.findViewById(R.id.img_circle_load_photo);

        imgLayout = (RelativeLayout) view.findViewById(R.id.relative_img_load_photo);
        imgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        view.findViewById(R.id.btn_load_car_photo_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainScreen();
            }
        });

        view.findViewById(R.id.btn_load_car_photo_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = null;
                toMainScreen();
            }
        });

        view.findViewById(R.id.btn_load_car_photo_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
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
                    bitmap = decodeBitmapFromUri(data, circleImageView.getWidth(),
                            circleImageView.getHeight(), true);
                    circleImageView.setImageBitmap(bitmap);
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
                    bitmap = decodeBitmapFromUri(data, circleImageView.getWidth(),
                            circleImageView.getHeight(), false);
                    circleImageView.setImageBitmap(bitmap);
                }
            }
        });
    }

    private void registration() {

        if (bitmap != null)
            user.setCarPhoto(bitmap);

        final CoreRequest<Boolean> request = getCoreService()
                .newRequest(getCoreActivity());
        request
                .withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(new SuccessOutput<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        Log.d(TAG, "registration " + result);
                    }
                });
        getCoreService().registration(user, request);
    }

    private void toMainScreen() {

        registration();

        MainScreenFragment mainScreenFragment = new MainScreenFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);

        mainScreenFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, mainScreenFragment)
                .commit();
    }


    private Bitmap decodeBitmapFromUri(Uri uri, int reqWidth, int reqHeight, boolean isTakenPhoto) {

        String filePath = isTakenPhoto ? createFileFromUri(uri).getAbsolutePath() : getRealPathFromURI(getContext(), uri);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(getContext().getCacheDir(), "photo");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bitmapdata);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }


}
