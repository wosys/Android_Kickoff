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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.wintmain.basic.R;

public class RabbitView extends View {
    public float bitmapX; // 小兔子显示位置的X坐标
    public float bitmapY; // 小兔子显示位置的Y坐标

    public RabbitView(Context context) { // 重写构造方法
        super(context);
        bitmapX = 75;
        bitmapY = 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(); // 创建并实例化Paint的对象
        Bitmap bitmap =
                BitmapFactory.decodeResource(this.getResources(), R.drawable.rabbit); // 根据图片生成位图对象
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint); // 绘制小兔子
        if (bitmap.isRecycled()) { // 判断图片是否回收
            bitmap.recycle(); // 强制回收图片
        }
    }
}
