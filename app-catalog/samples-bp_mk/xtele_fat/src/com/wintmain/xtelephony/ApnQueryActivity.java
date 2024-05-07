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

import static android.provider.Telephony.Carriers.APN;
import static android.provider.Telephony.Carriers.AUTH_TYPE;
import static android.provider.Telephony.Carriers.CARRIER_ID;
import static android.provider.Telephony.Carriers.MMSC;
import static android.provider.Telephony.Carriers.MMSPORT;
import static android.provider.Telephony.Carriers.MMSPROXY;
import static android.provider.Telephony.Carriers.MVNO_MATCH_DATA;
import static android.provider.Telephony.Carriers.MVNO_TYPE;
import static android.provider.Telephony.Carriers.NAME;
import static android.provider.Telephony.Carriers.NUMERIC;
import static android.provider.Telephony.Carriers.PASSWORD;
import static android.provider.Telephony.Carriers.PORT;
import static android.provider.Telephony.Carriers.PROTOCOL;
import static android.provider.Telephony.Carriers.PROXY;
import static android.provider.Telephony.Carriers.ROAMING_PROTOCOL;
import static android.provider.Telephony.Carriers.SERVER;
import static android.provider.Telephony.Carriers.TYPE;
import static android.provider.Telephony.Carriers.USER;
import static android.provider.Telephony.Carriers.USER_EDITABLE;
import static android.provider.Telephony.Carriers.USER_VISIBLE;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.wintmain.R;

import java.util.ArrayList;
import java.util.List;

/*
 * This class is used to query apn by mcc/mnc or carrier id from apn db.
 */
