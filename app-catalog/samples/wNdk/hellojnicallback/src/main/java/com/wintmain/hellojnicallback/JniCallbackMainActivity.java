/*
 * Copyright (C) 2016 The Android Open Source Project
 * Copyright 2023-2024 wintmain
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wintmain.hellojnicallback;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Keep;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.catalog.framework.annotations.Sample;

@Sample(name = "JniCallback",
        description = "JNI执行逻辑在C里面",
        tags = {"android-samples", "jni"})
public class JniCallbackMainActivity extends AppCompatActivity {

    String hour = "00";
    String minute = "00";
    String second = "00";
    TextView tickView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_jnicallback);
        tickView = (TextView) findViewById(R.id.tickView);
    }
    @Override
    public void onResume() {
        super.onResume();
        hour = minute = second = "00";
        ((TextView)findViewById(R.id.hellojniMsg)).setText(stringFromJNI());
        startTicks();
    }

    @Override
    public void onPause () {
        super.onPause();
        StopTicks();
    }

    /*
     * A function calling from JNI to update current timer
     */
    @Keep
    private void updateTimer() {
        int sec = Integer.parseInt(second);
        ++sec;
        second = String.valueOf(sec);
        if (sec < 10) {
            second = "0" + sec;
        }
        if(sec >= 60) {
            int min = Integer.parseInt(minute);
            ++min;
            minute = String.valueOf(min);
            if (min < 10) {
                minute = "0" + min;
            }
            sec -= 60;
            second = String.valueOf(sec);
            if(min >= 60) {
                int h = Integer.parseInt(hour);
                ++h;
                hour = String.valueOf(h);
                if (h < 10) {
                    hour = "0" + h;
                }
                min -= 60;
                minute = String.valueOf(min);
            }
        }
        runOnUiThread(() -> {
            String ticks = JniCallbackMainActivity.this.hour + ":" +
                    JniCallbackMainActivity.this.minute + ":" +
                    JniCallbackMainActivity.this.second;
            JniCallbackMainActivity.this.tickView.setText(ticks);
        });
    }

    static {
        System.loadLibrary("hello-jnicallback");
    }
    public native  String stringFromJNI();
    public native void startTicks();
    public native void StopTicks();
}