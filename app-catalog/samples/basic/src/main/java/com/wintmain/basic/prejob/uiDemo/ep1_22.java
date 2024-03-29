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

package com.wintmain.basic.prejob.uiDemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.Nullable;

import com.wintmain.basic.R;

public class ep1_22 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep1_22);
        Chronometer chronometer = findViewById(R.id.chro);
        //        设置起始时间
        chronometer.setBase(SystemClock.elapsedRealtime());
        //        设置格式
        chronometer.setFormat("已用时间:%s");
        Button start = findViewById(R.id.startbutton);
        Button end = findViewById(R.id.endbutton);
        Button for0 = findViewById(R.id.for0);
        start.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chronometer.start();
                    }
                });
        end.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chronometer.stop();
                    }
                });
        for0.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chronometer.setBase(SystemClock.elapsedRealtime());
                    }
                });
    }
}
