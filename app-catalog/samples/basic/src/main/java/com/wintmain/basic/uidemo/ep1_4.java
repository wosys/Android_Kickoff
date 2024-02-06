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

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class ep1_4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep1_4);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.mylayout); // 获取帧布局管理器
        final RabbitView rabbit = new RabbitView(ep1_4.this); // 创建并实例化RabbitView类
        // 为小兔子添加触摸事件监听器
        rabbit.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        rabbit.bitmapX = event.getX(); // 设置小兔子显示位置的X坐标
                        rabbit.bitmapY = event.getY(); // 设置小兔子显示位置的Y坐标
                        rabbit.invalidate(); // 重绘rabbit组件
                        return true;
                    }
                });
        frameLayout.addView(rabbit); // 将rabbit添加到布局管理器中
    }
}
