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
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class ep2_10 extends AppCompatActivity {
    private int[] imageId =
            new int[] {
                R.drawable.img01,
                R.drawable.img02,
                R.drawable.img03,
                R.drawable.img04,
                R.drawable.img05,
                R.drawable.img06
            }; // 定义并初始化保存图片id的数组
    private ImageSwitcher imageSwitcher; // 声明一个图像切换器对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example2_10);

        Gallery gallery = (Gallery) findViewById(R.id.galleryy10); // 获取Gallery组件
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcherr10);

        // 设置动画效果
        // 设置淡入动画
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        // 设置淡出动画
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        imageSwitcher.setFactory(
                new ViewSwitcher.ViewFactory() {
                    @Override
                    public View makeView() {
                        ImageView imageView = new ImageView(ep2_10.this); // 实例化一个类的对象
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER); // 设置保持纵横比居中缩放图像
                        imageView.setLayoutParams(
                                new FrameLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                        return imageView; // 返回的是一个imageView对象
                    }
                });

        BaseAdapter adapter =
                new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return imageId.length;
                    }

                    @Override
                    public Object getItem(int i) {
                        return i;
                    }

                    @Override
                    public long getItemId(int i) {
                        return i;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        ImageView imageview; // 声明ImageView的对象
                        if (convertView == null) {
                            imageview = new ImageView(ep2_10.this); // 实例化ImageView的对象
                            imageview.setScaleType(ImageView.ScaleType.FIT_XY); // 设置缩放方式
                            imageview.setLayoutParams(new Gallery.LayoutParams(300, 300));
                            TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
                            imageview.setBackgroundResource(
                                    typedArray.getResourceId(
                                            R.styleable.Gallery_android_galleryItemBackground, 0));
                            imageview.setPadding(5, 0, 5, 0); // 设置ImageView的内边距
                        } else {
                            imageview = (ImageView) convertView;
                        }
                        imageview.setImageResource(imageId[position]); // 为ImageView设置要显示的图片
                        return imageview; // 返回ImageView
                    }
                };

        gallery.setAdapter(adapter); // 关联适配器
        gallery.setSelection(imageId.length / 2); // 选中中间的图片
        gallery.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> adapterView, View view, int position, long l) {
                        imageSwitcher.setImageResource(imageId[position]); // 显示选中的照片
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                });
    }
}
