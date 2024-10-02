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

package com.wintmain.foundation.prejob.extDemo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.wintmain.foundation.R;

public class ep5_7 extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep5_7);
        textView = (TextView) findViewById(R.id.tv);
        //        registerForContextMenu(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this); // 实例化一个对象
        inflater.inflate(R.menu.optionmenu, menu); // 解析菜单
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getGroupId() == R.id.setting) { // 判断是否选择了“参数设置”菜单项
            if (item.isChecked()) { // 若菜单项已经被选中
                item.setChecked(false); // 设置菜单项不被选中
            } else {
                item.setChecked(true); // 设置菜单项被选中
            }
        }
        if (item.getItemId() != R.id.item2) { // 弹出消息提示框显示选择的菜单项的标题
            Toast.makeText(ep5_7.this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
