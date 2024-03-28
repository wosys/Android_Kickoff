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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.wintmain.basic.R;

public class ep3_3 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep3_3);

        Button reg = findViewById(R.id.reg);
        Button re = findViewById(R.id.re);

        EditText user = findViewById(R.id.user);
        EditText pwd = findViewById(R.id.pwd);
        EditText pwdc = findViewById(R.id.pwdc);
        EditText email = findViewById(R.id.email);

        re.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.setText("");
                        pwd.setText("");
                        pwdc.setText("");
                        email.setText("");
                    }
                });

        reg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String users = user.getText().toString();
                        String pwds = pwd.getText().toString();
                        String pwdcs = pwdc.getText().toString();
                        String emails = email.getText().toString();
                        if (!"".equals(users)
                                && !"".equals(pwds)
                                && !"".equals(pwdcs)
                                && !"".equals(emails)) {
                            if (!pwds.equals(pwdcs)) {
                                Toast.makeText(ep3_3.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                                pwd.requestFocus();
                            } else {
                                Intent intent = new Intent(ep3_3.this, ep3_3second.class);
                                Bundle bundle = new Bundle();
                                bundle.putCharSequence("users", users);
                                bundle.putCharSequence("pwds", pwds);
                                bundle.putCharSequence("emails", emails);
                                intent.putExtras(bundle);
                                //                        startActivity(intent);
                                startActivityForResult(intent, 0x717);
                            }
                        } else {
                            Toast.makeText(ep3_3.this, "请输入完整信息", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
