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

package com.wintmain.basic.upDemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.wintmain.basic.R;

import java.util.Random;

public class ep7_6 extends Activity {
    public static TextView[] tv = new TextView[14]; // TextView数组
    int[] bgcolor =
            new int[] {
                R.color.color1,
                R.color.color2,
                R.color.color3,
                R.color.color4,
                R.color.color5,
                R.color.color6,
                R.color.color7
            };
    private Handler handler; // 创建Handler对象
    private int index = 0; // 当前颜色值

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep7_6);
        // 整体布局
        LinearLayout linearLayout = findViewById(R.id.line1);

        int height = this.getResources().getDisplayMetrics().heightPixels; // 获取屏幕的高度
        for (int i = 0; i < tv.length; i++) {
            tv[i] = new TextView(this); // 创建一个文本框对象
            tv[i].setWidth(this.getResources().getDisplayMetrics().widthPixels); // 设置文本框的宽度
            tv[i].setHeight(height / tv.length); // 设置文本框的高度
            linearLayout.addView(tv[i]); // 将TextView组件添加到线性布局管理器中
        }

        Thread t =
                new Thread(
                        () -> {
                            while (!Thread.currentThread().isInterrupted()) {
                                Message m = handler.obtainMessage(); // 获取一个Message
                                m.what = 0x101; // 设置消息标识
                                handler.sendMessage(m); // 发送消息
                                try {
                                    Thread.sleep(new Random().nextInt(1000)); // 休眠1秒钟
                                } catch (InterruptedException e) {
                                    e.printStackTrace(); // 输出异常信息
                                }
                            }
                        });
        t.start(); // 开启线程

        handler =
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        int temp = 0; // 临时变量
                        if (msg.what == 0x101) {
                            for (int i = 0; i < tv.length; i++) {
                                temp = new Random().nextInt(bgcolor.length); // 产生一个随机数
                                // 去掉重复的并且相邻的颜色
                                if (index == temp) {
                                    temp++;
                                    if (temp == bgcolor.length) {
                                        temp = 0;
                                    }
                                }
                                index = temp;
                                // 为文本框设置背景
                                tv[i].setBackgroundColor(getResources().getColor(bgcolor[index]));
                            }
                        }
                        super.handleMessage(msg);
                    }
                };
    }
}
