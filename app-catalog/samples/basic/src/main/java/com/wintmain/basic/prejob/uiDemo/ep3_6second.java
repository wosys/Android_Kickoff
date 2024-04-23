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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.wintmain.basic.R;

public class ep3_6second extends Activity {
    int[] image =
            new int[]{
                    R.drawable.img01,
                    R.drawable.img02,
                    R.drawable.img03,
                    R.drawable.img04,
                    R.drawable.img05
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep3_6second);
        GridView gridView = findViewById(R.id.gridview);
        BaseAdapter baseAdapter =
                new BaseAdapter() {

                    @Override
                    public int getCount() {
                        return image.length;
                    }

                    @Override
                    public Object getItem(int position) {
                        return position;
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        ImageView imageView;
                        if (convertView == null) {
                            imageView = new ImageView(ep3_6second.this);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageView.setPadding(5, 0, 5, 0);
                            ViewGroup.LayoutParams layoutParams =
                                    new ViewGroup.LayoutParams(200, 200);
                            imageView.setLayoutParams(layoutParams);
                        } else {
                            imageView = (ImageView) convertView;
                        }
                        imageView.setImageResource(image[position]);
                        return imageView;
                    }
                };
        gridView.setAdapter(baseAdapter);

        gridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = getIntent();
                        Bundle bundle = new Bundle();
                        bundle.putInt("imageId", image[position]);
                        intent.putExtras(bundle);
                        setResult(0x888, intent);
                        finish();
                    }
                });
    }
}
