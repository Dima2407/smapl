package com.smapl_android.core;

import com.smapl_android.R;
import com.smapl_android.ui.base.CoreActivity;

import java.lang.ref.WeakReference;

public class CoreRequest<T> {

    private final WeakReference<CoreActivity> activity;
    private final WeakReference<CoreService> coreService;
    private boolean displayError = false;
    private final SuccessOutput<T> successOutput = new SuccessOutput<T>() {
        @Override
        public void onSuccess(T result) {
            if (activity.get() != null) {
                activity.get().hideProgress();
            }
        }
    };

    private final ErrorOutput errorOutput = new ErrorOutput() {
        @Override
        public void onError(String error) {
            if (activity.get() != null) {
                activity.get().hideProgress();
            }
            if (displayError) {
                if (activity.get() != null) {
                    activity.get().showMessage(activity.get().getString(R.string.app_name),
                            error);
                }
            }
        }
    };
    private SuccessOutput<T> successCallback;

    public CoreRequest(CoreActivity activity, CoreService coreService) {
        this.activity = new WeakReference<CoreActivity>(activity);
        this.coreService = new WeakReference<CoreService>(coreService);
    }

    public CoreRequest<T> withLoading(String title, String message) {
        this.activity.get().showProgress(title, message);
        return this;
    }

    public CoreRequest<T> withLoading(String message) {
        return withLoading(activity.get().getString(R.string.app_name), message);
    }

    public CoreRequest<T> withLoading(int title, int message) {
        return withLoading(activity.get().getString(title), activity.get().getString(message));
    }

    public CoreRequest<T> withLoading(int message) {
        return withLoading(R.string.app_name, message);
    }

    public CoreRequest<T> handleErrorAsDialog() {
        displayError = true;
        return this;
    }

    public CoreRequest<T> handleSuccess(SuccessOutput<T> callback){
        successCallback = callback;
        return this;
    }

    void processResult(T result) {
        successOutput.onSuccess(result);
        if(successCallback != null){
            successCallback.onSuccess(result);
        }
    }

    void processError(String error) {
        errorOutput.onError(error);
    }
}
