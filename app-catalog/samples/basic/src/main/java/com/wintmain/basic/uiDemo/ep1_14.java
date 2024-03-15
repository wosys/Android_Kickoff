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

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.wintmain.basic.R;

public class ep1_14 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep1_14);

        RadioGroup sex = findViewById(R.id.radiogroup1);
        sex.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        // i -> checkedId
                        RadioButton r = findViewById(i);
                        Log.i("单选按钮", "选择的是：" + r.getText()); // 获取被选中的单选按钮的值
                    }
                });

        final RadioGroup sex2 = findViewById(R.id.radiogroup1);
        Button button = findViewById(R.id.button1);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < sex2.getChildCount(); i++) {
                            RadioButton r = (RadioButton) sex2.getChildAt(i); // 根据索引值获取单选按钮的值
                            if (r.isChecked()) { // 判断是否选中
                                Toast.makeText(
                                                ep1_14.this,
                                                "你选择的是：" + r.getText(),
                                                Toast.LENGTH_SHORT)
                                        .show();
                                break; // 跳出循环，单选
                            }
                        }
                    }
                });
    }
}
