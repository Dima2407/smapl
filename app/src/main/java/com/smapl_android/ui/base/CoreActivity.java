package com.smapl_android.ui.base;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.smapl_android.R;
import com.smapl_android.SmaplApplication;
import com.smapl_android.core.CoreRequest;
import com.smapl_android.core.CoreService;
import com.smapl_android.core.SuccessOutput;
import com.smapl_android.model.UserInfo;
import com.smapl_android.services.GeolocationService;
import com.smapl_android.ui.fragments.NoInternetScreenFragment;

public abstract class CoreActivity extends AppCompatActivity {

    private static final String TAG = CoreActivity.class.getSimpleName();

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
        showMessage(title, message, null);
    }

    public void showMessage(String title, String message, final Runnable completed) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (completed != null) {
                    completed.run();
                }
            }
        });
        builder.show();
    }

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    //endregion

    //region navigation
    public void replaceContent(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public void replaceContent(int containerId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    public void replaceContentWithHistory(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    public void replaceContentWithHistory(int containerId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(containerId, fragment)
                .commit();
    }

    public void replaceContentWithTag(int containerId, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment, tag)
                .commit();
    }

    public void replaceContentWithTag(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, fragment, tag)
                .commit();
    }

    public void replaceContentWithHistory(int containerId, Class<? extends Fragment> fr) {
        final String tag = fr.getSimpleName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment == null){
            try {
                fragment = fr.newInstance();
            } catch (Exception e){
                Log.e(TAG, "replaceContentWithHistory: ", e);
            }
        }
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(containerId, fragment, tag)
                .commit();
    }

    public void replaceContentNoHistory(int containerId, FragmentManager fragmentManager, Class<? extends Fragment> fr) {
        final String tag = fr.getSimpleName();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if(fragment == null){
            try {
                fragment = fr.newInstance();
            } catch (Exception e){
                Log.e(TAG, "replaceContentNoHistory: ", e);
            }
        }
        fragmentManager.beginTransaction()
                .replace(containerId, fragment, tag)
                .commit();
    }

    public void replaceContentWithHistoryWithTag(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(android.R.id.content, fragment, tag)
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
        runWithPermissions(new OnPermissionsRequestListener() {
            @Override
            public void onSuccess() {
                imageManager.selectImage(CoreActivity.this, listener);
            }

            @Override
            public void onFail() {
                if (listener != null) {
                    listener.onResult(null);
                }
            }
        }, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean handled = imageManager.onActivityResult(this, requestCode, resultCode, data);
        if (!handled) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public UserInfo getUserInfo() {
        return getCoreService().getUserInfo();
    }

    public <T> CoreRequest<T> newWaitingRequest(SuccessOutput<T> successOutput) {
        final CoreRequest<T> request = getCoreService().newRequest(this);

        request
                .withLoading(R.string.wait_login)
                .handleErrorAsDialog()
                .handleSuccess(successOutput);
        return request;
    }

    //endregion


    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void showNoNetworkScreen() {
        replaceContentWithHistory(new NoInternetScreenFragment());
    }
}