public class ApnQueryActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    private static final String TAG = "ApnQueryActivity";

    private static final String MCC_MNC_KEY = "mcc_mnc";
    private static final String MVNO_TYPE_KEY = "mvno_type";
    private static final String MVNO_VALUE_KEY = "mvno_value";
    private static final String CARRIER_ID_KEY = "carrier_id";

    private static final int AUTH_UNKNOWN = -1;
    private static final int AUTH_NONE = 0;
    private static final int AUTH_PAP = 1;
    private static final int AUTH_CHAP = 2;
    private static final int AUTH_PAP_OR_CHAP = 3;

    private static String sEmpty = "";

    private EditTextPreference mMccMnc;
    private EditTextPreference mMvnoValue;
    private EditTextPreference mCarrierId;
    private ListPreference mMvnoType;
    private TextView mApnList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate");
        setContentView(R.layout.apn_query_activity);
        addPreferencesFromResource(R.xml.apn_preference);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Button queryButton = findViewById(R.id.query_apn);
        if (queryButton != null) {
            queryButton.setOnClickListener(v -> queryApn());
        }
        Button clearButton = findViewById(R.id.clear);
        if (clearButton != null) {
            clearButton.setOnClickListener(v -> initPreferenceValues());
        }
        mApnList = findViewById(R.id.apn_list);

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

        setPreferenceValue(preference, (String) newValue);

        return true;
    }

    private void initPreferences() {
        Log.i(TAG, "initPreferences");

        sEmpty = getResources().getString(R.string.apn_empty);

        mMccMnc = (EditTextPreference) findPreference(MCC_MNC_KEY);
        mMvnoValue = (EditTextPreference) findPreference(MVNO_VALUE_KEY);
        mCarrierId = (EditTextPreference) findPreference(CARRIER_ID_KEY);
        mMvnoType = (ListPreference) findPreference(MVNO_TYPE_KEY);

        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            getPreferenceScreen().getPreference(i).setOnPreferenceChangeListener(this);
        }
        initPreferenceValues();
    }

    private void initPreferenceValues() {
        final TelephonyManager telMgr =
                (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String plmn = telMgr.getSimOperator();
        // Set default SIM's mcc/mnc to mcc/mnc EditText
        setPreferenceValue(mMccMnc, TextUtils.isEmpty(plmn) ? sEmpty : plmn);

        setPreferenceValue(mMvnoValue, sEmpty);
        setPreferenceValue(mCarrierId, sEmpty);
        setPreferenceValue(mMvnoType, sEmpty);

        if (mApnList != null) mApnList.setText(sEmpty);
    }

    private void setPreferenceValue(Preference preference, String value) {
        if (preference == null || value == null) return;

        if (preference instanceof EditTextPreference editTextPref) {
            editTextPref.setText(value);
            editTextPref.setSummary(value);
        } else if (preference instanceof ListPreference listPref) {
            listPref.setValue(value);
            listPref.setSummary(listPref.getEntry());
        }
    }

    private boolean isValidValue(String value) {
        return !TextUtils.isEmpty(value) && !value.equals(sEmpty);
    }

    private void queryApn() {
        String selection = getSelection();
        Log.i(TAG, "queryApn selection: " + selection);

        List<String> apnList = new ArrayList<String>();
        if (!TextUtils.isEmpty(selection)) {
            final Cursor cursor = getContentResolver().query(Telephony.Carriers.CONTENT_URI,
                    null, selection, null, Telephony.Carriers.DEFAULT_SORT_ORDER);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    apnList.add(buildApn(cursor));
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }

        if (mApnList != null) {
            mApnList.setMovementMethod(new ScrollingMovementMethod());
            mApnList.setText(apnList.isEmpty() ? sEmpty : String.join("\n\n", apnList));
        }
    }

    private String getSelection() {
        String mccmnc = mMccMnc != null ? mMccMnc.getText() : sEmpty;
        String carrierId = mCarrierId != null ? mCarrierId.getText() : sEmpty;
        Log.i(TAG, "getSelection mccmnc: " + mccmnc + "; carrierId: " + carrierId);

        // If mcc/mnc and carrier id are both invalid. Can't query apn
        if (!isValidValue(mccmnc) && !isValidValue(carrierId)) return null;

        if (isValidValue(mccmnc)) {
            String selection = NUMERIC + " = '" + mccmnc + "'";

            String mvnoType = mMvnoType != null ? mMvnoType.getValue() : sEmpty;
            String mvnoValue = mMvnoValue != null ? mMvnoValue.getText() : sEmpty;
            Log.i(TAG, "mvnoType: " + mvnoType + "; mvnoValue: " + mvnoValue);
            if (isValidValue(mvnoType) && isValidValue(mvnoValue)) {
                selection += " AND (" + MVNO_TYPE + " = '" + mvnoType + "' AND "
                        + MVNO_MATCH_DATA + " = '" + mvnoValue + "')";
            }

            if (isValidValue(carrierId)) {
                selection += " AND " + CARRIER_ID + " = '" + carrierId + "'";
            }
            Log.i(TAG, "getSelection: " + selection);
            return selection;
        } else if (isValidValue(carrierId)) {
            return CARRIER_ID + " = '" + carrierId + "'";
        }

        return null;
    }

    private String buildApn(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(NAME));
        String apn = cursor.getString(cursor.getColumnIndex(APN));
        String mccmnc = cursor.getString(cursor.getColumnIndex(NUMERIC));
        String type = cursor.getString(cursor.getColumnIndex(TYPE));
        String protocol = cursor.getString(cursor.getColumnIndex(PROTOCOL));
        String roamingProtocol = cursor.getString(cursor.getColumnIndex(ROAMING_PROTOCOL));
        String mvnoType = cursor.getString(cursor.getColumnIndex(MVNO_TYPE));
        String mvnoData = cursor.getString(cursor.getColumnIndex(MVNO_MATCH_DATA));
        String proxy = cursor.getString(cursor.getColumnIndex(PROXY));
        String port = cursor.getString(cursor.getColumnIndex(PORT));
        String userName = cursor.getString(cursor.getColumnIndex(USER));
        String password = cursor.getString(cursor.getColumnIndex(PASSWORD));
        String server = cursor.getString(cursor.getColumnIndex(SERVER));
        String mmsc = cursor.getString(cursor.getColumnIndex(MMSC));
        String mmsProxy = cursor.getString(cursor.getColumnIndex(MMSPROXY));
        String mmsPort = cursor.getString(cursor.getColumnIndex(MMSPORT));
        String authType = getAuthType(cursor.getInt(cursor.getColumnIndex(AUTH_TYPE)));
        boolean visible = cursor.getInt(cursor.getColumnIndex(USER_VISIBLE)) != 0;
        boolean editable = cursor.getInt(cursor.getColumnIndex(USER_EDITABLE)) != 0;

        String apnDetails = "Name: " + name + " |APN: " + apn + " |MccMnc: " + mccmnc
                + " |Type: " + type + " |Protocol: " + protocol
                + " |Roaming Protocol: " + roamingProtocol + " |MvnoType: " + mvnoType
                + " |MvnoData: " + mvnoData + " |Proxy: " + proxy + " |Port: " + port
                + " |Username: " + userName + " |Password: " + password + " |Server: " + server
                + " |MMSC: " + mmsc + " |MMS Proxy: " + mmsProxy + " |MMS port: " + mmsPort
                + " |AuthType: " + authType + " |Visible: " + visible + " |Editable: " + editable;
        Log.i(TAG, "buildApn = " + apnDetails);
        return apnDetails;
    }

    private String getAuthType(int auth) {
        String authType = Integer.toString(auth);

        switch (auth) {
            case AUTH_NONE:
                authType += "(None)";
                break;
            case AUTH_PAP:
                authType += "(PAP)";
                break;
            case AUTH_CHAP:
                authType += "(CHAP)";
                break;
            case AUTH_PAP_OR_CHAP:
                authType += "(PAP or CHAP)";
                break;
            case AUTH_UNKNOWN:
            default:
                authType += "(Unknown)";
                break;
        }
        Log.i(TAG, "getAuthType: " + authType);
        return authType;
    }
}
