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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.wintmain.basic.R;

public class ep2_1 extends AppCompatActivity {

    private static final String[] COUNTRIES =
            new String[]{"android", "android app", "android开发", "开发应用", "开发者"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example2_1);

        // 获取自动完成文本框
        AutoCompleteTextView textView =
                (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        // 创建一个ArrayAdapter适配器
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        // 为自动完成文本框设置适配器
        textView.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(
                                        ep2_1.this,
                                        textView.getText().toString(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}
