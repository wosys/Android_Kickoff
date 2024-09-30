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

package com.wintmain.basic.prejob.uiDemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.basic.R


@Sample(
    name = "ArrayAdapter",
    description = "Adapter适配器入门",
    documentation = "",
    tags = ["A-Self_demos"],
)
class ep2_1 : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example2_1)

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

    companion object {
        private val COUNTRIES =
            arrayOf("android", "android app", "android开发", "开发应用", "开发者")
    }
}

@Sample(
    name = "进度条",
    description = "进度条展示入门",
    documentation = "",
    tags = ["A-Self_demos"],
)
class ep2_2 : AppCompatActivity() {
    private var horizonP: ProgressBar? = null // 水平进度条
    private var circleP: ProgressBar? = null // 圆形进度条
    private var mProgressStatus = 0 // 完成进度
    private var mHandler: Handler // 声明一个用于处理消息的Handler类的对象

    init {
        // 使用主线程的 Looper 创建 Handler
        mHandler = Handler(Looper.getMainLooper()) {
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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.example2_2)

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
