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

package com.wintmain.androidpft;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.catalog.framework.annotations.Sample;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.bar.style.LightBarStyle;
import com.hjq.toast.Toaster;

@Sample(
        name = "Android PfT",
        description = "Android Platform for Technology.",
        documentation = "",
//    owners = ["wintmain"],
        tags = "A-Self_demos"
)
public class PlaceHolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pft_activity_main);

        // 初始化一些三方库
        initLibs(getApplication());

        TitleBar titleBar = findViewById(R.id.tb_main_bar_click);
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {

            @Override
            public void onLeftClick(TitleBar titleBar) {
                Toaster.show("你点击了返回");
            }

            @Override
            public void onTitleClick(TitleBar titleBar) {
                Toaster.show("你点击了中间");
            }

            @Override
            public void onRightClick(TitleBar titleBar) {
                Toaster.show("你点击了设置");
            }
        });
    }

    private void initLibs(Application application) {
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

        // 初始化 Toast
        Toaster.init(this.getApplication());

    }
}
