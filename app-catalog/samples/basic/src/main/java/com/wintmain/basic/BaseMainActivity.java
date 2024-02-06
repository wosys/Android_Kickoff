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

package com.wintmain.basic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.catalog.framework.annotations.Sample;
import com.wintmain.basic.uidemo.ep1_1;
import com.wintmain.basic.uidemo.ep1_4;
import com.wintmain.basic.uidemo.ep1_6;
import com.wintmain.basic.uidemo.ep1_7;

@SuppressWarnings("unchecked")
@Sample(name = "Basic", description = "Android学习基础", documentation = "", tags = "A-Self_demos")
public class BaseMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_activity_main);

        Button ep_1 = this.findViewById(R.id.button1);
        ep_1.setOnClickListener(
                v -> startActivity(new Intent().setClass(BaseMainActivity.this, ep1_1.class)));

        Button ep_2 = this.findViewById(R.id.button2);
        ep_2.setOnClickListener(
                v -> startActivity(new Intent().setClass(BaseMainActivity.this, ep1_4.class)));

        Button ep_3 = this.findViewById(R.id.button3);
        ep_3.setOnClickListener(
                v -> startActivity(new Intent().setClass(BaseMainActivity.this, ep1_6.class)));

        Button ep_4 = this.findViewById(R.id.button4);
        ep_4.setOnClickListener(
                v -> startActivity(new Intent().setClass(BaseMainActivity.this, ep1_7.class)));
    }
}
