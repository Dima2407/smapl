package com.smapl_android.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smapl_android.R;

public class SimpleTopBar extends FrameLayout {

    private final String centerText;
    private final String leftText;
    private final String rightText;
    private final LinearLayout buttonLeftContainer;
    private final TextView buttonLeft;
    private final Button buttonRight;
    private final TextView textTitle;
    private final int barHeight;

    public SimpleTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.widget_top_bar, this);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SimpleTopBar,
                0, 0);

        try {
            centerText = a.getString(R.styleable.SimpleTopBar_centerText);
            leftText = a.getString(R.styleable.SimpleTopBar_leftText);
            rightText = a.getString(R.styleable.SimpleTopBar_rightText);
        } finally {
            a.recycle();
        }
        barHeight = context.getResources().getDimensionPixelSize(R.dimen.bar_height);
        buttonLeftContainer = (LinearLayout) findViewById(R.id.btn_left_container);
        buttonLeft = (TextView) findViewById(R.id.btn_left);
        buttonRight = (Button) findViewById(R.id.btn_right);
        textTitle = (TextView) findViewById(R.id.title_center);

        setTextForItem(buttonLeft, leftText);
        if(TextUtils.isEmpty(leftText)){
            buttonLeftContainer.setVisibility(GONE);
        }else {
            buttonLeftContainer.setVisibility(VISIBLE);
        }
        setTextForItem(textTitle, centerText);
        setTextForItem(buttonRight, rightText);
        setBackgroundResource(R.drawable.bar_bg);
    }

    @BindingAdapter("leftClick")
    public static void setLeftClick(SimpleTopBar container, OnClickListener listener) {
        if (listener != null) {
            container.buttonLeftContainer.setOnClickListener(listener);
        }
    }

    @BindingAdapter("rightClick")
    public static void setRightClick(SimpleTopBar container, OnClickListener listener) {
        if (listener != null) {
            container.buttonRight.setOnClickListener(listener);
        }
    }

    @BindingAdapter("rightEnabled")
    public static void setRightClickEnabled(SimpleTopBar container, boolean enabled) {
        container.buttonRight.setEnabled(enabled);
    }

    public void setTextForItem(TextView itemView, String itemText) {
        if (TextUtils.isEmpty(itemText)) {
            itemView.setVisibility(GONE);
        } else {
            itemView.setText(itemText);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), barHeight);
    }
}
