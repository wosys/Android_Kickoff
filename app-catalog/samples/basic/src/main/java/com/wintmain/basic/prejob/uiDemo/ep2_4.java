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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class ep2_4 extends AppCompatActivity {
    private RatingBar ratingbar; // 星级评分条

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example2_4);

        ratingbar = (RatingBar) findViewById(R.id.ratingBar1); // 获取星级评分条
        Button button = (Button) findViewById(R.id.button1); // 获取“提交”按钮
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int result = ratingbar.getProgress(); // 获取进度
                        float rating = ratingbar.getRating(); // 获取等级
                        float step = ratingbar.getStepSize(); // 获取每次最少要改变多少个星级
                        Log.i("星级评分条", "step=" + step + " result=" + result + " rating=" + rating);
                        Toast.makeText(ep2_4.this, "你得到了" + rating + "颗星", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}
