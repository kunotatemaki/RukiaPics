package com.rukiasoft.rukiapics.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintSet;

/**
 * Created by Roll on 10/7/17.
 */

public class PreferenceTools {

    public static String getNumberOfPicsToRequest(Context context){
        return getStringFromPreferences(context, "number_of_pictures_to_download");
    }

    public static String getStringFromPreferences(Context context, String name) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(name, "0");
    }

}
