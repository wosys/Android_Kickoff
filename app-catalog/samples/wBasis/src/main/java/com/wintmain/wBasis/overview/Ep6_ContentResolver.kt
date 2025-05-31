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

package com.wintmain.wBasis.overview

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.Contacts
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.CursorAdapter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.wBasis.R

// TODO 授予读取contacts权限
@Sample(name = "ep6_1", description = "ContentResolver示例", tags = ["A-Self_demos"])
class ep6_1 : AppCompatActivity() {
    private val columns = arrayOf(
        Contacts._ID,  // 希望获得ID值
        Contacts.DISPLAY_NAME,  // 希望获得姓名
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep6_1)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val textView = findViewById<View>(R.id.result) as TextView
        textView.text = queryData // 为标签设置数据
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    private val queryData: String
        get() {
            val stringBuilder = StringBuilder() // 用于保存字符串
            val contentResolver = contentResolver // 获得ContentResolver对象
            val cursor = contentResolver.query(
                Contacts.CONTENT_URI, columns, null,
                null, null
            )
            // 查询记录
            val idIndex = cursor!!.getColumnIndex(columns[0]) // 获得ID记录的索引值
            val displayNameIndex = cursor.getColumnIndex(columns[1]) // 获得姓名记录的索引值
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                // 迭代全部
                val id = cursor.getInt(idIndex)
                val displayName = cursor.getString(displayNameIndex)
                stringBuilder.append("$id: $displayName\n")
                cursor.moveToNext()
            }
            cursor.close() // 关闭Cursor
            return stringBuilder.toString() // 返回查询结果
        }
}

@Sample(name = "ep6_2", description = "ContentResolver示例", tags = ["A-Self_demos"])
class ep6_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val textView = TextView(this)
        textView.textSize = 25f

        val columns = arrayOf(
            Contacts._ID,
            Contacts.DISPLAY_NAME,
            Phone.NUMBER,
            Phone.CONTACT_ID
        )
        val stringBuilder = java.lang.StringBuilder()
        val contentResolver = contentResolver
        val cursor =
            contentResolver.query(
                Contacts.CONTENT_URI, null, null, null, null
            )

        while (cursor!!.moveToNext()) {
            val idIndex = cursor.getColumnIndex(columns[0])
            val nameIndex = cursor.getColumnIndex(columns[1])
            val id = cursor.getInt(idIndex)
            val name = cursor.getString(nameIndex)
            val phone =
                contentResolver.query(
                    Phone.CONTENT_URI,
                    null,
                    columns[3] + "=" + id,
                    null,
                    null
                )
            while (phone!!.moveToNext()) {
                val phoneNumIndex = phone.getColumnIndex(columns[2])
                val phoneNum = phone.getString(phoneNumIndex)
                stringBuilder.append("$name: $phoneNum\n")
            }
        }
        cursor.close()

        textView.text = stringBuilder.toString()
        linearLayout.addView(textView)
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "ep6_3", description = "ContentResolver示例", tags = ["A-Self_demos"])
class ep6_3 : AppCompatActivity() {
    private val columns = arrayOf(Contacts._ID, Contacts.DISPLAY_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep6_3)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val resolver = contentResolver
        val cursor = resolver.query(Contacts.CONTENT_URI, columns, null, null, null)
        val adapter = ContactListAdapter(this, cursor)
        val autoCompleteTextView = findViewById<View>(R.id.edit) as AutoCompleteTextView
        autoCompleteTextView.setAdapter(adapter)
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

class ContactListAdapter(context: Context, c: Cursor?) : CursorAdapter(context, c), Filterable {
    private val resolver: ContentResolver = context.contentResolver // 初始化
    private val columns = arrayOf(Contacts._ID, Contacts.DISPLAY_NAME)

    override fun convertToString(cursor: Cursor): CharSequence {
        return cursor.getString(1)
    }

    override fun runQueryOnBackgroundThread(constraint: CharSequence): Cursor {
        val filter = filterQueryProvider
        if (filter != null) {
            return filter.runQuery(constraint)
        }
        val uri =
            Uri.withAppendedPath(
                Contacts.CONTENT_FILTER_URI,
                Uri.encode(constraint.toString())
            )
        return resolver.query(uri, columns, null, null, null)!!
    }

    override fun newView(context: Context, cursor: Cursor, viewGroup: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view =
            inflater.inflate(
                android.R.layout.simple_dropdown_item_1line, viewGroup, false
            ) as TextView
        view.text = cursor.getString(1)
        return view
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        (view as TextView).text = cursor.getString(1)
    }
}
