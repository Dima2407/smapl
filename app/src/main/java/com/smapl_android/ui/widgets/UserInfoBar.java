package com.smapl_android.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.smapl_android.R;

public class UserInfoBar extends RelativeLayout {

    private final TextView textUserName;
    private final TextView textCarBrandModel;
    private final ImageView imageCarPhoto;
    private final int leftSpace;

    public UserInfoBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.widget_user_info, this);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.UserInfoBar,
                0, 0);

        try {
            leftSpace = a.getDimensionPixelSize(R.styleable.UserInfoBar_leftSpace, 0);
        } finally {
            a.recycle();
        }

        textUserName = (TextView) findViewById(R.id.text_map_user_name);
        textCarBrandModel = (TextView) findViewById(R.id.text_map_car_brand_model);
        imageCarPhoto = (ImageView) findViewById(R.id.img_map_car_photo);

        final LayoutParams uParams = (LayoutParams) textUserName.getLayoutParams();
        uParams.addRule(RIGHT_OF, imageCarPhoto.getId());
        uParams.addRule(ALIGN_TOP, imageCarPhoto.getId());
        uParams.leftMargin = leftSpace;

        textUserName.setLayoutParams(uParams);

        final LayoutParams cParams = (LayoutParams) textCarBrandModel.getLayoutParams();
        cParams.addRule(BELOW, textUserName.getId());
        cParams.addRule(ALIGN_LEFT, textUserName.getId());

        textCarBrandModel.setLayoutParams(cParams);
    }

    @BindingAdapter("userName")
    public static void setUserName(UserInfoBar container, String text) {
        container.textUserName.setText(text);
    }

    @BindingAdapter("carInfo")
    public static void setCarInfo(UserInfoBar container, String text){
        container.textCarBrandModel.setText(text);
    }

    @BindingAdapter("carPhoto")
    public static void setCarPhoto(UserInfoBar container, String url){
        ImageLoader.getInstance().displayImage(url, container.imageCarPhoto);
    }
}
