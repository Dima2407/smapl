package com.smapl_android.ui.fragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.smapl_android.core.CoreService;
import com.smapl_android.ui.BaseActivity;

public abstract class BaseFragment extends Fragment {

    protected CoreService getCoreService() {
        final FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            return baseActivity.getCoreService();
        }
        throw new UnsupportedOperationException("Not in " + BaseActivity.class.getName());
    }

    protected void showProgress(String title, String message) {
        final FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            baseActivity.showProgress(title, message);
        }else {
            throw new UnsupportedOperationException("Not in " + BaseActivity.class.getName());
        }
    }

    protected void hideProgress() {
        final FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            baseActivity.hideProgress();
        }else {
            throw new UnsupportedOperationException("Not in " + BaseActivity.class.getName());
        }
    }

    protected void showMessage(String title, String message) {
        final FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            baseActivity.showMessage(title, message);
        }else {
            throw new UnsupportedOperationException("Not in " + BaseActivity.class.getName());
        }
    }
}
