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

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ep7_1 extends AppCompatActivity implements Runnable {
    int i; // 循环变量
    private Thread thread; // 声明线程对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        Button button1 = new Button(this);
        button1.setText("开启线程");
        Button button2 = new Button(this);
        button2.setText("中断线程");
        linearLayout.addView(button1);
        linearLayout.addView(button2);

        button1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        i = 0;
                        thread = new Thread(ep7_1.this); // 创建一个线程
                        thread.start(); // 开启线程
                    }
                });

        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (thread != null) {
                            thread.interrupt(); // 中断线程
                            thread = null;
                        }
                        Log.i("提示：", "中断线程");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
        super.onDestroy();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            i++;
            Log.i("循环变量：", String.valueOf(i));
        }
    }
}
