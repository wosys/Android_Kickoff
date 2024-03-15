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
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.DecimalFormat;

public class ep3_5second extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Person person = (Person) bundle.getSerializable("person");
        textView.setText(person.getSex() + getWeight(person.getSex(), person.getHeight()));

        linearLayout.addView(textView);
    }

    private String getWeight(String sex, float height) {
        DecimalFormat decimalFormat = new DecimalFormat();
        if (sex.equals("男")) {
            return decimalFormat.format((height - 80) * 0.7);
        }
        if (sex.equals("女")) {
            return decimalFormat.format((height - 70) * 0.6);
        }
        return "";
    }
}
