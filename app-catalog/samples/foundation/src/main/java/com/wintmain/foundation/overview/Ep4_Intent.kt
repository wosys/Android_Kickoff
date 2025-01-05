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

package com.wintmain.foundation.overview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.foundation.R

@Sample(name = "Intent", description = "意图示例", tags = ["A-Self_demos"])
class ep4_1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep4_1)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val ok = findViewById<Button>(R.id.ok)
        ok.setOnClickListener {
            val username = findViewById<EditText>(R.id.username)
            val password = findViewById<EditText>(R.id.password) // 获得用户名和密码

            val intent = Intent() // 创建一个Intent对象
            intent.putExtra("com.android.USERNAME", username.text.toString())
            intent.putExtra("com.android.PASSWORD", password.text.toString()) // 封装信息
            intent.setClass(this@ep4_1, ep4_1second::class.java) // 指定传递对象
            startActivity(intent) // 将Intent传递给Activity
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

class ep4_1second : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep4_1second) // 设置页面布局
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val intent = intent // 获得Intent
        val username = intent.getStringExtra("com.android.USERNAME")
        val password = intent.getStringExtra("com.android.PASSWORD")

        val usernameTV = findViewById<TextView>(R.id.usr)
        val passwordTV = findViewById<TextView>(R.id.pwd)

        // 设置文本框内容，首先获取控件
        usernameTV.text = "用户名$username"
        passwordTV.text = "密码$password"
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "IntentExt", description = "意图示例", tags = ["A-Self_demos"])
class ep4_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this) // 设置页面布局
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button = Button(this)
        button.text = "Home"
        linearLayout.addView(button)

        button.setOnClickListener {
            val intent = Intent()
            intent.setAction(Intent.ACTION_MAIN) // 设置Intent动作
            intent.addCategory(Intent.CATEGORY_HOME) // 设置Intent种类
            startActivity(intent) // 将Intent传递给Activity
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "IntentExt", description = "意图示例", tags = ["A-Self_demos"])
class ep4_3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        val button = Button(this)
        button.text = "预定义intent点击"
        linearLayout.addView(button)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        button.setOnClickListener {
            val intent = Intent()
//            intent.setAction(Intent.ACTION_VIEW)
            intent.setAction("test_action")
            startActivity(intent)
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

class ep4_3second : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val textView = TextView(this)
        textView.text = "使用预定义动作的隐式intent"
        linearLayout.addView(textView)
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "Intent-setAction", description = "意图示例", tags = ["A-Self_demos"])
class ep4_5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        val editText = EditText(this)
        editText.hint = "输入电话号码"
        val s = editText.text.toString()
        val button = Button(this)
        button.text = "拨打"
        linearLayout.addView(editText)
        linearLayout.addView(button)
        linearLayout.orientation = LinearLayout.VERTICAL
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        button.setOnClickListener {
            val intent = Intent()
            intent.setAction(Intent.ACTION_CALL)
            intent.setData(Uri.parse("tel:$s"))
            startActivity(intent)
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "Intent-setData", description = "意图示例", tags = ["A-Self_demos"])
class ep4_6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button = Button(this)
        button.text = "start uri test"
        linearLayout.addView(button)

        button.setOnClickListener {
            val parse = Uri.parse("http://www.baidu.com")
            val intent = Intent()
            intent.setAction(Intent.ACTION_VIEW)
            intent.setData(parse)
            startActivity(intent)
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}
