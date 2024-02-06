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

package com.wintmain.basic.uidemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.wintmain.basic.R;

public class ep2_12 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);

        Button button = new Button(this);
        button.setText("生成通知");
        linearLayout.addView(button);

        // 获取通知管理器,用于发送通知
        final NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 自定义通道id属性
        String id = "channel_1";
        // 自定义通道描述属性
        String description = "channel_description";
        int NOTIFY_ID_1 = 0x1;
        int NOTIFY_ID_2 = 0x2;
        // 通知栏管理重要提示消息声音设定
        int importance = NotificationManager.IMPORTANCE_HIGH;

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //                建立通知通道类
                        NotificationChannel channel =
                                new NotificationChannel(id, "123", importance);
                        //                设置描述属性
                        channel.setDescription(description);
                        //                开启闪光灯
                        channel.enableLights(true);
                        //                开启震动
                        channel.enableVibration(true);
                        channel.setVibrationPattern(
                                new long[] {100, 200, 300, 400, 500, 400, 300, 200, 400});
                        //                管理器创建该通知渠道
                        manager.createNotificationChannel(channel);
                        //                创建notification对象
                        Notification builder =
                                new Notification.Builder(ep2_12.this, id)
                                        //                        标题
                                        .setContentTitle("通知标题1")
                                        //                        小图标
                                        .setSmallIcon(R.drawable.img01)
                                        //                        大图标
                                        .setLargeIcon(
                                                BitmapFactory.decodeResource(
                                                        getResources(), R.drawable.img01))
                                        //                        内容
                                        .setContentText("通知栏内容1")
                                        //                        设置自动删除通知
                                        .setAutoCancel(true)
                                        //                        构建
                                        .build();
                        //                保留多行通知,需要唯一编号
                        manager.notify(NOTIFY_ID_1, builder);

                        Intent intent = new Intent(ep2_12.this, NotificationActivity.class);
                        PendingIntent pendingIntent =
                                PendingIntent.getActivity(ep2_12.this, 0, intent, 0);
                        Notification builder2 =
                                new Notification.Builder(ep2_12.this, id)
                                        .setContentTitle("通知标题2") // 设置通知标题
                                        .setSmallIcon(R.drawable.img01)
                                        .setLargeIcon(
                                                BitmapFactory.decodeResource(
                                                        getResources(), R.drawable.img01))
                                        .setContentText("通知栏内容2")
                                        .setAutoCancel(true) // 设置自动删除图标
                                        .setContentIntent(pendingIntent)
                                        .build(); // 运行
                        manager.notify(NOTIFY_ID_2, builder2);
                    }
                });

        Button button2 = new Button(this);
        button2.setText("清除通知");
        linearLayout.addView(button2);
        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        manager.cancel(NOTIFY_ID_1); // 清除ID号为常量。。的通知
                        manager.cancelAll(); // 清除全部通知
                    }
                });
    }
}
