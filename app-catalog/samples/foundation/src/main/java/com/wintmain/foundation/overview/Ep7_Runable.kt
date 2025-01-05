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

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.foundation.R
import java.io.IOException
import java.util.Random

@Sample(name = "ep7_1", description = "线程示例", tags = ["A-Self_demos"])
class ep7_1 : AppCompatActivity(), Runnable {
    var i: Int = 0 // 循环变量
    private var thread: Thread? = null // 声明线程对象

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button1 = Button(this)
        button1.text = "开启线程"
        val button2 = Button(this)
        button2.text = "中断线程"
        linearLayout.addView(button1)
        linearLayout.addView(button2)

        button1.setOnClickListener {
            i = 0
            thread = Thread(this@ep7_1) // 创建一个线程
            thread!!.start() // 开启线程
        }

        button2.setOnClickListener {
            if (thread != null) {
                thread!!.interrupt() // 中断线程
                thread = null
            }
            Toast.makeText(this, "提示:中断线程", Toast.LENGTH_LONG).show()
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    override fun onDestroy() {
        if (thread != null) {
            thread!!.interrupt()
            thread = null
        }
        super.onDestroy()
    }

    override fun run() {
        while (!Thread.currentThread().isInterrupted) {
            i++
            if (i % 1000 == 0) {
                Log.i("循环变量：", i.toString())
            }
        }
    }
}

@Sample(name = "ep7_2", description = "线程示例", tags = ["A-Self_demos"])
class ep7_2 : AppCompatActivity() {
    private var thread: Thread? = null
    private var mediaplayer: MediaPlayer? = null
    private var path: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button1 = Button(this)
        button1.text = "点我播放音乐"

        linearLayout.addView(button1)

        button1.setOnClickListener {
            button1.isEnabled = false // 设置按钮不可用

            // 创建一个用于播放背景音乐的线程
            thread =
                Thread {
                    try {
                        display() // 播放背景音乐
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            thread!!.start() // 开启线程
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    @Throws(IOException::class)
    private fun display() {
        if (mediaplayer != null) {
            mediaplayer!!.release() // 释放资源
        }

        //        本地播放
        //        mediaplayer = MediaPlayer.create(ep7_2.this,R.raw.xxx);

        // 网络链接
        path =
            ("http://218.205.239.34/MIGUM2.0/v1.0/content/sub/listenSong.do?toneFlag=LQ&" +
                "netType=00&copyrightId=0&contentId=600913000000560388&resourceType=2&channel=0")
        mediaplayer = MediaPlayer()
        mediaplayer!!.setDataSource(path)
        mediaplayer!!.prepare()
        mediaplayer!!.start()

        // 为MediaPlayer添加播放完成事件监听器
        mediaplayer!!.setOnCompletionListener {
            try {
                Thread.sleep(5000)
                display() // 重新播放音乐
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    // 停止播放背景音乐并释放资源
    override fun onDestroy() {
        if (mediaplayer != null) {
            mediaplayer!!.stop() // 停止播放
            mediaplayer!!.release() // 释放资源
            mediaplayer = null
        }
        if (thread != null) {
            thread = null
        }
        super.onDestroy()
    }
}

@Sample(name = "ep7_3", description = "线程示例", tags = ["A-Self_demos"])
class ep7_3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val thread: LooperThread = LooperThread()
        thread.start()
    }

    internal inner class LooperThread : Thread() {
        var handler1: Handler? = null // 声明一个对象

        override fun run() {
            super.run()
            Looper.prepare() // 初始化Looper对象
            handler1 = Handler1()
            val m = (handler1 as Handler).obtainMessage() // 获取一个消息
            m.what = 0x11 // 设置Message的what属性的值
            (handler1 as Handler).sendMessage(m) // 发送消息
            Looper.loop() // 启动looper
        }
    }

    class Handler1 : Handler() {
        override fun handleMessage(msg: Message) {
            Log.i("Wintmain_Looper", msg.what.toString())
        }
    }
}

@Sample(name = "ep7_6", description = "线程示例", tags = ["A-Self_demos"])
class ep7_6 : AppCompatActivity() {
    var bgcolor: IntArray = intArrayOf(
        R.color.color1,
        R.color.color2,
        R.color.color3,
        R.color.color4,
        R.color.color5,
        R.color.color6,
        R.color.color7
    )
    private var handler: Handler? = null // 创建Handler对象
    private var index = 0 // 当前颜色值

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep7_6)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        // 整体布局
        val linearLayout = findViewById<LinearLayout>(R.id.line1)

        val height = this.resources.displayMetrics.heightPixels // 获取屏幕的高度
        for (i in tv.indices) {
            tv[i] = TextView(this) // 创建一个文本框对象
            tv[i]!!.width = this.resources.displayMetrics.widthPixels // 设置文本框的宽度
            tv[i]!!.height = height / tv.size // 设置文本框的高度
            linearLayout.addView(tv[i]) // 将TextView组件添加到线性布局管理器中
        }

        val t =
            Thread {
                while (!Thread.currentThread().isInterrupted) {
                    val m = handler!!.obtainMessage() // 获取一个Message
                    m.what = 0x101 // 设置消息标识
                    handler!!.sendMessage(m) // 发送消息
                    try {
                        Thread.sleep(Random().nextInt(1000).toLong()) // 休眠1秒钟
                    } catch (e: InterruptedException) {
                        e.printStackTrace() // 输出异常信息
                    }
                }
            }
        t.start() // 开启线程

        handler =
            object : Handler() {
                override fun handleMessage(msg: Message) {
                    var temp = 0 // 临时变量
                    if (msg.what == 0x101) {
                        for (i in tv.indices) {
                            temp = Random().nextInt(bgcolor.size) // 产生一个随机数
                            // 去掉重复的并且相邻的颜色
                            if (index == temp) {
                                temp++
                                if (temp == bgcolor.size) {
                                    temp = 0
                                }
                            }
                            index = temp
                            // 为文本框设置背景
                            tv[i]!!.setBackgroundColor(resources.getColor(bgcolor[index]))
                        }
                    }
                    super.handleMessage(msg)
                }
            }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    companion object {
        var tv: Array<TextView?> = arrayOfNulls(14) // TextView数组
    }
}
