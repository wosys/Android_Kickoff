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

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class ep2_8 extends AppCompatActivity {
    private int[] imageId =
            new int[] {
                R.drawable.img01,
                R.drawable.img02,
                R.drawable.img03,
                R.drawable.img04,
                R.drawable.img05,
                R.drawable.img06
            }; // 定义并初始化保存图片id的数组

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example2_8);

        Gallery gallery = (Gallery) findViewById(R.id.gallery1);

        BaseAdapter adapter =
                new BaseAdapter() {
                    // 获取数量
                    @Override
                    public int getCount() {
                        return imageId.length;
                    }

                    // 获得当前选项
                    @Override
                    public Object getItem(int position) {
                        return position;
                    }

                    // 获得当前选项的ID值
                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(int position, View convertview, ViewGroup parent) {
                        ImageView imageView; // 声明对象
                        if (convertview == null) {
                            imageView = new ImageView(ep2_8.this); // 实例化对象
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY); // 设置缩放方式
                            imageView.setLayoutParams(new Gallery.LayoutParams(300, 300));
                            TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
                            imageView.setBackgroundResource(
                                    typedArray.getResourceId(
                                            R.styleable.Gallery_android_galleryItemBackground, 0));
                            imageView.setPadding(5, 0, 5, 0); // 设置ImageView的内边距
                        } else {
                            imageView = (ImageView) convertview;
                        }
                        imageView.setImageResource(imageId[position]);
                        return imageView;
                    }
                };

        gallery.setAdapter(adapter); // 将适配器与Gallery关联
        gallery.setSelection(imageId.length / 2); // 选中中间的图片
        gallery.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> adapterView, View view, int position, long id) {
                        Toast.makeText(
                                        ep2_8.this,
                                        "您选择了第" + String.valueOf(position) + "张图片",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}
