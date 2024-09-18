/*
 * Copyright 2023-2024 wintmain
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

package com.wintmain.xtele.debug;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.telephony.CarrierConfigManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.wintmain.xtele.XTeleActivity;
import com.wintmain.xtele.R;

import java.util.Objects;

public class CarrierConfigDebugActivity extends PreferenceActivity {
    private final static int REQUEST_PERMISSIONS = 1;
    private final static int PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
    }

    @Override
    public Intent getIntent() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SHOW_FRAGMENT, ReadConfigFragment.class.getName());
        intent.putExtra(EXTRA_NO_HEADERS, true);
        return intent;
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toast(Context context, String text) {
        if (text == null) {
            text = "";
        }
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static class ReadConfigFragment extends PreferenceFragment {
        private final static String PHONE_PERMISSION = Manifest.permission.READ_PHONE_STATE;
        private boolean mIsPhonePermissionGranted = false;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getActivity().getActionBar().setDisplayOptions(
                    ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!mIsPhonePermissionGranted) {
                    this.requestPhonePermission((Context) getActivity());
                }
            }
            addPreferencesFromResource(R.xml.read_config);
        }

        @Override
        public void onResume() {
            super.onResume();
            Preference preference;
            boolean isEnable;

            for (CarrierConfigParameters cc : CarrierConfigParameters.values()) {
                preference = findPreference(getString(cc.nameId));
                if (mIsPhonePermissionGranted) {
                    isEnable = getBooleanCarrierConfig((Context) getActivity(), cc.configKey);
                    preference.setSummary(getStatus(isEnable));
                } else {
                    preference.setSummary("Please Allow PHONE permission!");
                }
            }
        }

        public enum CarrierConfigParameters {
            VOLTE_AVAILABLE(
                    CarrierConfigManager.KEY_CARRIER_VOLTE_AVAILABLE_BOOL,
                    R.string.current_volte_status),
            VT_AVAILABLE(
                    CarrierConfigManager.KEY_CARRIER_VT_AVAILABLE_BOOL,
                    R.string.current_vilte_status),
            WFC_IMS_AVAILABLE(
                    CarrierConfigManager.KEY_CARRIER_WFC_IMS_AVAILABLE_BOOL,
                    R.string.current_wificall_status);

            public final String configKey;
            public final int nameId;

            CarrierConfigParameters(String configKey, int nameId) {
                this.configKey = configKey;
                this.nameId = nameId;
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions,
                int[] grantResults) {
            if (requestCode == REQUEST_PERMISSIONS) {
                if (grantResults[0] == PERMISSION_GRANTED) {
                    mIsPhonePermissionGranted = true;
                } else {
                    if (shouldShowRequestPermissionRationale(PHONE_PERMISSION)) {
                        ((CarrierConfigDebugActivity) getActivity()).toast(getActivity(),
                                "Need permission to Read Phone State");
                    } else {
                        Intent intent = new Intent((Context) getActivity(), XTeleActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }
        }

        //get Carrier Config Status
        private boolean getBooleanCarrierConfig(Context context, String key) {
            CarrierConfigManager configManager = (CarrierConfigManager) context.getSystemService(
                    Context.CARRIER_CONFIG_SERVICE);
            PersistableBundle b = null;
            if (configManager != null) {
                b = configManager.getConfig();
            }
            return Objects.requireNonNullElseGet(b,
                    CarrierConfigManager::getDefaultConfig).getBoolean(key);
        }

        private String getStatus(boolean isEnable) {
            return isEnable ? getString(R.string.enabled) : getString(R.string.disabled);
        }

        private void requestPhonePermission(Context context) {
            if (context.checkSelfPermission(PHONE_PERMISSION) == PERMISSION_GRANTED) {
                mIsPhonePermissionGranted = true;
            }
            if (!mIsPhonePermissionGranted) {
                requestPermissions(new String[]{PHONE_PERMISSION}, REQUEST_PERMISSIONS);
            }
        }
    }
}
