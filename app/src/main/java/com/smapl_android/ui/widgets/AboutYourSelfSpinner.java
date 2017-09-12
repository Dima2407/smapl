package com.smapl_android.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.*;

import com.smapl_android.R;


public class AboutYourSelfSpinner extends RelativeLayout {

    private static final String TAG = AboutYourSelfSpinner.class.getSimpleName();
    private final String hint;
    private final CharSequence[] items;

    private TextView txtDefault;
    private ArrayAdapter<CharSequence> adapter;

    private Spinner spinner;

    public AboutYourSelfSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.custom_spinner_view, this);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AboutYourSelfSpinner,
                0, 0);

        try {
            hint = a.getString(R.styleable.AboutYourSelfSpinner_hint);
            items = a.getTextArray(R.styleable.AboutYourSelfSpinner_items);
        } finally {
            a.recycle();
        }
        spinner = (Spinner) findViewById(R.id.spinner_custom);
        txtDefault = (TextView) findViewById(R.id.txt_spinner_default);
        txtDefault.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
                txtDefault.setVisibility(GONE);
            }
        });

        adapter = new ArrayAdapter<CharSequence>(getContext(), R.layout.row_text_spinner, items);
        adapter.setDropDownViewResource(R.layout.row_text_spinner_down);

        spinner.setAdapter(adapter);
        if (TextUtils.isEmpty(hint)) {
            txtDefault.setText("");
        } else {
            txtDefault.setText(hint);
        }

    }

    @BindingAdapter(value = {"bind:selectedValue", "bind:selectedValueAttrChanged"}, requireAll = false)
    public static void bindSpinnerData(AboutYourSelfSpinner pAppCompatSpinner, String newSelectedValue, final InverseBindingListener newTextAttrChanged) {
        pAppCompatSpinner.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (newSelectedValue != null) {
            int pos = ((ArrayAdapter<String>) pAppCompatSpinner.spinner.getAdapter()).getPosition(newSelectedValue);
            pAppCompatSpinner.spinner.setSelection(pos, true);
        }
    }

    @InverseBindingAdapter(attribute = "bind:selectedValue", event = "bind:selectedValueAttrChanged")
    public static String captureSelectedValue(AboutYourSelfSpinner pAppCompatSpinner) {
        return (String) pAppCompatSpinner.spinner.getSelectedItem();
    }
}
