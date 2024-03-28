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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class EpOne_15 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep1_15);

        CheckBox c1 = findViewById(R.id.c1);
        CheckBox c2 = findViewById(R.id.c2);
        CheckBox c3 = findViewById(R.id.c3);

        c1.setOnCheckedChangeListener(listener);
        c2.setOnCheckedChangeListener(listener);
        c3.setOnCheckedChangeListener(listener);

        Button button = findViewById(R.id.submit1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = "";
                if (c1.isChecked()) answer += c1.getText().toString() + " ";
                if (c2.isChecked()) answer += c2.getText().toString() + " ";
                if (c3.isChecked()) answer += c3.getText().toString() + " ";
                Toast toast = Toast.makeText(EpOne_15.this, "选中" + answer, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                Toast.makeText(EpOne_15.this, "选中了" + buttonView.getText().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
}
