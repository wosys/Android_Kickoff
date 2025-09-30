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

import android.app.AlertDialog
import android.app.Notification.Builder
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.FrameLayout
import android.widget.GridView
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.ImageView.ScaleType.FIT_CENTER
import android.widget.ImageView.ScaleType.FIT_XY
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.wBasis.R

@Sample(name = "ArrayAdapter", description = "Adapter适配器入门", tags = ["A-Self_demos"])
class ep2_1 : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example2_1)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        // 获取自动完成文本框
        val textView =
            findViewById<View>(R.id.autoCompleteTextView1) as AutoCompleteTextView
        // 创建一个ArrayAdapter适配器
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, COUNTRIES)
        // 为自动完成文本框设置适配器
        textView.setAdapter(adapter)

        val button = findViewById<View>(R.id.button1) as Button
        button.setOnClickListener {
            Toast.makeText(
                this@ep2_1,
                textView.text.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    companion object {
        private val COUNTRIES =
            arrayOf("android", "android app", "android开发", "开发应用", "开发者")
    }
}

@Sample(name = "进度条", description = "进度条展示入门", tags = ["A-Self_demos"])
class ep2_2 : AppCompatActivity() {
    private var horizonP: ProgressBar? = null // 水平进度条
    private var circleP: ProgressBar? = null // 圆形进度条
    private var mProgressStatus = 0 // 完成进度

    // 使用主线程的 Looper 创建 Handler
    private var mHandler: Handler = Handler(Looper.getMainLooper()) {
        when (it.what) {
            0x111 -> {
                horizonP!!.progress = mProgressStatus // 更新进度
            }

            0x110 -> {
                Toast.makeText(this@ep2_2, "耗时操作已经完成", Toast.LENGTH_SHORT).show()
                horizonP!!.visibility = View.GONE // 设置进度条不显示，并且不占用空间
                circleP!!.visibility = View.GONE
            }
        }
        true // 表示消息被处理
    } // 声明一个用于处理消息的Handler类的对象

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example2_2)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        horizonP = findViewById(R.id.progressBar1) // 获取进度条
        circleP = findViewById(R.id.progressBar2)

        Thread {
            while (true) {
                mProgressStatus = doWork() // 获取耗时操作完成的百分比
                val m = Message()
                if (mProgressStatus < 100) {
                    m.what = 0x111
                    mHandler.sendMessage(m) // 发送信息
                } else {
                    m.what = 0x110
                    mHandler.sendMessage(m) // 发送消息
                    break
                }
            }
        }.start() // 开启一个线程
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    // 模拟一个耗时操作
    private fun doWork(): Int {
        mProgressStatus = (mProgressStatus + (Math.random() * 10)).toInt() // 改变完成进度
        try {
            Thread.sleep(200) // 线程休眠200毫秒
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return mProgressStatus
    }
}

@Sample(name = "SeekBar", description = "拖动条展示", tags = ["A-Self_demos"])
class ep2_3 : AppCompatActivity() {
    private var seekbar: SeekBar? = null // 拖动条

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example2_3)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val result = findViewById<View>(R.id.textView1) as TextView // 获取文本视图
        seekbar = findViewById<View>(R.id.seekBar1) as SeekBar // 获取拖动条
        seekbar!!.setOnSeekBarChangeListener(
            object : OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    Toast.makeText(this@ep2_3, "结束滑动", Toast.LENGTH_SHORT).show()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    Toast.makeText(this@ep2_3, "开始滑动", Toast.LENGTH_SHORT).show()
                }

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    result.text = "当前值: $progress" // 修改文本视图的值
                }
            })
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "RatingBar", description = "星级评分条展示", tags = ["A-Self_demos"])
class ep2_4 : AppCompatActivity() {
    private var ratingbar: RatingBar? = null // 星级评分条

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example2_4)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        ratingbar = findViewById<View>(R.id.ratingBar1) as RatingBar // 获取星级评分条
        val button = findViewById<View>(R.id.button1) as Button // 获取“提交”按钮
        button.setOnClickListener {
            val result = ratingbar!!.progress // 获取进度
            val rating = ratingbar!!.rating // 获取等级
            val step = ratingbar!!.stepSize // 获取每次最少要改变多少个星级
            Log.i(
                "星级评分条",
                "step=$step result=$result rating=$rating"
            )
            Toast.makeText(
                this@ep2_4,
                "你得到了" + rating + "颗星",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Deprecated("通过添加上面的suppress允许调用")
@Sample(name = "TabHost", description = "选项卡展示", tags = ["A-Self_demos"])
class ep2_5 : AppCompatActivity() {
    private var tabHost: android.widget.TabHost? = null // 选项卡对象

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example2_5)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        tabHost = findViewById<View>(android.R.id.tabhost) as android.widget.TabHost // 获取TabHost对象
        tabHost!!.setup() // 初始化TabHost组件

        val inflater = LayoutInflater.from(this) // 声明并实例化一个LayoutInflater对象
        inflater.inflate(R.layout.tab1, tabHost!!.tabContentView)
        inflater.inflate(R.layout.tab2, tabHost!!.tabContentView)
        tabHost!!.addTab(
            tabHost!!.newTabSpec("tab01")
                .setIndicator("智能设备")
                .setContent(R.id.linearlayout01)
        ) // 添加第一个标签页
        tabHost!!.addTab(
            tabHost!!.newTabSpec("tab02")
                .setIndicator("智慧城市")
                .setContent(R.id.linearlayout02)
        ) // 添加第二个标签页
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "ImageSwitcher", description = "图像切换器展示", tags = ["A-Self_demos"])
class ep2_6 : AppCompatActivity() {
    private val imageId = intArrayOf(
        R.drawable.icon_badminton,
        R.drawable.icon_baseball,
        R.drawable.icon_bowling,
        R.drawable.icon_cycling,
        R.drawable.icon_golf,
        R.drawable.icon_running
    ) // 声明并初始化一个保存要显示图像id的数组
    private var index = 0 // 当前显示图像的索引
    private var imageSwitcher: ImageSwitcher? = null // 声明一个ImageSwitcher对象

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example2_6)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        imageSwitcher = findViewById<View>(R.id.imageSwitcher1) as ImageSwitcher // 获取图像切换器
        // 设置动画效果
        imageSwitcher!!.inAnimation =
            AnimationUtils.loadAnimation(this, android.R.anim.fade_in) // 设置淡入动画
        imageSwitcher!!.outAnimation =
            AnimationUtils.loadAnimation(this, android.R.anim.fade_out) // 设置淡出动画
        imageSwitcher!!.setFactory {
            val imageView = ImageView(this@ep2_6) // 实例化一个ImageView对象
            imageView.scaleType = FIT_CENTER // 设置保持纵横比居中缩放图像
            imageView.layoutParams = FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            imageView // 返回对象
        }
        imageSwitcher!!.setImageResource(imageId[index]) // 显示默认的图片

        val up = findViewById<View>(R.id.button1) as Button // 获取”上一张"按钮
        val down = findViewById<View>(R.id.button2) as Button // 获取"下- -张"按钮
        up.setOnClickListener {
            if (index > 0) {
                index-- // index的值减1
            } else {
                index = imageId.size - 1
            }
            imageSwitcher!!.setImageResource(imageId[index])
        }
        down.setOnClickListener {
            if (index < imageId.size - 1) {
                index++ // index的值加1
            } else {
                index = 0
            }
            imageSwitcher!!.setImageResource(imageId[index]) // 显示当前图片
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "GridView", description = "网格视图展示", tags = ["A-Self_demos"])
class ep2_7 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example2_7)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val gridview = findViewById<View>(R.id.gridView1) as GridView // 获取GridView组件
        val imageId = intArrayOf(
            R.drawable.icon_cycling,
            R.drawable.icon_running,
            R.drawable.icon_golf,
            R.drawable.icon_bowling,
            R.drawable.icon_badminton,
            R.drawable.icon_soccer
        ) // 定义并初始化保存图片id的数组
        val title = arrayOf(
            "智能手机", "运营商认证", "自动化测试", "系统升级", "硬件设计", "Camera一站式"
        ) // 定义并初始化保存说明文字的数组
        val listItems: MutableList<Map<String, Any?>> = ArrayList() // 创建一个List集合
        // 通过for循环将图片id和列表项文字放到Map中并添加到List集合中
        for (i in imageId.indices) {
            val map: MutableMap<String, Any?> = HashMap()
            map["image"] = imageId[i]
            map["title"] = title[i]
            listItems.add(map) // 将map对象添加到List集合中
        }

        val adapter = SimpleAdapter(
            this,
            listItems,
            R.layout.items,
            arrayOf("title", "image"),
            intArrayOf(R.id.title, R.id.image)
        ) // 创建适配器
        gridview.adapter = adapter // 将适配器与GridView关联
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "Gallery", description = "画廊展示", tags = ["A-Self_demos"])
class ep2_8 : AppCompatActivity() {
    private val imageId = intArrayOf(
        R.drawable.icon_cycling,
        R.drawable.icon_running,
        R.drawable.icon_golf,
        R.drawable.icon_bowling,
        R.drawable.icon_badminton,
        R.drawable.icon_soccer
    ) // 定义并初始化保存图片id的数组

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example2_8)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val gallery = findViewById<View>(R.id.gallery1) as android.widget.Gallery

        val adapter: BaseAdapter =
            object : BaseAdapter() {
                // 获取数量
                override fun getCount(): Int {
                    return imageId.size
                }

                // 获得当前选项
                override fun getItem(position: Int): Any {
                    return position
                }

                // 获得当前选项的ID值
                override fun getItemId(position: Int): Long {
                    return position.toLong()
                }

                override fun getView(position: Int, convertview: View?, parent: ViewGroup?): View {
                    val imageView: ImageView // 声明对象
                    if (convertview == null) {
                        imageView = ImageView(this@ep2_8) // 实例化对象
                        imageView.scaleType = FIT_XY // 设置缩放方式
                        imageView.layoutParams = android.widget.Gallery.LayoutParams(300, 300)
                        val typedArray = obtainStyledAttributes(R.styleable.Gallery)
                        imageView.setBackgroundResource(
                            typedArray.getResourceId(
                                R.styleable.Gallery_android_galleryItemBackground, 0
                            )
                        )
                        imageView.setPadding(5, 0, 5, 0) // 设置ImageView的内边距
                    } else {
                        imageView = convertview as ImageView
                    }
                    imageView.setImageResource(imageId[position])
                    return imageView
                }
            }

        gallery.adapter = adapter // 将适配器与Gallery关联
        gallery.setSelection(imageId.size / 2) // 选中中间的图片
        gallery.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                Toast.makeText(
                    this@ep2_8,
                    "您选择了第" + position.toString() + "张图片",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "GalleryEXt", description = "画廊展示", tags = ["A-Self_demos"])
