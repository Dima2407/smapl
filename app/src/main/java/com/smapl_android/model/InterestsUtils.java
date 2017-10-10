package com.smapl_android.model;

import android.content.Context;
import android.text.TextUtils;

import com.smapl_android.R;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class InterestsUtils {

    public static String fromResponseToViewString(String keys, Context context){
        return keys;
    }

    public static String fromViewStringToRequestKeys(String selectedInterests, Context context){
        return selectedInterests;
    }

    public static Integer[] prepareSelected(String selectedInterests, Context context){
        List<String> interestsArray = Arrays.asList(context.getResources().getStringArray(R.array.interests));
        Set<Integer> selectedIndex = new LinkedHashSet<>();
        if (!TextUtils.isEmpty(selectedInterests)) {
            for (int i = 0; i < interestsArray.size(); i++) {
                String interest = interestsArray.get(i);
                if(selectedInterests.contains(interest)){
                    selectedIndex.add(i);
                }
            }
        }
        Integer [] indexes = new Integer[selectedIndex.size()];
        return selectedIndex.toArray(indexes);
    }
}
