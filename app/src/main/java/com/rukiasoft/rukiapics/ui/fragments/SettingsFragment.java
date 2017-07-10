package com.rukiasoft.rukiapics.ui.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.rukiasoft.rukiapics.R;

import java.io.Serializable;



public class SettingsFragment extends PreferenceFragment implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.options);

    }
}


