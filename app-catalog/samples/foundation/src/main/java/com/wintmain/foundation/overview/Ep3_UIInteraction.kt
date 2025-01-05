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
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.ImageView.ScaleType.FIT_XY
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.foundation.R
import com.wintmain.foundation.overview.ep3_7.DetailActivity
import java.io.Serializable
import java.text.DecimalFormat

@Sample(name = "Activity跳转", description = "跳转示例", tags = ["A-Self_demos"])
class ep3_1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep3_1)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button = findViewById<Button>(R.id.button1)
        button.setOnClickListener {
            val intent = Intent(this@ep3_1, ep3_1second::class.java)
            startActivity(intent) // 启动Activity
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

class ep3_1second : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep3_1second)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button = findViewById<Button>(R.id.button2)
        val tv = findViewById<TextView>(R.id.textv)
        tv.text = "详细内容。。。"
        button.setOnClickListener { finish() }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "Activity跳转Ext", description = "跳转示例", tags = ["A-Self_demos"])
class ep3_2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button = Button(this)
        button.text = "获取对话框"
        linearLayout.addView(button)

        button.setOnClickListener {
            val intent = Intent(this@ep3_2, ep3_1second::class.java)
            startActivity(intent)
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "startActivityForResult", description = "跳转示例", tags = ["A-Self_demos"])
class ep3_3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep3_3)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val reg = findViewById<Button>(R.id.reg)
        val re = findViewById<Button>(R.id.re)

        val user = findViewById<EditText>(R.id.user)
        val pwd = findViewById<EditText>(R.id.pwd)
        val pwdc = findViewById<EditText>(R.id.pwdc)
        val email = findViewById<EditText>(R.id.email)

        re.setOnClickListener {
            user.setText("")
            pwd.setText("")
            pwdc.setText("")
            email.setText("")
        }

        reg.setOnClickListener {
            val users = user.text.toString()
            val pwds = pwd.text.toString()
            val pwdcs = pwdc.text.toString()
            val emails = email.text.toString()
            if ("" != users && "" != pwds && "" != pwdcs && "" != emails) {
                if (pwds != pwdcs) {
                    Toast.makeText(
                        this@ep3_3,
                        "两次输入密码不一致",
                        Toast.LENGTH_SHORT
                    ).show()
                    pwd.requestFocus()
                } else {
                    val intent = Intent(this@ep3_3, ep3_3second::class.java)
                    val bundle = Bundle()
                    bundle.putCharSequence("users", users)
                    bundle.putCharSequence("pwds", pwds)
                    bundle.putCharSequence("emails", emails)
                    intent.putExtras(bundle)
                    // startActivity(intent);
                    startActivityForResult(intent, 0x717)
                }
            } else {
                Toast.makeText(this@ep3_3, "请输入完整信息", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

class ep3_3second : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        linearLayout.orientation = LinearLayout.VERTICAL
        val intent = intent
        val bundle = intent.extras
        val textView1 = TextView(this)
        val textView2 = TextView(this)
        val textView3 = TextView(this)

        textView1.text = "用户名：" + bundle!!.getString("users")
        textView2.text = "密码：" + bundle.getString("pwds")
        textView3.text = "邮箱：" + bundle.getString("emails")
        textView1.textSize = 20f
        textView2.textSize = 20f
        textView3.textSize = 20f

        val button = Button(this)
        button.text = "返回"
        button.setOnClickListener {
            setResult(0x717, intent) // 设置返回的结果码,并返回调用该Activity的Activity
            finish()
        }

        linearLayout.addView(textView1)
        linearLayout.addView(textView2)
        linearLayout.addView(textView3)
        linearLayout.addView(button)
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "3_5startActivity", description = "跳转示例", tags = ["A-Self_demos"])
class ep3_5 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        linearLayout.orientation = LinearLayout.VERTICAL

        val group = RadioGroup(this)
        val button1 = RadioButton(this)
        val button2 = RadioButton(this)
        button1.text = "男"
        button2.text = "女"
        group.addView(button1)
        group.addView(button2)

        val editText = EditText(this)

        val button = Button(this)
        button.text = "提交"

        linearLayout.addView(group)
        linearLayout.addView(editText)
        linearLayout.addView(button)

        val person = Person()

        button.setOnClickListener(
            View.OnClickListener {
                if ("" == editText.text.toString()) {
                    Toast.makeText(this@ep3_5, "null height", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }
                val i = editText.text.toString().toInt()
                person.height = i
                if (button1.isChecked) {
                    person.sex = button1.text.toString()
                }
                if (button2.isChecked) {
                    person.sex = button2.text.toString()
                }

                val intent = Intent(this@ep3_5, ep3_5second::class.java)
                val bundle = Bundle()
                bundle.putSerializable("person", person)
                intent.putExtras(bundle)
                startActivity(intent)
            })
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

class Person : Serializable {
    var sex: String = ""
    var height: Int = 0

    override fun toString(): String {
        return "Person{sex='$sex', height=$height}"
    }
}

class ep3_5second : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        linearLayout.orientation = LinearLayout.VERTICAL

        val textView = TextView(this)
        val intent = intent
        val bundle = intent.extras
        val person = bundle!!.getSerializable("person") as Person?
        textView.text = person!!.sex + getWeight(person.sex, person.height.toFloat())

        linearLayout.addView(textView)
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    private fun getWeight(sex: String, height: Float): String {
        val decimalFormat = DecimalFormat()
        if (sex == "男") {
            return decimalFormat.format((height - 80) * 0.7)
        }
        if (sex == "女") {
            return decimalFormat.format((height - 70) * 0.6)
        }
        return ""
    }
}

@Sample(name = "3_6startActivity", description = "跳转示例", tags = ["A-Self_demos"])
class ep3_6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep3_6)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val choose = findViewById<Button>(R.id.button1)

        choose.setOnClickListener {
            val intent = Intent(this@ep3_6, ep3_6second::class.java)
            startActivityForResult(intent, 0x777)
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0x777 && resultCode == 0x888) {
            val bundle = data?.extras
            val imageId = bundle!!.getInt("imageId")
            findViewById<ImageView?>(R.id.image).setImageResource(imageId)
        }
    }
}

