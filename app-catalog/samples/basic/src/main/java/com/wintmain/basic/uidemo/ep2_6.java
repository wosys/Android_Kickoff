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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wintmain.basic.R;

public class ep2_6 extends AppCompatActivity {
    private int[] imageId =
            new int[] {
                R.drawable.img01,
                R.drawable.img02,
                R.drawable.img03,
                R.drawable.img04,
                R.drawable.img05,
                R.drawable.img06
            }; // 声明并初始化一个保存要显示图像id的数组
    private int index = 0; // 当前显示图像的索引
    private ImageSwitcher imageSwitcher; // 声明一个ImageSwitcher对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example2_6);
        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1); // 获取图像切换器
        // 设置动画效果
        imageSwitcher.setInAnimation(
                AnimationUtils.loadAnimation(this, android.R.anim.fade_in)); // 设置淡入动画
        imageSwitcher.setOutAnimation(
                AnimationUtils.loadAnimation(this, android.R.anim.fade_out)); // 设置淡出动画
        imageSwitcher.setFactory(
                new ViewSwitcher.ViewFactory() {
                    @Override
                    public View makeView() {
                        ImageView imageView = new ImageView(ep2_6.this); // 实例化一个ImageView对象
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER); // 设置保持纵横比居中缩放图像
                        imageView.setLayoutParams(
                                new ImageSwitcher.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                        return imageView; // 返回对象
                    }
                });
        imageSwitcher.setImageResource(imageId[index]); // 显示默认的图片

        Button up = (Button) findViewById(R.id.button1); // 获取”上一张"按钮
        Button down = (Button) findViewById(R.id.button2); // 获取"下- -张"按钮
        up.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (index > 0) {
                            index--; // index的值减1
                        } else {
                            index = imageId.length - 1;
                        }
                        imageSwitcher.setImageResource(imageId[index]);
                    }
                });
        down.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (index < imageId.length - 1) {
                            index++; // index的值加1
                        } else {
                            index = 0;
                        }
                        imageSwitcher.setImageResource(imageId[index]); // 显示当前图片
                    }
                });
    }
}
