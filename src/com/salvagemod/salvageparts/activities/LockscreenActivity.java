package com.salvagemod.salvageparts.activities;

import com.salvagemod.salvageparts.R;

import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import java.io.File;
import java.util.ArrayList;

public class LockscreenActivity extends PreferenceActivity {

    private static final String LOCKSCREEN_MUSIC_CONTROLS = "lockscreen_music_controls";
    private static final String LOCKSCREEN_ALWAYS_MUSIC_CONTROLS = "lockscreen_always_music_controls";
    
    private CheckBoxPreference mMusicControlPref;
    private CheckBoxPreference mAlwaysMusicControlPref;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.lockscreen_settings_title_subhead);
        addPreferencesFromResource(R.xml.lockscreen_settings);

        PreferenceScreen prefSet = getPreferenceScreen();
                
        /* Music Controls */
        mMusicControlPref = (CheckBoxPreference) prefSet.findPreference(LOCKSCREEN_MUSIC_CONTROLS);
        mMusicControlPref.setChecked(Settings.System.getInt(getContentResolver(), 
                Settings.System.LOCKSCREEN_MUSIC_CONTROLS, 1) == 1);

        /* Always Display Music Controls */
        mAlwaysMusicControlPref = (CheckBoxPreference) prefSet.findPreference(LOCKSCREEN_ALWAYS_MUSIC_CONTROLS);
        mAlwaysMusicControlPref.setChecked(Settings.System.getInt(getContentResolver(), 
                Settings.System.LOCKSCREEN_ALWAYS_MUSIC_CONTROLS, 0) == 1);

    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mMusicControlPref) {
            value = mMusicControlPref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCKSCREEN_MUSIC_CONTROLS, value ? 1 : 0);
            return true;
        } else if (preference == mAlwaysMusicControlPref) {
            value = mAlwaysMusicControlPref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCKSCREEN_ALWAYS_MUSIC_CONTROLS, value ? 1 : 0);
            return true;
        }
        return false;
    }
}
