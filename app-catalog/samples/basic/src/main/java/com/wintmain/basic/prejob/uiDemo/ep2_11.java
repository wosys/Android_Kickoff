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
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class ep2_11 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example2_11);
        Toast.makeText(this, "Toast的第一种创建方式，通过makeText()", Toast.LENGTH_SHORT).show();

        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT); // 设置持续时间
        toast.setGravity(Gravity.CENTER, 0, 0); // 设置对齐方式

        LinearLayout ll = new LinearLayout(this); // 创建一个线性布局管理器
        ImageView imageView = new ImageView(this); // 创建一个ImageView对象
        imageView.setImageResource(R.drawable.img01); // 设置要显示的图片
        imageView.setPadding(0, 0, 5, 0); // 设置imageView的内边距
        ll.addView(imageView); // 将imageView添加到线性布局管理器中
        TextView textView = new TextView(this); // 创建一个TextView对象
        textView.setText("Toaset的第二种创建方式，通过构造方法");
        ll.addView(textView);

        toast.setView(ll);
        toast.show(); // 显示消息提示框
    }
}
