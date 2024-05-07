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

package com.wintmain.xtelephony;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.telephony.AccessNetworkConstants;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.wintmain.R;

public class NetworkDebugActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    private static final String TAG = "NetworkDebugActivity";

    private static final boolean VOICE_DATE_MERGED =
            SystemProperties.getBoolean("persist.debug.merge_voice_data", true);

    private static final String NETWORK_MCC_MNC_KEY = "mcc_mnc";
    private static final String NETWORK_NAME_KEY = "network_name";
    private static final String VOICE_RAT_KEY = "voice_rat";
    private static final String VOICE_STATE_KEY = "voice_reg_state";
    private static final String VOICE_ROAMING_KEY = "voice_roaming_type";
    private static final String DATA_RAT_KEY = "data_rat";
    private static final String DATA_STATE_KEY = "data_reg_state";
    private static final String DATA_ROAMING_KEY = "data_roaming_type";
    private static final String NR_STATE_KEY = "nr_state";
    private static final String NR_FREQUENCY_KEY = "nr_frequency_range";
    private static final String OPERATOR_KEY = "operator";
    private static final int MSG_RESET_NETWORK_DONE = 1;
    private EditTextPreference mNetworkMccMnc;
    private EditTextPreference mNetworkName;
    private ListPreference mVoiceRat;
    private ListPreference mVoiceState;
    private ListPreference mVoiceRoamingType;
    private ListPreference mDataRat;
    private ListPreference mDataState;
    private ListPreference mDataRoamingType;
    private ListPreference mNrState;
    private ListPreference mNrFrequency;
    private final Handler mHandler =
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case MSG_RESET_NETWORK_DONE:
                            initPreferenceValues();
                            break;
                        default:
                            break;
                    }
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Log.i(TAG, "onCreate");
        setContentView(R.layout.network_debug_activity);

        Button setButton = findViewById(R.id.set_network);
        if (setButton != null) {
            setButton.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            setOverrideNetworkInfo();
                        }
                    });
        }

        Button resetButton = findViewById(R.id.reset);
        if (resetButton != null) {
            resetButton.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View v) {
                            resetNetwork();
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

        if (preference == null || newValue == null) {
            return false;
        }

        if (preference == mNetworkMccMnc) {
            String mccmnc = (String) newValue;
            if (TextUtils.isEmpty(mccmnc) || mccmnc.length() > 6 || mccmnc.length() < 5) {
                updatePreference(mNetworkMccMnc, "");
                return false;
            }
        }

        updatePreference(preference, (String) newValue);

        return true;
    }

    private void updatePreference(Preference preference, String value) {
        if (preference == null || value == null) {
            return;
        }

        Log.i(TAG, "updatePreference key: " + preference.getKey() + "; value: " + value);

        if (preference instanceof EditTextPreference) {
            EditTextPreference pref = (EditTextPreference) preference;
            pref.setText(value);
            pref.setSummary(value);
        } else if (preference instanceof ListPreference) {
            ListPreference pref = (ListPreference) preference;
            pref.setValue(value);
            pref.setSummary(pref.getEntry());
        }
    }

    private void initPreferences() {
        Log.i(TAG, "initPreferences");
        addPreferencesFromResource(R.xml.network_preference);

        mNetworkMccMnc = (EditTextPreference) findPreference(NETWORK_MCC_MNC_KEY);
        mNetworkName = (EditTextPreference) findPreference(NETWORK_NAME_KEY);

        mVoiceRat = (ListPreference) findPreference(VOICE_RAT_KEY);
        mVoiceState = (ListPreference) findPreference(VOICE_STATE_KEY);
        mVoiceRoamingType = (ListPreference) findPreference(VOICE_ROAMING_KEY);

        mDataRat = (ListPreference) findPreference(DATA_RAT_KEY);
        mDataState = (ListPreference) findPreference(DATA_STATE_KEY);
        mDataRoamingType = (ListPreference) findPreference(DATA_ROAMING_KEY);

        if (VOICE_DATE_MERGED) {
            // Change title of voice preference from "Voice xx" to "Network xx"
            mVoiceRat.setTitle(R.string.network_rat);
            mVoiceRat.setDialogTitle(R.string.network_rat);
            mVoiceState.setTitle(R.string.network_state);
            mVoiceState.setDialogTitle(R.string.network_state);
            mVoiceRoamingType.setTitle(R.string.network_roaming_type);
            mVoiceRoamingType.setDialogTitle(R.string.network_roaming_type);

            // Remove data preferences
            getPreferenceScreen().removePreference(mDataRat);
            getPreferenceScreen().removePreference(mDataState);
            getPreferenceScreen().removePreference(mDataRoamingType);
            mDataRat = null;
            mDataState = null;
            mDataRoamingType = null;
        }

        mNrState = (ListPreference) findPreference(NR_STATE_KEY);
        mNrFrequency = (ListPreference) findPreference(NR_FREQUENCY_KEY);

        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            getPreferenceScreen().getPreference(i).setOnPreferenceChangeListener(this);
        }

        initPreferenceValues();
    }

    private void initPreferenceValues() {
        final TelephonyManager telMgr =
                (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String plmn = telMgr.getNetworkOperator();
        Log.i(TAG, "initPreferenceValues plmn: " + plmn);
        updatePreference(mNetworkMccMnc, plmn);

        String operator = telMgr.getNetworkOperatorName();
        Log.i(TAG, "initPreferenceValues operator: " + operator);
        updatePreference(mNetworkName, operator);

        ServiceState ss = telMgr.getServiceState();
        if (ss != null) {
            int voiceRat = ss.getRilVoiceRadioTechnology();
            if (voiceRat == ServiceState.RIL_RADIO_TECHNOLOGY_LTE
                    && ss.isUsingCarrierAggregation()) {
                voiceRat = ServiceState.RIL_RADIO_TECHNOLOGY_LTE_CA;
            }
            Log.i(TAG, "initPreferenceValues voiceRat: " + voiceRat);
            updatePreference(mVoiceRat, String.valueOf(voiceRat));

            int voiceState = ss.getState();
            Log.i(TAG, "initPreferenceValues voiceState: " + voiceState);
            updatePreference(mVoiceState, String.valueOf(voiceState));

            int voiceRoamingType = ss.getVoiceRoamingType();
            Log.i(TAG, "initPreferenceValues voiceRoamingType: " + voiceRoamingType);
            updatePreference(mVoiceRoamingType, String.valueOf(voiceRoamingType));

            if (!VOICE_DATE_MERGED) {
                int dataRat = ServiceState.RIL_RADIO_TECHNOLOGY_UNKNOWN;
                NetworkRegistrationInfo nri =
                        ss.getNetworkRegistrationInfo(
                                NetworkRegistrationInfo.DOMAIN_PS,
                                AccessNetworkConstants.TRANSPORT_TYPE_WWAN);
                if (nri != null) {
                    dataRat = nri.getAccessNetworkTechnology();
                }

                if (dataRat == ServiceState.RIL_RADIO_TECHNOLOGY_LTE
                        && ss.isUsingCarrierAggregation()) {
                    dataRat = ServiceState.RIL_RADIO_TECHNOLOGY_LTE_CA;
                }
                Log.i(TAG, "initPreferenceValues dataRat: " + dataRat);
                updatePreference(mDataRat, String.valueOf(dataRat));

                int dataState = ss.getDataRegistrationState();
                Log.i(TAG, "initPreferenceValues dataState: " + dataState);
                updatePreference(mDataState, String.valueOf(dataState));

                int dataRoamingType = ss.getDataRoamingType();
                Log.i(TAG, "initPreferenceValues dataRoamingType: " + dataRoamingType);
                updatePreference(mDataRoamingType, String.valueOf(dataRoamingType));
            }

            int nrState = ss.getNrState();
            Log.i(TAG, "initPreferenceValues nrState: " + nrState);
            updatePreference(mNrState, String.valueOf(nrState));

            int nrFrequency = ss.getNrFrequencyRange();
            Log.i(TAG, "initPreferenceValues nrFrequency: " + nrFrequency);
            updatePreference(mNrFrequency, String.valueOf(nrFrequency));
        }
    }

    private void setOverrideNetworkInfo() {
        Log.i(TAG, "setOverrideNetworkInfo");

        Intent intent = new Intent("com.android.internal.telephony.TestServiceState");

        setIntentExtra(intent, OPERATOR_KEY, null);
        setIntentExtra(intent, VOICE_RAT_KEY, mVoiceRat);
        setIntentExtra(intent, VOICE_STATE_KEY, mVoiceState);
        setIntentExtra(intent, VOICE_ROAMING_KEY, mVoiceRoamingType);

        // If voice and data setting merged, set voice info to data extra
        setIntentExtra(intent, DATA_RAT_KEY, VOICE_DATE_MERGED ? mVoiceRat : mDataRat);
        setIntentExtra(intent, DATA_STATE_KEY, VOICE_DATE_MERGED ? mVoiceState : mDataState);
        setIntentExtra(
                intent, DATA_ROAMING_KEY, VOICE_DATE_MERGED ? mVoiceRoamingType : mDataRoamingType);

        setIntentExtra(intent, NR_STATE_KEY, mNrState);
        setIntentExtra(intent, NR_FREQUENCY_KEY, mNrFrequency);

        sendBroadcast(intent);
    }

    private void setIntentExtra(Intent intent, String key, ListPreference pref) {
        if (OPERATOR_KEY.equals(key)) {
            if (mNetworkName != null && mNetworkMccMnc != null) {
                String name = mNetworkName.getText();
                String mccMnc = mNetworkMccMnc.getText();
                if (!TextUtils.isEmpty(mccMnc)) {
                    String operatorInfo = name + "," + name + "," + mccMnc;
                    Log.i(TAG, "operatorInfo: " + operatorInfo);
                    intent.putExtra(key, operatorInfo);
                }
            }
        } else {
            int value = -1;
            if (pref != null) {
                try {
                    value = Integer.parseInt(pref.getValue());
                    Log.i(TAG, "getPreference key: " + pref.getKey() + "; value: " + value);
                } catch (NumberFormatException e) {
                }
            }

            if (value != -1) {
                intent.putExtra(key, value);
            }
        }
    }

    private void resetNetwork() {
        Log.i(TAG, "resetNetwork");

        Intent intent = new Intent("com.android.internal.telephony.TestServiceState");
        intent.putExtra("action", "reset");
        sendBroadcast(intent);

        mHandler.sendEmptyMessageDelayed(MSG_RESET_NETWORK_DONE, 600);
    }
}
