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

package com.salvagemod.salvageparts.intents;

import com.salvagemod.salvageparts.activities.PerformanceActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemProperties;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class CPUReceiver extends BroadcastReceiver {

    private static final String TAG = "CPUSettings";

    private static final String CPU_SETTINGS_PROP = "sys.cpufreq.restored";

    @Override
    public void onReceive(Context ctx, Intent intent) {
        if (SystemProperties.getBoolean(CPU_SETTINGS_PROP, false) == false
                && intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)) {
            SystemProperties.set(CPU_SETTINGS_PROP, "true");
            configureCPU(ctx);
        } else {
            SystemProperties.set(CPU_SETTINGS_PROP, "false");
        }
    }

    private void configureCPU(Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

        if (prefs.getBoolean(PerformanceActivity.SOB_PREF, false) == false) {
            Log.i(TAG, "Restore disabled by user preference.");
            return;
        }

        String governor = prefs.getString(PerformanceActivity.GOV_PREF, null);
        String minFrequency = prefs.getString(PerformanceActivity.MIN_FREQ_PREF, null);
        String maxFrequency = prefs.getString(PerformanceActivity.MAX_FREQ_PREF, null);
        boolean noSettings = (governor == null) && (minFrequency == null) && (maxFrequency == null);

        if (noSettings) {
            Log.d(TAG, "No settings saved. Nothing to restore.");
        } else {
            List<String> governors = Arrays.asList(PerformanceActivity.readOneLine(
                    PerformanceActivity.GOVERNORS_LIST_FILE).split(" "));
            List<String> frequencies = Arrays.asList(PerformanceActivity.readOneLine(
                    PerformanceActivity.FREQ_LIST_FILE).split(" "));
            if (governor != null && governors.contains(governor)) {
                PerformanceActivity.writeOneLine(PerformanceActivity.GOVERNOR, governor);
            }
            if (maxFrequency != null && frequencies.contains(maxFrequency)) {
                PerformanceActivity.writeOneLine(PerformanceActivity.FREQ_MAX_FILE, maxFrequency);
            }
            if (minFrequency != null && frequencies.contains(minFrequency)) {
                PerformanceActivity.writeOneLine(PerformanceActivity.FREQ_MIN_FILE, minFrequency);
            }
            Log.d(TAG, "CPU settings restored.");
        }
    }
}