class ep2_10 : AppCompatActivity() {
    private val imageId = intArrayOf(
        R.drawable.icon_cycling,
        R.drawable.icon_running,
        R.drawable.icon_golf,
        R.drawable.icon_bowling,
        R.drawable.icon_badminton,
        R.drawable.icon_soccer
    ) // 定义并初始化保存图片id的数组
    private var imageSwitcher: ImageSwitcher? = null // 声明一个图像切换器对象

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example2_10)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val gallery = findViewById<View>(R.id.galleryy10) as android.widget.Gallery // 获取Gallery组件
        imageSwitcher = findViewById<View>(R.id.imageSwitcherr10) as ImageSwitcher

        // 设置动画效果
        // 设置淡入动画
        imageSwitcher!!.inAnimation =
            AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        // 设置淡出动画
        imageSwitcher!!.outAnimation =
            AnimationUtils.loadAnimation(this, android.R.anim.fade_out)
        imageSwitcher!!.setFactory {
            val imageView = ImageView(this@ep2_10) // 实例化一个类的对象
            imageView.scaleType = FIT_CENTER // 设置保持纵横比居中缩放图像
            imageView.layoutParams = FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            imageView // 返回的是一个imageView对象
        }

        val adapter: BaseAdapter =
            object : BaseAdapter() {
                override fun getCount(): Int {
                    return imageId.size
                }

                override fun getItem(i: Int): Any {
                    return i
                }

                override fun getItemId(i: Int): Long {
                    return i.toLong()
                }

                override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                    val imageview: ImageView // 声明ImageView的对象
                    if (convertView == null) {
                        imageview = ImageView(this@ep2_10) // 实例化ImageView的对象
                        imageview.scaleType = FIT_XY // 设置缩放方式
                        imageview.layoutParams = android.widget.Gallery.LayoutParams(300, 300)
                        val typedArray = obtainStyledAttributes(R.styleable.Gallery)
                        imageview.setBackgroundResource(
                            typedArray.getResourceId(
                                R.styleable.Gallery_android_galleryItemBackground, 0
                            )
                        )
                        imageview.setPadding(5, 0, 5, 0) // 设置ImageView的内边距
                    } else {
                        imageview = convertView as ImageView
                    }
                    imageview.setImageResource(imageId[position]) // 为ImageView设置要显示的图片
                    return imageview // 返回ImageView
                }
            }

        gallery.adapter = adapter // 关联适配器
        gallery.setSelection(imageId.size / 2) // 选中中间的图片
        gallery.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?, view: View, position: Int, l: Long
            ) {
                imageSwitcher!!.setImageResource(imageId[position]) // 显示选中的照片
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "Toast", description = "吐司展示", tags = ["A-Self_demos"])
class ep2_11 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example2_11)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        Toast.makeText(this, "Toast的第一种创建方式，通过makeText()", Toast.LENGTH_SHORT).show()

        val toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT // 设置持续时间
        toast.setGravity(Gravity.CENTER, 0, 0) // 设置对齐方式

        val ll = LinearLayout(this) // 创建一个线性布局管理器
        val imageView = ImageView(this) // 创建一个ImageView对象
        imageView.setImageResource(R.drawable.icon_soccer) // 设置要显示的图片
        imageView.setPadding(0, 0, 5, 0) // 设置imageView的内边距
        ll.addView(imageView) // 将imageView添加到线性布局管理器中
        val textView = TextView(this) // 创建一个TextView对象
        textView.text = "Toaset的第二种创建方式，通过构造方法"
        ll.addView(textView)

        toast.view = ll
        toast.show() // 显示消息提示框
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "Notification Demo", description = "通知展示", tags = ["A-Self_demos"])
class ep2_12 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button = Button(this)
        button.text = "生成通知"
        linearLayout.addView(button)

        // 获取通知管理器,用于发送通知
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // 自定义通道id属性
        val id = "channel_1"
        // 自定义通道描述属性
        val description = "channel_description"
        val NOTIFY_ID_1 = 0x1
        val NOTIFY_ID_2 = 0x2
        // 通知栏管理重要提示消息声音设定
        val importance = NotificationManager.IMPORTANCE_HIGH

        button.setOnClickListener { // 建立通知通道类
            val channel = NotificationChannel(id, "123", importance)
            // 设置描述属性
            channel.description = description
            // 开启闪光灯
            channel.enableLights(true)
            // 开启震动
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            // 管理器创建该通知渠道
            manager.createNotificationChannel(channel)
            // 创建notification对象
            val builder = Builder(this@ep2_12, id)
                // 标题
                .setContentTitle("通知标题1")
                // 小图标
                .setSmallIcon(R.drawable.icon_baseball)
                // 大图标
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.icon_baseball))
                // 内容
                .setContentText("通知栏内容1")
                // 设置自动删除通知
                .setAutoCancel(true)
                // 构建
                .build()
            // 保留多行通知,需要唯一编号
            manager.notify(NOTIFY_ID_1, builder)

            val intent = Intent(this@ep2_12, NotificationActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(
                    this@ep2_12, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            val builder2 = Builder(this@ep2_12, id)
                .setContentTitle("通知标题2") // 设置通知标题
                .setSmallIcon(R.drawable.icon_soccer)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        resources, R.drawable.icon_soccer
                    )
                )
                .setContentText("通知栏内容2")
                .setAutoCancel(true) // 设置自动删除图标
                .setContentIntent(pendingIntent)
                .build() // 运行
            manager.notify(NOTIFY_ID_2, builder2)
        }

        val button2 = Button(this)
        button2.text = "清除通知"
        linearLayout.addView(button2)
        button2.setOnClickListener {
            manager.cancel(NOTIFY_ID_1) // 清除ID号为常量的通知
            manager.cancelAll() // 清除全部通知
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val tv = TextView(this)
        linearLayout.addView(tv)
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "AlertDialog", description = "弹框展示", tags = ["A-Self_demos"])
class ep2_13 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button1 = Button(this)
        val button2 = Button(this)
        val button3 = Button(this)
        val button4 = Button(this)
        button1.text = "按钮对话框"
        button2.text = "列表对话框"
        button3.text = "列表+按钮对话框"
        button4.text = "多选+按钮对话框"
        linearLayout.addView(button1)
        linearLayout.addView(button2)
        linearLayout.addView(button3)
        linearLayout.addView(button4)

        button1.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this@ep2_13).create()
            alertDialog.setIcon(R.drawable.icon_swimming)
            alertDialog.setTitle("提示一：")
            alertDialog.setMessage("取消、中立和确定")
            // 消极的
            alertDialog.setButton(
                DialogInterface.BUTTON_NEGATIVE,
                "取消"
            ) { _, _ -> Log.i("对话框", "点击了取消") }
            // 积极的
            alertDialog.setButton(
                DialogInterface.BUTTON_POSITIVE,
                "确定"
            ) { _, _ -> Log.i("对话框", "点击了确定") }
            // 中立
            alertDialog.setButton(
                DialogInterface.BUTTON_NEUTRAL,
                "中立"
            ) { _, _ -> Log.i("对话框", "点击了中立") }
            alertDialog.show()
        }

        button2.setOnClickListener {
            val strings = arrayOf("第一条", "第二条", "第三条", "第四条")
            val alertDialog = AlertDialog.Builder(this@ep2_13)
            alertDialog.setTitle("提示二：")
            alertDialog.setItems(
                strings
            ) { _, which -> Log.i("对话框", "onClick: " + strings[which]) }
            alertDialog.create().show()
        }
        button3.setOnClickListener {
            val strings = arrayOf("第一条", "第二条", "第三条", "第四条")
            val alertDialog = AlertDialog.Builder(this@ep2_13)
            alertDialog.setTitle("提示三：")
            alertDialog.setSingleChoiceItems(
                strings,
                0
            ) { _, which -> Log.i("对话框", "onClick: " + strings[which]) }
            alertDialog.setPositiveButton("确定", null)
            alertDialog.create().show()
        }
        button4.setOnClickListener {
            val items = booleanArrayOf(false, false, true, true)
            val strings = arrayOf("第一条", "第二条", "第三条", "第四条")
            val alertDialog = AlertDialog.Builder(this@ep2_13)
            alertDialog.setTitle("提示三：")
            alertDialog.setMultiChoiceItems(
                strings,
                items
            ) { _, which, isChecked -> items[which] = isChecked }
            alertDialog.setPositiveButton(
                "确定"
            ) { _, _ ->
                var result = ""
                for (i in items.indices) {
                    if (items[i]) {
                        result += strings[i] + "、"
                    }
                }
                if ("" != result) {
                    result = result.substring(0, result.length - 1)
                    Log.i("对话框", result)
                }
            }
            alertDialog.create().show()
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "AlertDialogExt", description = "弹框展示", tags = ["A-Self_demos"])
class ep2_14 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button = Button(this)
        button.text = "点击退出"
        linearLayout.addView(button)

        button.setOnClickListener {
            val builder = AlertDialog.Builder(this@ep2_14)
            builder.setIcon(R.drawable.icon_soccer)
            builder.setTitle("是否退出？")
            builder.setMessage("是否真的要退出码？")
            builder.setNegativeButton(
                "取消"
            ) { _, _ -> }
            builder.setPositiveButton(
                "确定"
            ) { _, _ -> finish() }
            builder.create().show()
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

@Sample(name = "AlertDialogExt", description = "弹框展示", tags = ["A-Self_demos"])
class ep2_15 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example2_15)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        Toast.makeText(this, "ep2_15 make...", Toast.LENGTH_LONG).show()
        // 定义并初始化保存图片id的数组
        val imageId = intArrayOf(
            R.drawable.icon_cycling,
            R.drawable.icon_running,
            R.drawable.icon_golf,
            R.drawable.icon_bowling,
            R.drawable.icon_badminton
        )
        // 定义并初始化保存列表项文字的数组
        val title = arrayOf("程序管理", "保密设置", "安全设置", "邮件设置", "铃声设置")
        val listItems: MutableList<Map<String, Any?>> = java.util.ArrayList() // 创建一个List集合
        // 通过for循环将图片id和列表项文字放到Map中,并添加到List集合中
        for (i in imageId.indices) {
            val map: MutableMap<String, Any?> = java.util.HashMap() // 实例化map对象
            map["image"] = imageId[i]
            map["title"] = title[i]
            listItems.add(map) // 将map对象添加到List集合中
            val adapter = SimpleAdapter(
                this,
                listItems,
                R.layout.items,
                arrayOf("title", "image"),
                intArrayOf(R.id.title, R.id.image)
            ) // 创建SimpleAdapter

            val button1 = findViewById<View>(R.id.button) as Button
            button1.setOnClickListener {
                val builder = AlertDialog.Builder(this@ep2_15)
                builder.setIcon(R.drawable.rabbit)
                builder.setTitle("设置：")
                builder.setAdapter(adapter) { _, i ->
                    Toast.makeText(
                        this@ep2_15,
                        "你选择了【" + title[i] + "】",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                builder.create().show()
            }
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}
