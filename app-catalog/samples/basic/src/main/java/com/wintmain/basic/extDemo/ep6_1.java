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

package com.wintmain.basic.extDemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.wintmain.basic.R;

public class ep6_1 extends Activity {
    private String[] columns = {
            ContactsContract.Contacts._ID,//希望获得ID值
            ContactsContract.Contacts.DISPLAY_NAME,//希望获得姓名
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ep6_1);
        TextView textView = (TextView)findViewById(R.id.result);
        textView.setText(getQueryData());//为标签设置数据
    }

    private String getQueryData() {
        StringBuilder stringBuilder = new StringBuilder();//用于保存字符串
        ContentResolver contentResolver = getContentResolver();//获得ContentResolver对象
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, columns, null, null, null);
        //查询记录
        int idIndex = cursor.getColumnIndex(columns[0]);//获得ID记录的索引值
        int displayNameIndex = cursor.getColumnIndex(columns[1]);//获得姓名记录的索引值
        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){//迭代全部
            int id = cursor.getInt(idIndex);
            String displayName = cursor.getString(displayNameIndex);
            stringBuilder.append(id + ": "+ displayName + "\n");
        }
        cursor.close();//关闭CUrsor
        return stringBuilder.toString();//返回查询结果
    }

}
