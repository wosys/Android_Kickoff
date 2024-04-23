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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.wintmain.basic.R;

import static android.graphics.BitmapFactory.decodeResource;
import static android.widget.Toast.LENGTH_SHORT;

public class EpOne_4 extends Activity {
    // AppCompatActivity -> 会有title bar，还有其他的一些

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep1_4);

        FrameLayout frameLayout = findViewById(R.id.mylayout); // 获取帧布局管理器
        final RabbitView rabbit = new RabbitView(EpOne_4.this); // 创建并实例化RabbitView类
        // 为小兔子添加触摸事件监听器
        rabbit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rabbit.bitmapX = event.getX(); // 设置小兔子显示位置的X坐标
                rabbit.bitmapY = event.getY(); // 设置小兔子显示位置的Y坐标
                rabbit.invalidate(); // 重绘rabbit组件
                Toast.makeText(getBaseContext(), "You touch this rabbit.", LENGTH_SHORT).show();
                return true;
            }
        });
        frameLayout.addView(rabbit); // 将rabbit添加到布局管理器中
    }
}

class RabbitView extends View {
    public float bitmapX; // 小兔子显示位置的X坐标
    public float bitmapY; // 小兔子显示位置的Y坐标

    public RabbitView(Context context) { // 重写构造方法
        super(context);
        bitmapX = 0;
        bitmapY = 0;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(); // 创建并实例化Paint的对象
        Bitmap bitmap = decodeResource(this.getResources(), R.drawable.img06); // 根据图片生成位图对象
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint); // 绘制小兔子
        if (bitmap.isRecycled()) { // 判断图片是否回收
            bitmap.recycle(); // 强制回收图片
        }
    }
}

