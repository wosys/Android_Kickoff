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

package com.wintmain.foundation.prejob.uiDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;

public class ep3_5 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        RadioGroup group = new RadioGroup(this);
        RadioButton button1 = new RadioButton(this);
        RadioButton button2 = new RadioButton(this);
        button1.setText("男");
        button2.setText("女");
        group.addView(button1);
        group.addView(button2);

        EditText editText = new EditText(this);

        Button button = new Button(this);
        button.setText("提交");

        linearLayout.addView(group);
        linearLayout.addView(editText);
        linearLayout.addView(button);

        Person person = new Person();

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("".equals(editText.getText().toString())) {
                            Toast.makeText(ep3_5.this, "null height", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int i = Integer.parseInt(editText.getText().toString());
                        person.setHeight(i);
                        if (button1.isChecked()) {
                            person.setSex(button1.getText().toString());
                        }
                        if (button2.isChecked()) {
                            person.setSex(button2.getText().toString());
                        }

                        Intent intent = new Intent(ep3_5.this, ep3_5second.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("person", person);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
    }
}
