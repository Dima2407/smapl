package com.smapl_android.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.smapl_android.R;

public class SimpleTopBar extends LinearLayout {

    private final String centerText;
    private final String leftText;
    private final String rightText;
    private final Button buttonLeft;
    private final Button buttonRight;
    private final TextView textTitle;

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
        buttonLeft = (Button)findViewById(R.id.btn_left);
        buttonRight = (Button)findViewById(R.id.btn_right);
        textTitle = (TextView)findViewById(R.id.title_center);

        setTextForItem(buttonLeft, leftText);
        setTextForItem(textTitle, centerText);
        setTextForItem(buttonRight, rightText);
    }

    @BindingAdapter("leftClick")
    public static void setLeftClick(SimpleTopBar container, OnClickListener listener) {
        if (listener != null) {
            container.buttonLeft.setOnClickListener(listener);
        }
    }

    @BindingAdapter("rightClick")
    public static void setRightClick(SimpleTopBar container, OnClickListener listener) {
        if (listener != null) {
            container.buttonRight.setOnClickListener(listener);
        }
    }

    public void setTextForItem(TextView itemView, String itemText){
        if(TextUtils.isEmpty(itemText)){
            itemView.setVisibility(GONE);
        }else {
            itemView.setText(itemText);
        }
    }
}
