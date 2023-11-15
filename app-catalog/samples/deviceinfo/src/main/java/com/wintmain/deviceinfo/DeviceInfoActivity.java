/*
 * Copyright 2023 wintmain
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wintmain.deviceinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.google.android.catalog.framework.annotations.Sample;

/**
 * @Description
 * @Author wintmain
 * @mailto wosintmain@gmail.com
 * @Date 2023-11-05 13:58:04
 */
@Sample(
        name = "Deviceinfo",
        description = "为了显示设备信息，结合Preference;" +
                "由于设备信息所需要的API很多都是@hide，所以先以普通字符串表示",
        documentation = "",
//    owners = ["wintmain"],
        tags = "A-Self_demos"
)
public class DeviceInfoActivity extends AppCompatActivity {
    // 现在的implements XXX替代 PreferenceActivity;

    private static final String TAG = DeviceInfoActivity.class.getSimpleName();

    // Preferences
    private static final String KEY_ANDROID_VERSION = "android_version";
    private static final String KEY_PLACEHOLDER_1 = "placeholder1";
    private static final String KEY_PLACEHOLDER_2 = "placeholder2";
    private static final String KEY_PLACEHOLDER_3 = "placeholder3";
    private static final String KEY_PLACEHOLDER_4 = "placeholder4";
    private static final String KEY_PLACEHOLDER_5 = "placeholder5";
    private static final String KEY_PLACEHOLDER_6 = "placeholder6";
    private static String mUnknown = null;
    private static final String TITLE_TAG = "settingsActivityTitle";
    // 广播接受器示例，没有其他作用
    private final BroadcastReceiver mReceiver = new DeviceInfoReceiver();
    private String title = null;
    FragmentManager supportFragmentManager = getSupportFragmentManager();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placeholder_layout);
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        } else {
            title = (String) savedInstanceState.getCharSequence(TITLE_TAG);
        }

        supportFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (supportFragmentManager.getBackStackEntryCount() == 0) {
                    Log.d(TAG, "getBackStackEntryCount is 0...");
                    setTitle("XXX");
                }
            }
        });

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        registerReceiver(mReceiver, intentFilter);

        if (mUnknown == null) {
            mUnknown = getResources().getString(R.string.device_info_unknown);
        }
    }

    // java.lang.IllegalStateException:
    // Fragment XXX.SettingsFragment must be a public static class to be  properly recreated from instance state.
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
            setPreferencesFromResource(R.xml.device_info, rootKey);
        }

        @Override
        public PreferenceScreen getPreferenceScreen() {
            // at com.wintmain.deviceinfo.DeviceInfoActivity$SettingsFragment.getPreferenceScreen(DeviceInfoActivity.java:116)
            // at androidx.preference.PreferenceFragmentCompat.bindPreferences(PreferenceFragmentCompat.java:518)
            // at androidx.preference.PreferenceFragmentCompat.onViewCreated(PreferenceFragmentCompat.java:275)
            // at androidx.fragment.app.Fragment.performViewCreated(Fragment.java:3147)
            // at androidx.fragment.app.FragmentStateManager.createView(FragmentStateManager.java:588)
            // at androidx.fragment.app.FragmentStateManager.moveToExpectedState(FragmentStateManager.java:272)
            // at androidx.fragment.app.FragmentManager.executeOpsTogether(FragmentManager.java:1943)
            // at androidx.fragment.app.FragmentManager.removeRedundantOperationsAndExecute(FragmentManager.java:1839)
            // at androidx.fragment.app.FragmentManager.execPendingActions(FragmentManager.java:1782)
            // at androidx.fragment.app.FragmentManager.dispatchStateChange(FragmentManager.java:3042)
            // at androidx.fragment.app.FragmentManager.dispatchActivityCreated(FragmentManager.java:2952)
            // at androidx.fragment.app.FragmentController.dispatchActivityCreated(FragmentController.java:263)
            // at androidx.fragment.app.FragmentActivity.onStart(FragmentActivity.java:350)
            // at androidx.appcompat.app.AppCompatActivity.onStart(AppCompatActivity.java:251)
            // ...
            //android.util.Log.d(TAG, android.util.Log.getStackTraceString(new Throwable()));

            // 添加Preferences的summary
            setPreferenceSummary();
            return super.getPreferenceScreen();
        }

        private void setPreferenceSummary() {
            // get android version, get property of ro.build.version.release_or_codename.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                setSummaryText(KEY_ANDROID_VERSION, Build.VERSION.RELEASE_OR_CODENAME);
            }

            setSummaryText(KEY_PLACEHOLDER_1, "PlaceHolder 1");
            setSummaryText(KEY_PLACEHOLDER_2, "PlaceHolder 2");
            setSummaryText(KEY_PLACEHOLDER_3, "PlaceHolder 3");
            setSummaryText(KEY_PLACEHOLDER_4, "PlaceHolder 4");
            setSummaryText(KEY_PLACEHOLDER_5, "PlaceHolder 5");
            setSummaryText(KEY_PLACEHOLDER_6, "Test");
        }

        private void setSummaryText(String preferenceKey, String value) {
            Preference preference = findPreference(preferenceKey);
            if (preference == null) {
                Log.d(TAG, "Cannot find preference...");
                return;
            }
            if (TextUtils.isEmpty(value)) {
                preference.setSummary(mUnknown);
            } else {
                preference.setSummary(value);
            }
        }
    }

    static class DeviceInfoReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
                Log.d(TAG, "AIRPLANE_MODE changed...");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save current activity title so we can set it again after a configuration change
        outState.putCharSequence(TITLE_TAG, title);
    }
}
