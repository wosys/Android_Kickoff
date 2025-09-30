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

import android.content.res.XmlResourceParser
import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.wBasis.R
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

@Sample(name = "ep5_1", description = "示例", tags = ["A-Self_demos"])
class ep5_1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val linearLayout = LinearLayout(this)
//        setContentView(linearLayout);
//
//        val textView1 = TextView(this)
//        textView1.setText(this.getResources().getString(R.string.title2))
//        textView1.setTextColor(this.getResources().getColor(R.color.title2))
//        textView1.setTextSize(this.getResources().getDimension(R.dimen.title2))
//        linearLayout.addView(textView1)
        setContentView(R.layout.ep5_1)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "ep5_2", description = "示例", tags = ["A-Self_demos"])
class ep5_2 : AppCompatActivity() {
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setContentView(R.layout.ep5_2)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val tvID =
            intArrayOf(
                R.id.str1, R.id.str2, R.id.str3, R.id.str4, R.id.str5, R.id.str6, R.id.str7
            ) // 定义TextView组件的id数组
        val tvColor =
            intArrayOf(
                R.color.color1,
                R.color.color2,
                R.color.color3,
                R.color.color4,
                R.color.color5,
                R.color.color6,
                R.color.color7
            ) // 使用颜色资源
        for (i in 0..6) {
            val tv = findViewById<View>(tvID[i]) as TextView // 根据id获取TextView组件
            tv.gravity = Gravity.CENTER // 设置文字居中显示
            tv.setBackgroundColor(resources.getColor(tvColor[i])) // 为TextView组件设置背景颜色
            tv.height = (resources.getDimension(R.dimen.basic).toInt() * (i + 2)
                / 2) // 为TextView组件设置高度
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "ep5_3", description = "示例", tags = ["A-Self_demos"])
class ep5_3 : Fragment(R.layout.ep5_3)

@Sample(name = "ep5_4", description = "示例", tags = ["A-Self_demos"])
class ep5_4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep5_4)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener { view ->
            val b = view as Button // 获取当前按钮
            b.isEnabled = false // 当按钮变为不可用
            b.text = "我是不可按钮"
            Toast.makeText(this@ep5_4, "按钮变为不可用", Toast.LENGTH_SHORT).show()
        }
        val button2 = findViewById<Button>(R.id.button2)
        button2.setOnClickListener {
            button1.isEnabled = true // 当按钮变为不可用
            button1.text = "我是可按钮"
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "ep5_5", description = "示例", tags = ["A-Self_demos"])
class ep5_5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val textView = TextView(this)
        textView.textSize = 28f
        textView.text = "正在读取文件..."
        linearLayout.addView(textView)

        val xmlResourceParser: XmlResourceParser = getResources().getXml(R.xml.customers) // 获取xml文档
        val stringBuilder = StringBuilder("") // 创建一个空的字符串构造器

        try {
            // 如果没有到xml文档的结尾
            while (xmlResourceParser.eventType != XmlPullParser.END_DOCUMENT) {
                if (xmlResourceParser.eventType == XmlPullParser.START_TAG) {
                    // 判断是否是开始标志
                    val tagName = xmlResourceParser.name // 获取标记名
                    if (tagName == "customer") {
                        // 如果标记名是customer
                        stringBuilder.append(
                            "姓名：" + xmlResourceParser.getAttributeValue(1) + " "
                        ) // 获取客户姓名
                        stringBuilder.append(
                            "联系电话：" + xmlResourceParser.getAttributeValue(2) + " "
                        )
                        stringBuilder.append("E-mail：" + xmlResourceParser.getAttributeValue(0))
                        stringBuilder.append("\n")
                    }
                }
                xmlResourceParser.next()
            }
            textView.text = stringBuilder.toString()
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "ep5_6", description = "示例", tags = ["A-Self_demos"])
class ep5_6 : AppCompatActivity() {
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this)
        setContentView(layout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        textView = TextView(this)
        textView!!.textSize = 28f
        textView!!.text = "打开菜单。。。"
        layout.addView(textView)
        registerForContextMenu(textView) // 为文本框注册上下文菜单
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    override fun onCreateContextMenu(
        menu: ContextMenu, v: View, menuInfo: ContextMenuInfo
    ) {
        val inflater = MenuInflater(this) // 实例化一个MenuInflater对象
        inflater.inflate(R.menu.contextmenu, menu) // 解析菜单文件
        //        menu.setHeaderIcon(R.drawable.green1);//为菜单头设置图标
        menu.setHeaderTitle("请选择文字颜色：") // 为菜单头设置标题
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.color1 -> {
                textView!!.setTextColor(Color.rgb(255, 0, 0))
            }

            R.id.color2 -> {
                textView!!.setTextColor(Color.rgb(0, 255, 0))
            }

            R.id.color3 -> {
                textView!!.setTextColor(Color.rgb(0, 0, 255))
            }

            R.id.color4 -> {
                textView!!.setTextColor(Color.rgb(255, 180, 0))
            }

            else -> {
                textView!!.setTextColor(Color.rgb(255, 255, 255))
            }
        }
        return true
    }
}

@Sample(name = "ep5_7", description = "示例", tags = ["A-Self_demos"])
class ep5_7 : AppCompatActivity() {
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep5_7)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        textView = findViewById<View>(R.id.tv) as TextView
        //        registerForContextMenu(textView);
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = MenuInflater(this) // 实例化一个对象
        inflater.inflate(R.menu.optionmenu, menu) // 解析菜单
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.groupId == R.id.setting) { // 判断是否选择了“参数设置”菜单项
            if (item.isChecked) { // 若菜单项已经被选中
                item.setChecked(false) // 设置菜单项不被选中
            } else {
                item.setChecked(true) // 设置菜单项被选中
            }
        }
        if (item.itemId != R.id.item2) { // 弹出消息提示框显示选择的菜单项的标题
            Toast.makeText(this@ep5_7, item.title, Toast.LENGTH_SHORT).show()
        }
        return true
    }
}

@Sample(name = "ep5_8", description = "示例", tags = ["A-Self_demos"])
class ep5_8 : Fragment(R.layout.ext_activity_main)

@Sample(name = "ep5_9", description = "示例", tags = ["A-Self_demos"])
class ep5_9 : Fragment(R.layout.ep5_9)

@Sample(name = "ep5_10", description = "示例", tags = ["A-Self_demos"])
class ep5_10 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val editText = EditText(this)
        editText.setText(R.string.edittext)
        linearLayout.addView(editText)
        registerForContextMenu(editText) // 为编辑框注册上下文菜单
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    override fun onCreateContextMenu(
        menu: ContextMenu, v: View, menuInfo: ContextMenuInfo
    ) {
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.contextmenu2, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
        return true
    }
}
