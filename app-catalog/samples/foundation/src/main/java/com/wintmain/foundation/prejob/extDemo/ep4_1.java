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

package com.wintmain.foundation.prejob.extDemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.wintmain.foundation.R;

public class ep4_1 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep4_1);

        Button ok = findViewById(R.id.ok);
        ok.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText username = findViewById(R.id.username);
                        EditText password = findViewById(R.id.password); // 获得用户名和密码

                        Intent intent = new Intent(); // 创建一个Intent对象
                        intent.putExtra("com.android.USERNAME", username.getText().toString());
                        intent.putExtra(
                                "com.android.PASSWORD", password.getText().toString()); // 封装信息
                        intent.setClass(ep4_1.this, ep4_1second.class); // 指定传递对象
                        startActivity(intent); // 将Intent传递给Activity
                    }
                });
    }
}
