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

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class ep2_2 extends AppCompatActivity {
    private ProgressBar horizonP; // 水平进度条
    private ProgressBar circleP; // 圆形进度条
    private int mProgressStatus = 0; // 完成进度
    private Handler mHandler; // 声明一个用于处理消息的Handler类的对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example2_2);

        horizonP = (ProgressBar) findViewById(R.id.progressBar1); // 获取进度条
        circleP = (ProgressBar) findViewById(R.id.progressBar2);
        mHandler =
                new Handler() {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        if (msg.what == 0x111) {
                            horizonP.setProgress(mProgressStatus); // 更新进度
                        } else {
                            Toast.makeText(ep2_2.this, "耗时操作已经完成", Toast.LENGTH_SHORT).show();
                            horizonP.setVisibility(View.GONE); // 设置进度条不显示，并且不占用空间
                            circleP.setVisibility(View.GONE);
                        }
                    }
                };

        new Thread(
                        new Runnable() {
                            public void run() {
                                while (true) {
                                    mProgressStatus = doWork(); // 获取耗时操作完成的百分比
                                    Message m = new Message();
                                    if (mProgressStatus < 100) {
                                        m.what = 0x111;
                                        mHandler.sendMessage(m); // 发送信息
                                    } else {
                                        m.what = 0x110;
                                        mHandler.sendMessage(m); // 发送消息
                                        break;
                                    }
                                }
                            }

                            // 模拟一个耗时操作
                            private int doWork() {
                                mProgressStatus += Math.random() * 10; // 改变完成进度
                                try {
                                    Thread.sleep(200); // 线程休眠200毫秒
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return mProgressStatus;
                            }
                        })
                .start(); // 开启一个线程
    }
}
