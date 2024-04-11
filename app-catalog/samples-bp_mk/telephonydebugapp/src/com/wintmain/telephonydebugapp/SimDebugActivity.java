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

package com.wintmain.telephonydebugapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import com.wintmain.telephonydebugapp.R;

public class SimDebugActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    private static final String TAG = "SimDebugActivity";

    private static final String SIM_KEY = "sim";
    private static final String SIM_MCC_MNC_KEY = "mcc_mnc";
    private static final String SIM_IMSI_KEY = "sim_imsi";
    private static final String SIM_ICCID_KEY = "sim_iccid";
    private static final String SIM_GID1_KEY = "sim_gid1";
    private static final String SIM_GID2_KEY = "sim_gid2";
    private static final String SIM_PNN_KEY = "sim_pnn";
    private static final String SIM_SPN_KEY = "sim_spn";
    private static final String SIM_APN_KEY = "sim_apn";

    private ListPreference mSimPref;
    private EditTextPreference mSimMccMncPref;
    private EditTextPreference mSimImsiPref;
    private EditTextPreference mSimIccidPref;
    private EditTextPreference mSimGid1Pref;
    private EditTextPreference mSimGid2Pref;
    private EditTextPreference mSimPnnPref;
    private EditTextPreference mSimSpnPref;
    private EditTextPreference mSimApnPref;

    private int mCurrentSlotId = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Log.i(TAG, "onCreate");
        setContentView(R.layout.sim_debug_activity);

        Button overrideButton = findViewById(R.id.override_sim);
        if (overrideButton != null) {
            overrideButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    overrideSimInfo();
                }
            });
        }

        Button resetButton = findViewById(R.id.reset);
        if (resetButton != null) {
            resetButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    resetSim();
                }
            });
        }

        initPreferences();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.i(TAG, "onPreferenceChange pref: " + preference + "; newValue: " + newValue);

        if (preference == null || newValue == null) return false;

        if (preference == mSimPref) {
            try {
                int slotId = Integer.parseInt((String) newValue);
                if (SubscriptionManager.isValidSlotIndex(slotId) && slotId != mCurrentSlotId) {
                    mCurrentSlotId = slotId;
                    initPreferenceValues();
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "onPreferenceChange NumberFormatException");
            }
        } else if (preference == mSimMccMncPref) {
            String mccmnc = (String) newValue;
            if (TextUtils.isEmpty(mccmnc) || mccmnc.length() > 6 || mccmnc.length() < 5) {
                updatePreference(mSimMccMncPref, "");
                updatePreference(mSimImsiPref, "");
                return false;
            } else {
                String imsi = mSimImsiPref.getText();
                if (!TextUtils.isEmpty(imsi) && !imsi.startsWith(mccmnc)) {
                    imsi = mccmnc + imsi.substring(mccmnc.length());
                    updatePreference(mSimImsiPref, imsi);
                }
                updatePreference(mSimMccMncPref, mccmnc);
            }
        } else if (preference == mSimImsiPref) {
            String imsi = (String) newValue;
            if (TextUtils.isEmpty(imsi)) {
                updatePreference(mSimMccMncPref, "");
                updatePreference(mSimImsiPref, "");
                return false;
            } else {
                String mccmnc = mSimMccMncPref.getText();
                if (!TextUtils.isEmpty(mccmnc) && !imsi.startsWith(mccmnc)) {
                    mccmnc = imsi.substring(0, mccmnc.length());
                    updatePreference(mSimMccMncPref, mccmnc);
                }
                updatePreference(mSimImsiPref, imsi);
            }
        } else {
            updatePreference(preference, (String) newValue);
        }

        return true;
    }

    private void initPreferences() {
        Log.i(TAG, "initPreferences");
        addPreferencesFromResource(R.xml.sim_preference);

        mSimPref = (ListPreference) findPreference(SIM_KEY);
        mSimMccMncPref = (EditTextPreference) findPreference(SIM_MCC_MNC_KEY);
        mSimImsiPref = (EditTextPreference) findPreference(SIM_IMSI_KEY);
        mSimIccidPref = (EditTextPreference) findPreference(SIM_ICCID_KEY);
        mSimGid1Pref = (EditTextPreference) findPreference(SIM_GID1_KEY);
        mSimGid2Pref = (EditTextPreference) findPreference(SIM_GID2_KEY);
        mSimPnnPref = (EditTextPreference) findPreference(SIM_PNN_KEY);
        mSimSpnPref = (EditTextPreference) findPreference(SIM_SPN_KEY);
        mSimApnPref = (EditTextPreference) findPreference(SIM_APN_KEY);

        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            getPreferenceScreen().getPreference(i).setOnPreferenceChangeListener(this);
        }

        initPreferenceValues();
    }

    private void initPreferenceValues() {
        Log.i(TAG, "initPreferenceValues for slot" + mCurrentSlotId);

        updatePreference(mSimPref, String.valueOf(mCurrentSlotId));

        Phone phone = PhoneFactory.getPhone(mCurrentSlotId);
        updatePreference(mSimMccMncPref, phone.getOperatorNumeric());
        updatePreference(mSimImsiPref, phone.getSubscriberId());
        updatePreference(mSimIccidPref, phone.getIccSerialNumber());
        updatePreference(mSimGid1Pref, phone.getGroupIdLevel1());
        updatePreference(mSimGid2Pref, phone.getGroupIdLevel2());
        updatePreference(mSimPnnPref, phone.getPlmn());

        updatePreference(mSimSpnPref,
                ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE))
                        .getSimOperatorNameForPhone(mCurrentSlotId));

        updatePreference(mSimApnPref, "");
    }

    private void updatePreference(Preference preference, String value) {
        if (preference == null) return;

        Log.i(TAG, "updatePreference key: " + preference.getKey() + "; value: " + value);
        value = (value == null ? "" : value);
        if (preference instanceof EditTextPreference) {
            EditTextPreference editTextPref = (EditTextPreference) preference;
            editTextPref.setText(value);
            editTextPref.setSummary(value);
        } else if (preference instanceof ListPreference) {
            ListPreference listPref = (ListPreference) preference;
            listPref.setValue(value);
            listPref.setSummary(listPref.getEntry());
        }
    }

    private void overrideSimInfo() {
        Log.i(TAG, "overrideSimInfo");

        setCarrierTestOverride(mSimMccMncPref.getText(), mSimImsiPref.getText(),
                mSimIccidPref.getText(), mSimGid1Pref.getText(), mSimGid2Pref.getText(),
                mSimPnnPref.getText(), mSimSpnPref.getText(), mSimApnPref.getText());
    }

    private void resetSim() {
        Log.i(TAG, "resetSim");

        setCarrierTestOverride(null, null, null, null, null, null, null, null);

        // setCarrierTestOverride(null, ...) will set mccmnc and spn as null.
        // So need to reset them to sim's real mccmnc and spn.
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tm.setSimOperatorNumericForPhone(mCurrentSlotId,
                PhoneFactory.getPhone(mCurrentSlotId).getOperatorNumeric());
        tm.setSimOperatorNameForPhone(mCurrentSlotId, tm.getSimOperatorName());

        initPreferenceValues();
    }

    private void setCarrierTestOverride(String mccmnc, String imsi, String iccid,
            String gid1, String gid2, String pnn, String spn, String apn) {
        Phone phone = PhoneFactory.getPhone(mCurrentSlotId);
        if (phone != null) {
            phone.setCarrierTestOverride(mccmnc, imsi, iccid, gid1, gid2, pnn, spn, "", apn);
        }

        updateCarrierConfig();
    }

    private void updateCarrierConfig() {
        int[] subIds = ((SubscriptionManager) getSystemService(
                Context.TELEPHONY_SUBSCRIPTION_SERVICE)).getSubscriptionIds(mCurrentSlotId);
        if (subIds != null && subIds.length >= 1) {
            Log.i(TAG, "updateCarrierConfig subId: " + subIds[0]);
            ((CarrierConfigManager) getSystemService(Context.CARRIER_CONFIG_SERVICE))
                    .notifyConfigChangedForSubId(subIds[0]);
        }
    }
}
