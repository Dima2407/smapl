package com.smapl_android.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.smapl_android.core.CoreService;
import com.smapl_android.ui.base.CoreActivity;

public abstract class BaseFragment extends Fragment {

    protected CoreService getCoreService() {
        return getCoreActivity().getCoreService();
    }

    protected CoreActivity getCoreActivity() {
        final FragmentActivity activity = getActivity();
        if (activity instanceof CoreActivity) {
            return  (CoreActivity) activity;
        }
        throw new UnsupportedOperationException("Not in " + CoreActivity.class.getName());
    }

    protected void showProgress(String title, String message) {
        final FragmentActivity activity = getActivity();
        if (activity instanceof CoreActivity) {
            CoreActivity coreActivity = (CoreActivity) activity;
            coreActivity.showProgress(title, message);
        }else {
            throw new UnsupportedOperationException("Not in " + CoreActivity.class.getName());
        }
    }

    protected void hideProgress() {
        final FragmentActivity activity = getActivity();
        if (activity instanceof CoreActivity) {
            CoreActivity coreActivity = (CoreActivity) activity;
            coreActivity.hideProgress();
        }else {
            throw new UnsupportedOperationException("Not in " + CoreActivity.class.getName());
        }
    }

    protected void showMessage(String title, String message) {
        final FragmentActivity activity = getActivity();
        if (activity instanceof CoreActivity) {
            CoreActivity coreActivity = (CoreActivity) activity;
            coreActivity.showMessage(title, message);
        }else {
            throw new UnsupportedOperationException("Not in " + CoreActivity.class.getName());
        }
    }

    protected void hideKeyboard() {
        final FragmentActivity activity = getActivity();
        if (activity instanceof CoreActivity) {
            CoreActivity coreActivity = (CoreActivity) activity;
            coreActivity.hideKeyboard();
        }else {
            throw new UnsupportedOperationException("Not in " + CoreActivity.class.getName());
        }
    }
}
