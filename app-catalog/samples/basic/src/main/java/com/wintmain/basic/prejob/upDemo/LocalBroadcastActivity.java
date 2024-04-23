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

package com.wintmain.basic.prejob.upDemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class LocalBroadcastActivity extends Activity {
    private LocalBroadcastTest localBroadcastTest;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        Button button = new Button(this);
        button.setText("测试本地广播");
        linearLayout.addView(button);
        // 实例化对象
        localBroadcastManager = LocalBroadcastManager.getInstance(LocalBroadcastActivity.this);
        localBroadcastTest = new LocalBroadcastTest();
        // 发送广播
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction("LOCAL");
                        localBroadcastManager.sendBroadcast(intent);
                    }
                });
        // 动态注册接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("LOCAL");
        localBroadcastManager.registerReceiver(localBroadcastTest, intentFilter);
    }

    class LocalBroadcastTest extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "本地广播接收器已接受", Toast.LENGTH_SHORT).show();
        }
    }
}
