package com.smapl_android.ui.widgets;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.smapl_android.R;

public class AboutYourSelfSpinner extends RelativeLayout {

    private static final String TAG = AboutYourSelfSpinner.class.getSimpleName();

    private TextView txtDefault;
    private ArrayAdapter<CharSequence> adapter;
    private Spinner spinner;
    private Context context;

    public AboutYourSelfSpinner(Context context) {
        super(context);
        init(context);
    }

    public AboutYourSelfSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AboutYourSelfSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setParams(@ArrayRes int stringArray, @LayoutRes int resoirseId, String defaultText) {
        adapter = ArrayAdapter.createFromResource(context, stringArray, resoirseId);
        spinner.setAdapter(adapter);
        if (defaultText == null) {
            txtDefault.setText("");
        } else {
            txtDefault.setText(defaultText);
        }
    }

    public void setParams(@ArrayRes int stringArray, @LayoutRes int resoirseId, @LayoutRes int dropdownResourseId, String defaultText) {
        setParams(stringArray, resoirseId, defaultText);
        adapter.setDropDownViewResource(dropdownResourseId);
    }

    public void setParams(@ArrayRes int stringArray, String defoultText) {
        setParams(stringArray, R.layout.row_text_spinner, defoultText);
        adapter.setDropDownViewResource(R.layout.row_text_spinner_down);
    }

    private void init(Context context) {
        removeAllViewsInLayout();
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_spinner_view, this);
        spinner = (Spinner) view.findViewById(R.id.spinner_custom);
        txtDefault = (TextView) view.findViewById(R.id.txt_spinner_default);
        txtDefault.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
                txtDefault.setVisibility(GONE);
            }
        });
    }

    public Object getSelectedItem() {
        return spinner.getSelectedItem();
    }
}
