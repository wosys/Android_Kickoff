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

package com.wintmain.basic.extDemo;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class ep5_2 extends AppCompatActivity {
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.ep5_2);
        int[] tvID =
                new int[] {
                    R.id.str1, R.id.str2, R.id.str3, R.id.str4, R.id.str5, R.id.str6, R.id.str7
                }; // 定义TextView组件的id数组
        int[] tvColor =
                new int[] {
                    R.color.color1,
                    R.color.color2,
                    R.color.color3,
                    R.color.color4,
                    R.color.color5,
                    R.color.color6,
                    R.color.color7
                }; // 使用颜色资源
        for (int i = 0; i < 7; i++) {
            TextView tv = (TextView) findViewById(tvID[i]); // 根据id获取TextView组件
            tv.setGravity(Gravity.CENTER); // 设置文字居中显示
            tv.setBackgroundColor(getResources().getColor(tvColor[i])); // 为TextView组件设置背景颜色
            tv.setHeight(
                    (int) (getResources().getDimension(R.dimen.basic))
                            * (i + 2)
                            / 2); // 为TextView组件设置高度
        }
    }
}
