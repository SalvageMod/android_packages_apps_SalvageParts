/*
 * Copyright (C) 2011 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.salvagemod.salvageparts.activities;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.salvagemod.salvageparts.R;

public class LockscreenWidgetsActivity extends PreferenceActivity implements
        OnPreferenceChangeListener {

    private static final String LOCKSCREEN_MUSIC_CONTROLS = "lockscreen_music_controls";

    private static final String LOCKSCREEN_NOW_PLAYING = "pref_lockscreen_now_playing";

    private static final String LOCKSCREEN_ALBUM_ART = "pref_lockscreen_album_art";

    private static final String LOCKSCREEN_MUSIC_CONTROLS_HEADSET = "pref_lockscreen_music_headset";

    private static final String LOCKSCREEN_ALWAYS_MUSIC_CONTROLS = "lockscreen_always_music_controls";

    private static final String LOCKSCREEN_ALWAYS_BATTERY = "lockscreen_always_battery";

    private CheckBoxPreference mMusicControlPref;

    private CheckBoxPreference mNowPlayingPref;

    private CheckBoxPreference mAlbumArtPref;

    private CheckBoxPreference mAlwaysMusicControlPref;

    private CheckBoxPreference mAlwaysBatteryPref;

    private ListPreference mLockscreenMusicHeadsetPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.lockscreen_settings_title_subhead);
        addPreferencesFromResource(R.xml.lockscreen_widgets_settings);

        PreferenceScreen prefSet = getPreferenceScreen();

        /* Music Controls */
        mMusicControlPref = (CheckBoxPreference) prefSet.findPreference(LOCKSCREEN_MUSIC_CONTROLS);
        mMusicControlPref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.LOCKSCREEN_MUSIC_CONTROLS, 1) == 1);

        /* Now Playing / Song title */
        mNowPlayingPref = (CheckBoxPreference) prefSet.findPreference(LOCKSCREEN_NOW_PLAYING);
        mNowPlayingPref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.LOCKSCREEN_NOW_PLAYING, 1) == 1);

        /* Album Art */
        mAlbumArtPref = (CheckBoxPreference) prefSet.findPreference(LOCKSCREEN_ALBUM_ART);
        mAlbumArtPref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.LOCKSCREEN_ALBUM_ART, 1) == 1);

        /* Show Music Controls with Headset */
        mLockscreenMusicHeadsetPref = (ListPreference) prefSet
                .findPreference(LOCKSCREEN_MUSIC_CONTROLS_HEADSET);
        int lockscreenMusicHeadsetPref = Settings.System.getInt(getContentResolver(),
                Settings.System.LOCKSCREEN_MUSIC_CONTROLS_HEADSET, 0);
        mLockscreenMusicHeadsetPref.setValue(String.valueOf(lockscreenMusicHeadsetPref));
        mLockscreenMusicHeadsetPref.setOnPreferenceChangeListener(this);

        /* Always Display Music Controls */
        mAlwaysMusicControlPref = (CheckBoxPreference) prefSet
                .findPreference(LOCKSCREEN_ALWAYS_MUSIC_CONTROLS);
        boolean alwaysMusicControlPref = Settings.System.getInt(getContentResolver(),
                Settings.System.LOCKSCREEN_ALWAYS_MUSIC_CONTROLS, 0) == 1;
        mAlwaysMusicControlPref.setChecked(alwaysMusicControlPref);
        mLockscreenMusicHeadsetPref.setEnabled(!alwaysMusicControlPref);

        /* Always Display Battery Status */
        mAlwaysBatteryPref = (CheckBoxPreference) prefSet.findPreference(LOCKSCREEN_ALWAYS_BATTERY);
        mAlwaysBatteryPref.setChecked(Settings.System.getInt(getContentResolver(),
                Settings.System.LOCKSCREEN_ALWAYS_BATTERY, 0) == 1);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mMusicControlPref) {
            value = mMusicControlPref.isChecked();
            Settings.System.putInt(getContentResolver(), Settings.System.LOCKSCREEN_MUSIC_CONTROLS,
                    value ? 1 : 0);
            return true;
        } else if (preference == mNowPlayingPref) {
            value = mNowPlayingPref.isChecked();
            Settings.System.putInt(getContentResolver(), Settings.System.LOCKSCREEN_NOW_PLAYING,
                    value ? 1 : 0);
            return true;
        } else if (preference == mAlbumArtPref) {
            value = mAlbumArtPref.isChecked();
            Settings.System.putInt(getContentResolver(), Settings.System.LOCKSCREEN_ALBUM_ART,
                    value ? 1 : 0);
            return true;
        } else if (preference == mAlwaysMusicControlPref) {
            value = mAlwaysMusicControlPref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCKSCREEN_ALWAYS_MUSIC_CONTROLS, value ? 1 : 0);
            mLockscreenMusicHeadsetPref.setEnabled(!value);
            return true;
        } else if (preference == mAlwaysBatteryPref) {
            value = mAlwaysBatteryPref.isChecked();
            Settings.System.putInt(getContentResolver(), Settings.System.LOCKSCREEN_ALWAYS_BATTERY,
                    value ? 1 : 0);
            return true;
        }
        return false;
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mLockscreenMusicHeadsetPref) {
            int lockscreenMusicHeadsetPref = Integer.valueOf((String) newValue);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.LOCKSCREEN_MUSIC_CONTROLS_HEADSET, lockscreenMusicHeadsetPref);
            return true;
        }
        return false;
    }
}