class ep3_6second : AppCompatActivity() {
    var image: IntArray = intArrayOf(
        R.drawable.img_badminton,
        R.drawable.img_baseball,
        R.drawable.img_bowling,
        R.drawable.img_golf,
        R.drawable.icon_swimming
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep3_6second)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val gridView = findViewById<GridView>(R.id.gridview)
        val baseAdapter: BaseAdapter =
            object : BaseAdapter() {
                override fun getCount(): Int {
                    return image.size
                }

                override fun getItem(position: Int): Any {
                    return position
                }

                override fun getItemId(position: Int): Long {
                    return position.toLong()
                }

                override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                    val imageView: ImageView
                    if (convertView == null) {
                        imageView = ImageView(this@ep3_6second)
                        imageView.scaleType = FIT_XY
                        imageView.setPadding(5, 0, 5, 0)
                        val layoutParams =
                            LayoutParams(200, 200)
                        imageView.layoutParams = layoutParams
                    } else {
                        imageView = convertView as ImageView
                    }
                    imageView.setImageResource(image[position])
                    return imageView
                }
            }
        gridView.adapter = baseAdapter

        gridView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val intent = intent
                val bundle = Bundle()
                bundle.putInt("imageId", image[position])
                intent.putExtras(bundle)
                setResult(0x888, intent)
                finish()
            }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "Fragment", description = "fragment示例", tags = ["A-Self_demos"])
class ep3_7 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep3_7)
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

    class DetailActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.ep3_7_detail)
            if (supportActionBar != null) {
                supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
                supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
            }

            // 如果为横屏，结束当前Activity，准备使用Fragment显示内容
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                finish() // 结束当前Activity
                return
            }
            if (savedInstanceState == null) {
                // 如果不是横屏就插入显示详细内容的Fragment
                val detailFragment = DetailFragment()
                detailFragment.arguments = intent.extras
                // 设置要传递的参数
                fragmentManager
                    .beginTransaction()
                    .add(android.R.id.content, detailFragment)
                    .commit() // 添加一个显示详细内容的Fragment
            }
        }

        // 处理返回键事件
        override fun onSupportNavigateUp(): Boolean {
            onBackPressed() // 或者你可以 finish() 当前 Activity
            return true
        }
    }
}

