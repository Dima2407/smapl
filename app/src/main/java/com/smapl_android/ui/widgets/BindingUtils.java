package com.smapl_android.ui.widgets;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.ObservableField;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.smapl_android.R;

public class BindingUtils {

    @BindingAdapter("bind:onSendActionListener")
    public static void bindImeListener(EditText editText, final Runnable listener) {
        if (listener != null) {
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                        listener.run();
                        handled = true;
                    }
                    return handled;
                }
            });
        }
    }

    @BindingAdapter("bind:focused")
    public static void bindFocused(EditText editText, final ObservableField<Boolean> value) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                value.set(hasFocus);
            }
        });
    }

    @BindingAdapter("bind:photo")
    public static void setPhoto(ImageView imageView, String url){
        ImageLoader.getInstance().displayImage(url, imageView);
    }

    @BindingAdapter("bind:error")
    public static void setError(EditText editText, String error){
        if(!TextUtils.isEmpty(error)){
            editText.setError(error);
        }
    }
}
