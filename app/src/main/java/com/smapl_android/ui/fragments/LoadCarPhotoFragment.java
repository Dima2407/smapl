package com.smapl_android.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.smapl_android.R;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.model.User;
import com.smapl_android.ui.base.BaseFragment;

import java.io.IOException;

import com.smapl_android.ui.base.OnImageRequestListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class LoadCarPhotoFragment extends BaseFragment {

    private static final int GALLERY_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final String TAG = LoadCarPhotoFragment.class.getSimpleName();
    //private ImageView imageView;
    RelativeLayout imgLayout;
    private User user;
    private CircleImageView circleImageView;

    private static final String [] IMAGE_PERMISSIONS=  {Manifest.permission.CAMERA};

    private static final int REQUEST_IMAGE_PERMISSIONS = 10;

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

       // imageView = (ImageView) view.findViewById(R.id.img_car_photo);
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
            }
        });
    }

    private void loadPhotoFromGallery() {
        getCoreActivity().selectPhoto(new OnImageRequestListener() {
            @Override
            public void onResult(Uri data) {
                Log.d(TAG, "onResult: " + data);

            }
        });
    }

    private void registration(){
        Log.i("crazj", "registration()");
        final CoreRequest<Boolean> request = getCoreService()
                .newRequest(getCoreActivity());
        request
                .withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(new SuccessOutput<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {

                    }
                });
        getCoreService().registration(user, request);
    }

    private void toMainScreen() {

        Log.i("crazj", "toMainScreen()");

        registration();

        Log.i("crazj", "posr registration toMainScreen()");

        MainScreenFragment mainScreenFragment = new MainScreenFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);

        mainScreenFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, mainScreenFragment)
                .commit();
    }

}
