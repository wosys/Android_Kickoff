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

package com.wintmain.basic.uiDemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class ep2_5 extends AppCompatActivity {
    private TabHost tabHost; // 选项卡对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example2_5);
        tabHost = (TabHost) findViewById(android.R.id.tabhost); // 获取TabHost对象
        tabHost.setup(); // 初始化TabHost组件

        LayoutInflater inflater = LayoutInflater.from(this); // 声明并实例化一个LayoutInflater对象
        inflater.inflate(R.layout.tab1, tabHost.getTabContentView());
        inflater.inflate(R.layout.tab2, tabHost.getTabContentView());
        tabHost.addTab(
                tabHost.newTabSpec("tab01")
                        .setIndicator("智能设备")
                        .setContent(R.id.linearlayout01)); // 添加第一个标签页
        tabHost.addTab(
                tabHost.newTabSpec("tab02")
                        .setIndicator("智慧城市")
                        .setContent(R.id.linearlayout02)); // 添加第二个标签页
    }
}
