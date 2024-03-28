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

package com.wintmain.basic.prejob.extDemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class ep5_6 extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        setContentView(layout);
        textView = new TextView(this);
        textView.setTextSize(28);
        textView.setText("打开菜单。。。");
        layout.addView(textView);
        registerForContextMenu(textView); // 为文本框注册上下文菜单
    }

    @Override
    public void onCreateContextMenu(
            ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this); // 实例化一个MenuInflater对象
        inflater.inflate(R.menu.contextmenu, menu); // 解析菜单文件
        //        menu.setHeaderIcon(R.drawable.green1);//为菜单头设置图标
        menu.setHeaderTitle("请选择文字颜色："); // 为菜单头设置标题
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.color1) {
            textView.setTextColor(Color.rgb(255, 0, 0));
        } else if (item.getItemId() == R.id.color2) {
            textView.setTextColor(Color.rgb(0, 255, 0));
        } else if (item.getItemId() == R.id.color3) {
            textView.setTextColor(Color.rgb(0, 0, 255));
        } else if (item.getItemId() == R.id.color4) {
            textView.setTextColor(Color.rgb(255, 180, 0));
        } else {
            textView.setTextColor(Color.rgb(255, 255, 255));
        }
        return true;
    }
}
