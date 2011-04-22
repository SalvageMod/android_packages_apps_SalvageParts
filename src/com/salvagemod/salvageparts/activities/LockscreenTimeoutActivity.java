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
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.salvagemod.salvageparts.R;

public class LockscreenTimeoutActivity extends PreferenceActivity implements
        OnPreferenceChangeListener {

    private static final String LOCKSCREEN_TIMEOUT_DELAY_PREF = "pref_lockscreen_timeout_delay";

    private static final String LOCKSCREEN_SCREENOFF_DELAY_PREF = "pref_lockscreen_screenoff_delay";

    private ListPreference mScreenLockTimeoutDelayPref;

    private ListPreference mScreenLockScreenOffDelayPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.lockscreen_settings_title_subhead);
        addPreferencesFromResource(R.xml.lockscreen_timeout_settings);

        PreferenceScreen prefSet = getPreferenceScreen();

        /* Screen Lock */
        mScreenLockTimeoutDelayPref = (ListPreference) prefSet
                .findPreference(LOCKSCREEN_TIMEOUT_DELAY_PREF);
        int timeoutDelay = Settings.System.getInt(getContentResolver(),
                Settings.System.SCREEN_LOCK_TIMEOUT_DELAY, 5000);
        mScreenLockTimeoutDelayPref.setValue(String.valueOf(timeoutDelay));
        mScreenLockTimeoutDelayPref.setOnPreferenceChangeListener(this);

        mScreenLockScreenOffDelayPref = (ListPreference) prefSet
                .findPreference(LOCKSCREEN_SCREENOFF_DELAY_PREF);
        int screenOffDelay = Settings.System.getInt(getContentResolver(),
                Settings.System.SCREEN_LOCK_SCREENOFF_DELAY, 0);
        mScreenLockScreenOffDelayPref.setValue(String.valueOf(screenOffDelay));
        mScreenLockScreenOffDelayPref.setOnPreferenceChangeListener(this);

    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mScreenLockTimeoutDelayPref) {
            int timeoutDelay = Integer.valueOf((String) newValue);
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_LOCK_TIMEOUT_DELAY,
                    timeoutDelay);
            return true;
        } else if (preference == mScreenLockScreenOffDelayPref) {
            int screenOffDelay = Integer.valueOf((String) newValue);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.SCREEN_LOCK_SCREENOFF_DELAY, screenOffDelay);
            return true;
        }
        return false;
    }

}
