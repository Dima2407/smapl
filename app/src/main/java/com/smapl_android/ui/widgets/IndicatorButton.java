package com.smapl_android.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.smapl_android.R;

public class IndicatorButton extends LinearLayout implements Checkable {
    private final String title;
    private final Drawable icon;
    private final View indicatorView;
    private final ImageView iconView;
    private final TextView titleView;
    private boolean checked;

    public IndicatorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.widget_indicator_button, this);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.IndicatorButton,
                0, 0);

        try {
            title = a.getString(R.styleable.IndicatorButton_indicator_title);
            icon = a.getDrawable(R.styleable.IndicatorButton_indicator_icon);
        } finally {
            a.recycle();
        }
        iconView = (ImageView) findViewById(android.R.id.icon);
        titleView = (TextView) findViewById(android.R.id.text1);
        indicatorView = findViewById(R.id.top_indicator);

        iconView.setImageDrawable(icon);
        titleView.setText(title);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);

    }

    @BindingAdapter(value = {"bind:checked", "bind:checkedAttrChanged"}, requireAll = false)
    public static void setUserName(final IndicatorButton container, Boolean data, final InverseBindingListener newTextAttrChanged) {
        container.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setChecked(true);
                newTextAttrChanged.onChange();
            }
        });
        if (data != null) {
            container.setChecked(data);
        }
    }

    @InverseBindingAdapter(attribute = "bind:checked", event = "bind:checkedAttrChanged")
    public static Boolean captureSelectedValue(IndicatorButton container) {
        return container.isChecked();
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            indicatorView.setVisibility(VISIBLE);
        } else {
            indicatorView.setVisibility(INVISIBLE);
        }
    }

    @Override
    public void toggle() {
        setChecked(!isChecked());
    }

}
