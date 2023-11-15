/*
 * Copyright 2023 wintmain
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

package com.wintmain.titlebar;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.catalog.framework.annotations.Sample;

import lib.wintmain.titlebar.OnTitleBarListener;
import lib.wintmain.titlebar.TitleBar;
import lib.wintmain.titlebar.style.LightBarStyle;

@Sample(
        name = "Titlebar_demo",
        description = "标题栏例子",
        documentation = "",
//    owners = ["wintmain"],
        tags = "A-Self_demos"
)
public class TitleBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.titlebar_main);

        // 初始化
        initLibs();

        TitleBar titleBar = findViewById(R.id.tb_main_bar_click);
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {

            @Override
            public void onLeftClick(TitleBar titleBar) {
                Toast.makeText(getApplicationContext(),"左项 View 被点击", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTitleClick(TitleBar titleBar) {
                Toast.makeText(getApplicationContext(),"中间 View 被点击", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRightClick(TitleBar titleBar) {
                Toast.makeText(getApplicationContext(),"右项 View 被点击", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initLibs() {
        // 初始化 TitleBar 默认样式
        TitleBar.setDefaultStyle(new LightBarStyle() {
            @Override
            public TextView newTitleView(Context context) {
                return new AppCompatTextView(context);
            }

            @Override
            public TextView newLeftView(Context context) {
                return new AppCompatTextView(context);
            }

            @Override
            public TextView newRightView(Context context) {
                return new AppCompatTextView(context);
            }
        });
    }
}
