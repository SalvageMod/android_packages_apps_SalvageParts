package com.salvagemod.salvageparts.activities;

import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.salvagemod.salvageparts.R;


public class MainActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.salvageparts);
    }
}