class ListFragment : android.app.ListFragment() {
    private var dualPane: Boolean = false // 是否在一屏上同时显示列表和详细内容
    private var curCheckPosition: Int = 0 // 当前选择的索引位置

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 为列表设置适配器
        listAdapter = ArrayAdapter(
            activity, android.R.layout.simple_list_item_checked, Data.TITLES
        )
        // 得到布局文件中添加的帧布局管理器
        val detail = activity.findViewById<View>(R.id.detail)
        // 判断是否在一屏上同时显示列表和详细内容
        dualPane = detail != null && detail.visibility == View.VISIBLE
        if (savedInstanceState != null) {
            // 更新当前索引的位置
            curCheckPosition = savedInstanceState.getInt("curChoice", 0)
        }
        if (dualPane) { // 在同一屏上同时显示列表和详细内容
            // 如果是横屏，则将list设置成单选模式
            listView.choiceMode = ListView.CHOICE_MODE_SINGLE
            showDetails(curCheckPosition) // 显示详细内容
        }
    }

    // 重写方法，保存当前选中的索引值
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("curChoice", curCheckPosition)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        showDetails(position) // 调用方法显示详细内容
    }

    private fun showDetails(aIndex: Int) {
        curCheckPosition = aIndex // 更新保存当前索引位置的变量的值为当前选中值
        // 如果是横屏状态
        if (dualPane) {
            listView.setItemChecked(aIndex, true) // 设置选中列表项为选中状态
            var details: DetailFragment? =
                fragmentManager
                    .findFragmentById(R.id.detail) as DetailFragment // 获取用于显示详细内容的Fragment
            // 如果 details的fragment为空或者是它的当前显示的索引不等于选中的索引
            if (details == null || details.shownIndex != aIndex) {
                // 创建一个detailFragment的实例对象，用于显示当前选择项对应的详细内容
                details = DetailFragment.newInstance(aIndex)
                // 要在Activity中管理fragment，需要使用FragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                // 将这个实例化对象替换到原先的内容
                fragmentTransaction.replace(R.id.detail, details)
                // 设置转换效果
                fragmentTransaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction.commit() // 提交事务
            }
        } else { // 竖屏就进行跳转（在一屏上只能显示列表或详细内容中的一个内容时，使用一个新的Activity显示详细内容）
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("aIndex", aIndex) // 设置一个要传递的参数
            startActivity(intent) // 开启一个指定的activity
        }
    }
}

class DetailFragment : android.app.Fragment() {
    val shownIndex: Int
        get() = arguments.getInt("index", 0) // 获取要显示的列表项索引

    override fun onCreateView(
        inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (container == null) {
            return null
        }
        val scroller = ScrollView(activity) // 创建一个滚动视图
        val text = TextView(activity) // 创建一个文本框对象
        text.setPadding(10, 10, 10, 10) // 设置内边距
        scroller.addView(text) // 将文本框对象添加到滚动视图中
        text.text = Data.DETAIL[shownIndex] // 设置文本框中要显示的文本
        return scroller
    }

    companion object {
        // 创建一个DetailFragment的新实例，其中包括要传递的数据包
        fun newInstance(index: Int): DetailFragment {
            val f = DetailFragment()
            // 将index作为一个参数传递
            val bundle = Bundle() // 实例化一个Bundle对象
            bundle.putInt("index", index) // 将索引值添加到Bundle对象中
            f.arguments = bundle // 将bundle对象作为Fragment的参数保存
            return f
        }
    }
}

object Data {
    // 标题
    val TITLES: Array<String> = arrayOf("Java", "C", "Python", "PHP")
    val DETAIL: Array<String> = arrayOf(
        "Java是一门面向对象编程语言",
        "C语言是一门面向过程的、抽象化的通用程序设计语言。",
        "Python提供了高效的高级数据结构，还能简单有效地面向对象编程。Python语法和动态类型,以及解释型语言的本质，"
            + "使它成为多数平台上写脚本和快速开发应用的编程语言。",
        "PHP即“超文本预处理器”&，是在服务器端执行的脚本语言。"
    )
}
