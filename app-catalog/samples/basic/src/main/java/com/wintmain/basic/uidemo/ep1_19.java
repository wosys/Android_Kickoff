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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.wintmain.basic.R;

public class ep1_19 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //        布局
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(ep1_19.this);
        setContentView(linearLayout);
        //        listview
        ListView listView = new ListView(ep1_19.this);
        //        配置分割线
        View line = View.inflate(ep1_19.this, R.layout.ep1_19, null);
        //        设置头部线
        listView.addHeaderView(line);
        //        创建适配器
        ArrayAdapter<CharSequence> ad =
                ArrayAdapter.createFromResource(
                        this, R.array.listview, android.R.layout.simple_list_item_checked);
        listView.setAdapter(ad);
        listView.addFooterView(line);
        //        放进去
        linearLayout.addView(listView);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        String s = parent.getItemAtPosition(position).toString();
                        Toast.makeText(ep1_19.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
