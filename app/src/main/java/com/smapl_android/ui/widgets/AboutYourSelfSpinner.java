package com.smapl_android.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.smapl_android.R;

import java.util.Arrays;
import java.util.List;


public class AboutYourSelfSpinner extends Spinner {

    private static final String TAG = AboutYourSelfSpinner.class.getSimpleName();
    private final String hint;
    private final CharSequence[] entities;

    private ArrayAdapter<CharSequence> adapter;

    public AboutYourSelfSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AboutYourSelfSpinner,
                0, 0);

        try {
            hint = a.getString(R.styleable.AboutYourSelfSpinner_hint);
            entities = a.getTextArray(R.styleable.AboutYourSelfSpinner_items);
        } finally {
            a.recycle();
        }

        adapter = new PrettyAdapter(getContext(), hint, entities);
        setAdapter(adapter);

    }
    private String getItemText(int position){
        return (String) adapter.getItem(position);
    }

    @BindingAdapter(value = {"bind:selectedValue", "bind:selectedValueAttrChanged"}, requireAll = false)
    public static void bindSpinnerData(final AboutYourSelfSpinner pAppCompatSpinner, String newSelectedValue, final InverseBindingListener newTextAttrChanged) {
        pAppCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                pAppCompatSpinner.setSelection(0, true);
            }
        });
        if(TextUtils.isEmpty(newSelectedValue)){
            pAppCompatSpinner.setSelection(0, true);
        }else {
            int pos = pAppCompatSpinner.adapter.getPosition(newSelectedValue);
            if(pos > 0) {
                pAppCompatSpinner.setSelection(pos, false);
            }
        }
    }

    @InverseBindingAdapter(attribute = "bind:selectedValue", event = "bind:selectedValueAttrChanged")
    public static String captureSelectedValue(AboutYourSelfSpinner pAppCompatSpinner) {
        int selectedItemPosition = pAppCompatSpinner.getSelectedItemPosition();
        if(selectedItemPosition == 0){
            return null;
        }
        return pAppCompatSpinner.getItemText(selectedItemPosition);
    }

    private static class PrettyAdapter extends ArrayAdapter<CharSequence>{

        private final static int TYPE_HINT = 0;
        private final static int TYPE_ITEM = 1;

        private final String noSelectionHint;
        @NonNull
        private final List<CharSequence> objects;

        public PrettyAdapter(@NonNull Context context, String noSelectionHint, @NonNull CharSequence[] objects) {
            super(context, android.R.layout.simple_spinner_dropdown_item);
            this.noSelectionHint = noSelectionHint;
            this.objects = Arrays.asList(objects);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(getItemViewType(position) == TYPE_HINT){
                TextView textView = new TextView(getContext());
                textView.setText(getItem(position));
                return textView;
            }else {
                TextView textView = new TextView(getContext());
                textView.setText(getItem(position));
                return textView;
            }
        }

        @Override
        public int getCount() {
            return objects.size() + 1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getItem(int position) {
            if(position == 0){
                return noSelectionHint;
            }
            return objects.get(position -1);
        }

        @Override
        public int getPosition(@Nullable CharSequence item) {
            return objects.indexOf(item) + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HINT;
            } else {
                return TYPE_ITEM;
            }
        }
    }
}
