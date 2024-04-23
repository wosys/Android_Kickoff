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
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import com.wintmain.basic.R;

public class ep9_3 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep9_3);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent();
                        intent1.setAction("com.android.MY_BROADCAST");
                        intent1.setComponent(
                                new ComponentName(
                                        "com.wintmain.up_demo",
                                        "com.wintmain.up_demo.MyBroadcastReceiver"));
                        sendBroadcast(intent1);
                    }
                });
        button1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent2 = new Intent();
                        intent2.setAction("com.android.brocasttest.MY_BROADCAST");
                        intent2.setComponent(
                                new ComponentName(
                                        "com.wintmain.up_demo",
                                        "com.wintmain.up_demo.AnotherBroadcastReceiver"));
                        sendBroadcast(intent2);
                    }
                });
    }
}
