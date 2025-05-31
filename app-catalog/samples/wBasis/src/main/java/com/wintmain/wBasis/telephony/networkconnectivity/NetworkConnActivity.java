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

package com.wintmain.wBasis.telephony.networkconnectivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.catalog.framework.annotations.Sample;
import com.wintmain.wBasis.R;

/**
 * @Description Sample Activity demonstrating how to connect to the network and fetch raw HTML. It
 * uses a Fragment that encapsulates the network operations on an AsyncTask. This sample uses a
 * TextView to display output.
 * @Author wintmain
 * @mailto wosintmain@gmail.com
 * @Date 2023-08-17 21:06:51
 */
@Sample(name = "NetworkConnectivity", description = "网络连接例子",
        tags = {"android-samples", "telephony"})
public class NetworkConnActivity extends AppCompatActivity implements DownloadCallback {
    private static final String TAG = NetworkConnActivity.class.getSimpleName();

    // Reference to the TextView showing fetched data, so we can clear it with a button as
    // necessary.
    private TextView mDataText;

    // Keep a reference to the NetworkFragment which owns the AsyncTask object
    // that is used to execute network ops.
    private NetworkFragment mNetworkFragment;

    // Boolean telling us whether a download is in progress, so we don't trigger overlapping
    // downloads with consecutive button clicks.
    private boolean mDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conn_main);
        mDataText = findViewById(R.id.data_text);
        // 这里的网站换成国内的，便于访问
        mNetworkFragment =
                NetworkFragment.getInstance(getSupportFragmentManager(), "https://www.baidu.com");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 使用getMenuInflater()，无法显示菜单Menu选项，需要将extends变成AppCompatActivity
        // 详情见：https://blog.csdn.net/weixin_42664622/article/details/107426259
        getMenuInflater().inflate(R.menu.connmain, menu);
        Log.d(TAG, "inflate menu success");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // "case R.id.xxx"会报not const的错误，可以把这个写法换成if-else的
        // 详情见：http://tools.android.com/tips/non-constant-fields
        int id = item.getItemId();
        if (id == R.id.fetch_action) {
            // When the user clicks FETCH, fetch the first 500 characters of raw HTML
            startDownload();
            return true;
        } else if (id == R.id.clear_action) {
            // Clear the text and cancel download.
            finishDownloading();
            mDataText.setText("");
            return true;
        }
        return false;
    }

    private void startDownload() {
        if (!mDownloading && mNetworkFragment != null) {
            // Execute the async download.
            mNetworkFragment.startDownload();
            mDownloading = true;
        }
    }

    @Override
    public void updateFromDownload(String result) {
        if (result != null) {
            mDataText.setText(result);
        } else {
            mDataText.setText(getString(R.string.connection_error));
        }
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    @Override
    public void finishDownloading() {
        mDownloading = false;
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch (progressCode) {
            // TODO: You can add UI behavior for progress updates here.
            case Progress.ERROR:
            case Progress.CONNECT_SUCCESS:
            case Progress.GET_INPUT_STREAM_SUCCESS:
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                mDataText.setText(percentComplete + "%");
                break;
        }
    }
}
