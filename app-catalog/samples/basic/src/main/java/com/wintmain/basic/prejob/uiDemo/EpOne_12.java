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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.wintmain.basic.R;

public class EpOne_12 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep1_12);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nicknameET = (EditText) findViewById(R.id.nickname); // 获取会员昵称编辑框组件
                String nickname = nicknameET.getText().toString(); // 获取输入的会员昵称
                EditText pwdET = (EditText) findViewById(R.id.pwd); // 获取密码编辑框组件
                String pwd = pwdET.getText().toString(); // 获取输入的密码
                EditText emailET = (EditText) findViewById(R.id.email); // 获取E-mail编辑框组件
                String email = emailET.getText().toString(); // 获取输入的E-mail地址

                EditText confirmpwdET = (EditText) findViewById(R.id.confirmpwd); // 获取确认密码编辑框组件
                String confirmpwd = confirmpwdET.getText().toString();

                if (!confirmpwd.isEmpty() && confirmpwd.equals(pwd)) {
                    Toast.makeText(getApplicationContext(),
                            "会员昵称:" + nickname + " 密码:" + pwd +
                                    " E-mail地址:" + email, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "两次输入的密码不一致",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nicknameET = (EditText) findViewById(R.id.nickname); // 获取会员昵称编辑框组件
                nicknameET.setText("");
                EditText pwdET = (EditText) findViewById(R.id.pwd); // 获取密码编辑框组件
                pwdET.setText("");
                EditText confirmpwdET = (EditText) findViewById(R.id.confirmpwd); // 获取确认密码编辑框组件
                confirmpwdET.setText("");
                EditText emailET = (EditText) findViewById(R.id.email); // 获取E-mail编辑框组件
                emailET.setText("");

                Toast.makeText(getApplicationContext(), "Reset success...", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
