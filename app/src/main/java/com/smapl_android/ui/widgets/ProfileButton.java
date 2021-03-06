package com.smapl_android.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smapl_android.R;

public class ProfileButton extends RelativeLayout {

    public static final int TOP_VISIBLE = 1;
    public static final int BOTTOM_VISIBLE = 2;
    private final String content;
    private final int lineHeight;
    private final int lineColor;
    private final int lineSpace;
    private final int visibleLines;

    private TextView contentView;
    private View bottomLineView;

    public ProfileButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.widget_profile_button, this);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ProfileButton,
                0, 0);

        try {
            content = a.getString(R.styleable.ProfileButton_content);
            lineHeight = a.getDimensionPixelSize(R.styleable.ProfileButton_line_height, 0);
            lineColor = a.getColor(R.styleable.ProfileButton_line_color, 0);
            lineSpace = a.getDimensionPixelSize(R.styleable.ProfileButton_line_space, 0);
            visibleLines = a.getInt(R.styleable.ProfileButton_lines, TOP_VISIBLE | BOTTOM_VISIBLE);
        } finally {
            a.recycle();
        }

        contentView = (TextView) findViewById(R.id.content);
        bottomLineView = findViewById(R.id.bottom_line);

        final LayoutParams bLayoutParams = (LayoutParams) bottomLineView.getLayoutParams();
        bLayoutParams.height = lineHeight;
        bLayoutParams.topMargin = lineSpace;
        bLayoutParams.addRule(BELOW, R.id.content);
        bottomLineView.setLayoutParams(bLayoutParams);

        if ((visibleLines & BOTTOM_VISIBLE) == BOTTOM_VISIBLE) {
            bottomLineView.setVisibility(VISIBLE);
        } else {
            bottomLineView.setVisibility(INVISIBLE);
        }


        bottomLineView.setBackgroundColor(lineColor);

        contentView.setText(content);
    }
}
