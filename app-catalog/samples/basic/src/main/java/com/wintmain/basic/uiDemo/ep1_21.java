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

package com.wintmain.basic.uiDemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.wintmain.basic.R;

import java.util.Calendar;

public class ep1_21 extends Activity {

    int year;
    int month;
    int day;
    int hour;
    int minute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep1_21);
        DatePicker datePicker = findViewById(R.id.date);
        TimePicker timePicker = findViewById(R.id.time);
        timePicker.setIs24HourView(true);
        Calendar instance = Calendar.getInstance();
        year = instance.get(Calendar.YEAR);
        month = instance.get(Calendar.MONTH);
        day = instance.get(Calendar.DAY_OF_MONTH);
        hour = instance.get(Calendar.HOUR_OF_DAY);
        minute = instance.get(Calendar.MINUTE);
        datePicker.init(
                year,
                month,
                day,
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int month, int day) {
                        ep1_21.this.year = year;
                        ep1_21.this.month = month;
                        ep1_21.this.day = day;
                        show(year, month, day, hour, minute); // 通过消息框显示日期和时间
                    }
                });
        timePicker.setOnTimeChangedListener(
                new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hour, int minute) {
                        ep1_21.this.hour = hour;
                        ep1_21.this.month = minute;
                        Log.i("time", "时间：" + hour + minute);
                    }
                });
    }

    private void show(int year, int month, int day, int hour, int minute) {
        String str =
                year + "年" + (month + 1) + "月" + day + "日" + hour + ":" + minute; // 获取拾取器设置的日期和时间
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show(); // 显示消息提示框
    }
}
