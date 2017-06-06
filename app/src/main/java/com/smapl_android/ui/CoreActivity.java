package com.smapl_android.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import com.smapl_android.R;
import com.smapl_android.SmaplApplication;
import com.smapl_android.core.CoreService;

public abstract class CoreActivity extends AppCompatActivity {

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
}
