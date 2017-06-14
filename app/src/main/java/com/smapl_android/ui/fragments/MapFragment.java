package com.smapl_android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapl_android.R;
import com.smapl_android.model.User;
import com.smapl_android.ui.base.BaseFragment;

public class MapFragment extends BaseFragment {

    private static final String TAG = MapFragment.class.getSimpleName();
    private TextView textUserName;
    private TextView textCar;
    private ImageView imageCarPhoto;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*user = getArguments().getParcelable("user");

        textUserName = (TextView) view.findViewById(R.id.text_map_user_name);
        textCar = (TextView) view.findViewById(R.id.text_map_car_brand_model);
        imageCarPhoto = (ImageView) view.findViewById(R.id.img_map_car_photo);

        textUserName.setText(user.getFirstName() + " " + user.getLastName());
        Log.i("model", " name = " + textUserName.getText().toString());
        textCar.setText(user.getCarBrand() + " " + user.getCarModel());
        Log.i("model", " car model = " + textCar.getText().toString());
        imageCarPhoto.setImageBitmap(user.getCarPhoto());*/
    }
}
