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

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class ep1_1 extends AppCompatActivity {

    public TextView text2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. xml方式
        //        setContentView(R.layout.ep1_1);

        // 2. code方式
        // 创建帧布局管理器
        FrameLayout frameLayout = new FrameLayout(this);
        // 设置背景
        frameLayout.setBackgroundDrawable(
                this.getResources().getDrawable(R.drawable.ic_launcher_foreground));
        // 设置在Activity中显示frameLayout
        setContentView(frameLayout);
        // 创建一个TextView组件text1,设置其文字大小和颜色，并将其添加到布局管理器中
        TextView text1 = new TextView(this);
        text1.setText("在代码中控制UI界面"); // 设置显示的文字
        text1.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24); // 设置文字大小，单位为像素
        text1.setTextColor(Color.rgb(1, 1, 1)); // 设置文字的颜色
        frameLayout.addView(text1); // 将 text1添加到布局管理器中

        // 实例化text2组件，设置其显示文字、文字大小、颜色和布局
        text2 = new TextView(this);
        text2.setText("单击进入Android......"); // 设置显示文字
        text2.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24); // 设置文字大小，单位为像素
        text2.setTextColor(Color.rgb(1, 1, 1)); // 设置文字的颜色
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT); // 创建保存布局参数的对象
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL; // 设置居中显示
        text2.setLayoutParams(params); // 设置布局参数
    }
}
