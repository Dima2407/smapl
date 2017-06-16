package com.smapl_android.ui.base;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import com.smapl_android.R;
import com.smapl_android.SmaplApplication;
import com.smapl_android.core.CoreService;

public abstract class CoreActivity extends AppCompatActivity {

    private final PermissionsManager permissionsManager = new PermissionsManager();
    private final ImageManager imageManager = new ImageManager();
    //region progress
    private ProgressDialog progressDialog;

    public CoreService getCoreService() {
        final SmaplApplication application = (SmaplApplication) getApplication();
        return application.getCoreService();
    }

    public void showProgress(String message) {
        showProgress(getString(R.string.app_name), message);
    }

    public void showProgress(String title, String message) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
            progressDialog = null;
        }
        progressDialog = ProgressDialog.show(this, title, message);
    }

    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
        progressDialog = null;
    }
    //endregion

    //region dialog
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    //endregion

    //region navigation
    public void replaceContent(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit();
    }

    public void replaceContentWithHistory(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(android.R.id.content, fragment)
                .commit();
    }
    //endregion

    //region permissions

    public void runWithPermissions(OnPermissionsRequestListener listener, String... permissions) {
        permissionsManager.runWithPermissions(this, listener, permissions, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //endregion

    //region system
    public void takePhoto(final OnImageRequestListener listener) {
        runWithPermissions(new OnPermissionsRequestListener() {
            @Override
            public void onSuccess() {
                imageManager.takePhoto(CoreActivity.this, listener);
            }

            @Override
            public void onFail() {
                if (listener != null) {
                    listener.onResult(null);
                }
            }
        }, new String[]{Manifest.permission.CAMERA});
    }

    public void selectPhoto(final OnImageRequestListener listener) {
        imageManager.selectImage(this, listener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageManager.onActivityResult(this, requestCode, resultCode, data);
    }

    //endregion
}
