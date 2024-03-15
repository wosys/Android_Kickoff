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
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

public class ep7_3 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LooperThread thread = new LooperThread();
        thread.start();
    }

    class LooperThread extends Thread {
        public Handler handler1; // 声明一个对象

        @Override
        public void run() {
            super.run();
            Looper.prepare(); // 初始化Looper对象
            handler1 =
                    new Handler() {
                        public void handleMessage(Message msg) {
                            Log.i("Wintmain_Looper", String.valueOf(msg.what));
                        }
                    };
            Message m = handler1.obtainMessage(); // 获取一个消息
            m.what = 0x11; // 设置Message的what属性的值
            handler1.sendMessage(m); // 发送消息
            Looper.loop(); // 启动looper
        }
    }
}
