package com.smapl_android.ui.base;

import android.content.DialogInterface;
import android.databinding.ObservableField;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.smapl_android.R;
import com.smapl_android.core.CoreService;
import com.smapl_android.model.InterestsUtils;

import java.util.Arrays;
import java.util.List;

public abstract class BaseFragment extends Fragment {

    protected CoreService getCoreService() {
        return getCoreActivity().getCoreService();
    }

    protected CoreActivity getCoreActivity() {
        final FragmentActivity activity = getActivity();
        if (activity instanceof CoreActivity) {
            return (CoreActivity) activity;
        }
        throw new UnsupportedOperationException("Not in " + CoreActivity.class.getName());
    }

    protected void showProgress(String title, String message) {
        final FragmentActivity activity = getActivity();
        if (activity instanceof CoreActivity) {
            CoreActivity coreActivity = (CoreActivity) activity;
            coreActivity.showProgress(title, message);
        } else {
            throw new UnsupportedOperationException("Not in " + CoreActivity.class.getName());
        }
    }

    protected void hideProgress() {
        final FragmentActivity activity = getActivity();
        if (activity instanceof CoreActivity) {
            CoreActivity coreActivity = (CoreActivity) activity;
            coreActivity.hideProgress();
        } else {
            throw new UnsupportedOperationException("Not in " + CoreActivity.class.getName());
        }
    }


    protected void showMessage(String message) {
        showMessage(message, null);
    }

    protected void showMessage(String message, Runnable completed) {
        final FragmentActivity activity = getActivity();
        if (activity instanceof CoreActivity) {
            CoreActivity coreActivity = (CoreActivity) activity;
            coreActivity.showMessage(getString(R.string.app_name), message, completed);
        } else {
            throw new UnsupportedOperationException("Not in " + CoreActivity.class.getName());
        }
    }

    protected void hideKeyboard() {
        final FragmentActivity activity = getActivity();
        if (activity instanceof CoreActivity) {
            CoreActivity coreActivity = (CoreActivity) activity;
            coreActivity.hideKeyboard();
        } else {
            throw new UnsupportedOperationException("Not in " + CoreActivity.class.getName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideKeyboard();
    }

    protected class BasePresenter {
        public void onClickBack() {
            hideKeyboard();
            getCoreActivity().onBackPressed();
        }

        protected void selectGender(final ObservableField<String> gender) {
            List<String> strings = Arrays.asList(getActivity().getResources().getStringArray(R.array.gender));
            final int selectedIndex = strings.indexOf(gender.get());
            new MaterialDialog.Builder(getActivity())
                    .theme(Theme.LIGHT)
                    .title(R.string.select_gender)
                    .items(R.array.gender)
                    .itemsCallbackSingleChoice(selectedIndex, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            gender.set(text.toString());
                            return true;
                        }
                    })
                    .alwaysCallSingleChoiceCallback()
                    .negativeText(android.R.string.cancel)
                    .show();
        }

        protected void selectAge(final ObservableField<String> age) {
            List<String> strings = Arrays.asList(getActivity().getResources().getStringArray(R.array.age_entities));
            final int selectedIndex = strings.indexOf(age.get());
            new MaterialDialog.Builder(getActivity())
                    .theme(Theme.LIGHT)
                    .title(R.string.select_age)
                    .items(R.array.age_entities)
                    .itemsCallbackSingleChoice(selectedIndex, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            age.set(text.toString());
                            return true;
                        }
                    })
                    .alwaysCallSingleChoiceCallback()
                    .negativeText(android.R.string.cancel)
                    .show();
        }

        protected void selectInterests(final ObservableField<String> interests) {
            Integer[] selectedIndex = InterestsUtils.prepareSelected(interests.get(), getActivity());
            final  String originalInterests = interests.get();

            new MaterialDialog.Builder(getActivity())
                    .theme(Theme.LIGHT)
                    .title(R.string.select_interests)
                    .items(R.array.interests)
                    .itemsCallbackMultiChoice(selectedIndex, new MaterialDialog.ListCallbackMultiChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                            interests.set(TextUtils.join(",", text));
                            return true;
                        }
                    })
                    .cancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            interests.set(originalInterests);
                        }
                    })
                    .alwaysCallMultiChoiceCallback()
                    .negativeText(android.R.string.cancel)
                    .positiveText(android.R.string.ok)
                    .show();
        }

        protected void selectCarBrand(final ObservableField<String> carBrand) {
            List<String> strings = Arrays.asList(getActivity().getResources().getStringArray(R.array.car_brand));
            final int selectedIndex = strings.indexOf(carBrand.get());
            new MaterialDialog.Builder(getActivity())
                    .theme(Theme.LIGHT)
                    .title(R.string.select_car_brand)
                    .items(R.array.car_brand)
                    .itemsCallbackSingleChoice(selectedIndex, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            carBrand.set(text.toString());
                            return true;
                        }
                    })
                    .alwaysCallSingleChoiceCallback()
                    .negativeText(android.R.string.cancel)
                    .show();
        }

        protected void selectCarColor(final ObservableField<String> carColor) {
            List<String> strings = Arrays.asList(getActivity().getResources().getStringArray(R.array.car_color));
            final int selectedIndex = strings.indexOf(carColor.get());
            new MaterialDialog.Builder(getActivity())
                    .theme(Theme.LIGHT)
                    .title(R.string.select_car_color)
                    .items(R.array.car_color)
                    .itemsCallbackSingleChoice(selectedIndex, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            carColor.set(text.toString());
                            return true;
                        }
                    })
                    .alwaysCallSingleChoiceCallback()
                    .negativeText(android.R.string.cancel)
                    .show();
        }
    }
}
