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

package com.wintmain.basic.uidemo;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ep2_7 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example2_7);

        GridView gridview = (GridView) findViewById(R.id.gridView1); // 获取GridView组件
        int[] imageId =
                new int[] {
                    R.drawable.img01,
                    R.drawable.img02,
                    R.drawable.img03,
                    R.drawable.img04,
                    R.drawable.img05,
                    R.drawable.img06
                }; // 定义并初始化保存图片id的数组
        String[] title =
                new String[] {
                    "智能手机", "运营商认证", "自动化测试", "系统升级", "硬件设计", "Camera一站式"
                }; // 定义并初始化保存说明文字的数组
        List<Map<String, Object>> listItems = new ArrayList<>(); // 创建一个List集合
        // 通过for循环将图片id和列表项文字放到Map中并添加到List集合中
        for (int i = 0; i < imageId.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", imageId[i]);
            map.put("title", title[i]);
            listItems.add(map); // 将map对象添加到List集合中
        }

        SimpleAdapter adapter =
                new SimpleAdapter(
                        this,
                        listItems,
                        R.layout.items,
                        new String[] {"title", "image"},
                        new int[] {R.id.title, R.id.image}); // 创建适配器
        gridview.setAdapter(adapter); // 将适配器与GridView关联
    }
}
