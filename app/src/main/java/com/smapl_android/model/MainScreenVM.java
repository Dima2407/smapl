package com.smapl_android.model;

import android.databinding.Observable;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public class MainScreenVM {

    public final ObservableField<Boolean> mapActive = new ObservableField<>(true);

    public final ObservableField<Boolean> profileActive = new ObservableField<>();

    public final ObservableField<Boolean> historyActive = new ObservableField<>();

    {
        mapActive.addOnPropertyChangedCallback(getCallback(historyActive, profileActive));
        historyActive.addOnPropertyChangedCallback(getCallback(mapActive, profileActive));
        profileActive.addOnPropertyChangedCallback(getCallback(mapActive, historyActive));
    }

    @NonNull
    private Observable.OnPropertyChangedCallback getCallback(final ObservableField<Boolean>... oposites) {
        return new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                ObservableField<Boolean> field = (ObservableField<Boolean>) sender;
                if(field.get()) {
                    for (ObservableField<Boolean> op : oposites) {
                        op.set(!field.get());
                    }
                }
            }
        };
    }

    public void init() {
        final List<ObservableField<Boolean>> fields = Arrays.asList(mapActive, historyActive, profileActive);
        for(ObservableField<Boolean> f : fields){
            if(f.get()){
                f.set(false);
                f.set(true);
                break;
            }
        }
    }
}
