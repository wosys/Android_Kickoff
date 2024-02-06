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

package com.wintmain.basic.extdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class ep4_1second extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep4_1second); // 设置页面布局
        Intent intent = getIntent(); // 获得Intent
        String username = intent.getStringExtra("com.android.USERNAME");
        String password = intent.getStringExtra("com.android.PASSWORD");

        TextView usernameTV = findViewById(R.id.usr);
        TextView passwordTV = findViewById(R.id.pwd);

        // 设置文本框内容，首先获取控件
        usernameTV.setText("用户名" + username);
        passwordTV.setText("密码" + password);
    }
}
