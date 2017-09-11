package com.smapl_android.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.smapl_android.R;

public class OverlayImageButton extends ImageButton {
    private final int overlayContainerId;
    private final OnLayoutChangeListener onLayoutChangeListener = new OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

            final int width = right - left;
            final int height = bottom - top;

            final FrameLayout.LayoutParams itemLp = (FrameLayout.LayoutParams) getLayoutParams();
            itemLp.topMargin = height - itemLp.height / 2;
            itemLp.leftMargin = width / 2 - itemLp.width / 2;

            setLayoutParams(itemLp);
        }
    };

    public OverlayImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.OverlayImageButton,
                0, 0);

        try {
            overlayContainerId = a.getResourceId(R.styleable.OverlayImageButton_overlay_at, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final ViewParent parent = getParent();
        if(parent instanceof FrameLayout){
            FrameLayout fl = (FrameLayout) parent;
            final View overlay = fl.findViewById(overlayContainerId);
            overlay.addOnLayoutChangeListener(onLayoutChangeListener);
        }
    }
}
