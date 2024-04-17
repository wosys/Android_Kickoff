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

import static android.net.NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED;
import static android.net.NetworkCapabilities.TRANSPORT_CELLULAR;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.wintmain.R;

public class ConnectivityDebugActivity extends Activity {

    private static final String TAG = "ConnectivityDebugActivity";
    private static final String[] CONNECTIVITY_CAPABILITY_TABLES = {
            "Network Internet",
            "Network MMS",
            "Network DUN",
            "Network IMS",
            "Network SUPL",
            "Network FOTA",
            "Network CBS",
            "Network XCAP",
            "Network IA",
            "Network EIMS",
            "Network Enterprise"
    };
    private static final int[] CONNECTIVITY_CAPABILITIES = {
            NetworkCapabilities.NET_CAPABILITY_INTERNET,
            NetworkCapabilities.NET_CAPABILITY_MMS,
            NetworkCapabilities.NET_CAPABILITY_DUN,
            NetworkCapabilities.NET_CAPABILITY_IMS,
            NetworkCapabilities.NET_CAPABILITY_SUPL,
            NetworkCapabilities.NET_CAPABILITY_FOTA,
            NetworkCapabilities.NET_CAPABILITY_CBS,
            NetworkCapabilities.NET_CAPABILITY_XCAP,
            NetworkCapabilities.NET_CAPABILITY_IA,
            NetworkCapabilities.NET_CAPABILITY_EIMS,
            NetworkCapabilities.NET_CAPABILITY_ENTERPRISE
    };

    private Button mRequestNetworkButton;
    private Button mReleaseNetworkButton;
    private Spinner mConnectivityCapabilities;
    private int mConnectivityCapabilityIndex = 0;
    private ConnectivityManager mCM;
    private NetworkRequest mNetworkRequest;
    private ConnectivityManager.NetworkCallback mNetworkCallback = new NetworkRequestCallback();

    AdapterView.OnItemSelectedListener mConnectivityCapabilityHandler =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(TAG, "onItemSelected position: " + position
                            + "; mConnectivityCapabilityIndex: " + mConnectivityCapabilityIndex);
                    if (position >= 0 && position < CONNECTIVITY_CAPABILITY_TABLES.length) {
                        mConnectivityCapabilityIndex = position;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO
                }
            };

    View.OnClickListener mRequestNetworkHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mNetworkRequest = new NetworkRequest.Builder()
                    .addTransportType(TRANSPORT_CELLULAR)
                    .addCapability(CONNECTIVITY_CAPABILITIES[mConnectivityCapabilityIndex])
                    .addCapability(NET_CAPABILITY_NOT_RESTRICTED)
                    .build();
            Log.i(TAG, "mNetworkRequest: " + mNetworkRequest);
            if (mCM != null) {
                mCM.requestNetwork(mNetworkRequest, mNetworkCallback);
            }
        }
    };

    View.OnClickListener mReleaseNetworkHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCM != null && mNetworkRequest != null) {
                try {
                    mCM.unregisterNetworkCallback(mNetworkCallback);
                } catch (IllegalArgumentException e) {
                    Log.e(TAG, "unregisterNetworkCallback exception");
                }
            }
        }
    };

    private class NetworkRequestCallback extends ConnectivityManager.NetworkCallback {
        @Override
        public void onLost(Network network) {
            super.onLost(network);
            Log.i(TAG, "onLost mConnectivityCapabilityIndex: " + mConnectivityCapabilityIndex);
            Toast.makeText(getApplicationContext(),
                    CONNECTIVITY_CAPABILITY_TABLES[mConnectivityCapabilityIndex] + " lost",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUnavailable() {
            super.onUnavailable();
            Log.i(TAG, "onUnavailable mConnectivityCapabilityIndex: " + mConnectivityCapabilityIndex);
            Toast.makeText(getApplicationContext(),
                    CONNECTIVITY_CAPABILITY_TABLES[mConnectivityCapabilityIndex] + " unavailable",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAvailable(Network network) {
            super.onAvailable(network);
            Log.i(TAG, "onAvailable mConnectivityCapabilityIndex: " + mConnectivityCapabilityIndex);
            Toast.makeText(getApplicationContext(),
                    CONNECTIVITY_CAPABILITY_TABLES[mConnectivityCapabilityIndex] + " available",
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.connectivity_debug_activity);

        Log.i(TAG, "onCreate");
        mCM = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        mRequestNetworkButton = (Button) findViewById(R.id.request_network);
        mReleaseNetworkButton = (Button) findViewById(R.id.release_network);
        mRequestNetworkButton.setOnClickListener(mRequestNetworkHandler);
        mReleaseNetworkButton.setOnClickListener(mReleaseNetworkHandler);

        mConnectivityCapabilities = (Spinner) findViewById(R.id.connectivityCapability);
        ArrayAdapter<String> mConnectivityCapabilitiesAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                CONNECTIVITY_CAPABILITY_TABLES);
        mConnectivityCapabilitiesAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mConnectivityCapabilities.setAdapter(mConnectivityCapabilitiesAdapter);
        mConnectivityCapabilities.setOnItemSelectedListener(mConnectivityCapabilityHandler);
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
}
