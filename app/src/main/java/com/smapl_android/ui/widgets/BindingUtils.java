package com.smapl_android.ui.widgets;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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

    @BindingAdapter("photo")
    public static void setPhoto(ImageView imageView, String url){
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }

    @BindingAdapter("circlePhoto")
    public static void setCirclePhoto(ImageView imageView, String url){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new CircleCrop());
        Glide.with(imageView.getContext()).load(url).apply(requestOptions).into(imageView);
    }

    @BindingAdapter("roundedSquarePhoto")
    public static void setRoundedSquarePhoto(ImageView imageView, String url){
        if(TextUtils.isEmpty(url)){
            imageView.setVisibility(View.INVISIBLE);
            return;
        }else {
            imageView.setVisibility(View.VISIBLE);
        }
        RequestOptions requestOptions = new RequestOptions();
        int radius = imageView.getContext().getResources().getDimensionPixelSize(R.dimen.photo_corner);
        requestOptions.transform(new RoundedCorners(radius));
        Glide.with(imageView.getContext()).load(url).apply(requestOptions).into(imageView);
    }

    @BindingAdapter("bind:error")
    public static void setError(EditText editText, String error){
        if(!TextUtils.isEmpty(error)){
            editText.setError(error);
        }
    }
}
