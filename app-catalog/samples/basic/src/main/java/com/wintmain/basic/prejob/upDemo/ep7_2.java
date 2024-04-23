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

/**
 * @Description @Author wintmain <wosintmain@gmail.com> @Date 2022-06-12 22:58:39
 */

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

import java.io.IOException;

public class ep7_2 extends Activity {
    private Thread thread;
    private MediaPlayer mediaplayer;
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        Button button1 = new Button(this);
        button1.setText("点我播放音乐");

        linearLayout.addView(button1);

        button1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        button1.setEnabled(false); // 设置按钮不可用

                        // 创建一个用于播放背景音乐的线程
                        thread =
                                new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    display(); // 播放背景音乐
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                        thread.start(); // 开启线程
                    }
                });
    }

    private void display() throws IOException {
        if (mediaplayer != null) {
            mediaplayer.release(); // 释放资源
        }
        //        本地播放
        //        mediaplayer = MediaPlayer.create(ep7_2.this,R.raw.xxx);

        // 网络链接
        path =
                "http://218.205.239.34/MIGUM2.0/v1.0/content/sub/listenSong"
                        + ".do?toneFlag=LQ&netType=00&copyrightId=0&contentId=600913000000560388"
                        + "&resourceType=2&channel=0";
        mediaplayer = new MediaPlayer();
        mediaplayer.setDataSource(path);
        mediaplayer.prepare();
        mediaplayer.start();

        // 为MediaPlayer添加播放完成事件监听器
        mediaplayer.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        try {
                            Thread.sleep(5000);
                            display(); // 重新播放音乐
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    // 停止播放背景音乐并释放资源
    @Override
    protected void onDestroy() {
        if (mediaplayer != null) {
            mediaplayer.stop(); // 停止播放
            mediaplayer.release(); // 释放资源
            mediaplayer = null;
        }
        if (thread != null) {
            thread = null;
        }
        super.onDestroy();
    }
}
