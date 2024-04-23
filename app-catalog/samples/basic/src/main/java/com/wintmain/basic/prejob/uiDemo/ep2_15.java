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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.wintmain.basic.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ep2_15 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example2_15);
        Log.d("ep2_15", "on Create");
        int[] imageId =
                new int[]{
                        R.drawable.img01,
                        R.drawable.img02,
                        R.drawable.img03,
                        R.drawable.img04,
                        R.drawable.img05
                }; // 定义 并初始化保存图片id的数组
        final String[] title =
                new String[]{"程序管理", "保密设置", "安全设置", "邮件设置",
                        "铃声设置"}; // 定义并初始化保存列表项文字的数组
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>(); // 创建一个List集合
        // 通过for循环将图片id和列表项文字放到Map中,并添加到List集合中
        for (int i = 0; i < imageId.length; i++) {
            Map<String, Object> map = new HashMap<>(); // 实例化map对象
            map.put("image", imageId[i]);
            map.put("title", title[i]);
            listItems.add(map); // 将map对象添加到List集合中
            final SimpleAdapter adapter =
                    new SimpleAdapter(
                            this,
                            listItems,
                            R.layout.items,
                            new String[]{"title", "image"},
                            new int[]{R.id.title, R.id.image}); // 创建SimpleAdapter

            Button button1 = (Button) findViewById(R.id.button);
            button1.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ep2_15.this);
                            builder.setIcon(R.drawable.rabbit);
                            builder.setTitle("设置：");
                            builder.setAdapter(
                                    adapter,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialogInterface, int i) {
                                            Toast.makeText(
                                                            ep2_15.this,
                                                            "你选择了【" + title[i] + "】",
                                                            Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    });
                            builder.create().show();
                        }
                    });
        }
    }
}
