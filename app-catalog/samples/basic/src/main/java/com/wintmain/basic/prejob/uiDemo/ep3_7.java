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

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class ep3_7 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep3_7);
    }

    public static class DetailActivity extends Activity {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.ep3_7_detail);
            // 如果为横屏，结束当前Activity，准备使用Fragment显示内容
            if (getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_LANDSCAPE) {
                finish(); // 结束当前Activity
                return;
            }
            if (savedInstanceState == null) {
                // 如果不是横屏就插入显示详细内容的Fragment
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(getIntent().getExtras());
                // 设置要传递的参数
                getFragmentManager()
                        .beginTransaction()
                        .add(android.R.id.content, detailFragment)
                        .commit(); // 添加一个显示详细内容的Fragment
            }
        }
    }
}
