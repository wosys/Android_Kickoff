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
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class ep9_4 extends Activity {
    private Receiver1 receiver1;
    private Receiver2 receiver2;
    private Receiver3 receiver3;
    private Receiver4 receiver4;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        Button button = new Button(this);
        button.setText("测试有序广播");
        linearLayout.addView(button);
        setContentView(linearLayout);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction("com.test.broadcasttest.My_TestBroadcast");
                        sendOrderedBroadcast(intent, null);
                    }
                });
        // 在配置清单中静态注册广播接收者，会使接收者无法接收到广播
        receiver1 = new Receiver1();
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("com.test.broadcasttest.My_TestBroadcast");
        intentFilter1.setPriority(88);
        registerReceiver(receiver1, intentFilter1, Context.RECEIVER_NOT_EXPORTED);

        // 优先级比1高，则会先响应
        receiver2 = new Receiver2();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.test.broadcasttest.My_TestBroadcast");
        intentFilter2.setPriority(100);
        registerReceiver(receiver2, intentFilter2, Context.RECEIVER_NOT_EXPORTED);

        // 3接收到后会截断，导致4接收不到
        receiver3 = new Receiver3();
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.test.broadcasttest.My_TestBroadcast");
        registerReceiver(receiver3, intentFilter3, Context.RECEIVER_NOT_EXPORTED);

        receiver4 = new Receiver4();
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("com.test.broadcasttest.My_TestBroadcast");
        registerReceiver(receiver4, intentFilter4, Context.RECEIVER_NOT_EXPORTED);
    }
}
