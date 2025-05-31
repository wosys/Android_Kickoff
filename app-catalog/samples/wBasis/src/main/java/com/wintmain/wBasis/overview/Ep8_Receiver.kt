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

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.wBasis.R

@Sample(name = "LocalBroadcastActivity", description = "广播示例", tags = ["A-Self_demos"])
class LocalBroadcastActivity : AppCompatActivity() {
    private var localBroadcastTest: LocalBroadcastTest? = null
    private var localBroadcastManager: LocalBroadcastManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button = Button(this)
        button.text = "测试本地广播"
        linearLayout.addView(button)
        // 实例化对象
        localBroadcastManager = LocalBroadcastManager.getInstance(this@LocalBroadcastActivity)
        localBroadcastTest = LocalBroadcastTest()
        // 发送广播
        button.setOnClickListener {
            val intent = Intent()
            intent.setAction("LOCAL")
            localBroadcastManager!!.sendBroadcast(intent)
        }
        // 动态注册接收器
        val intentFilter = IntentFilter()
        intentFilter.addAction("LOCAL")
        localBroadcastManager!!.registerReceiver(localBroadcastTest!!, intentFilter)
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    internal inner class LocalBroadcastTest : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context, "本地广播接收器已接受", Toast.LENGTH_SHORT).show()
        }
    }
}

@Sample(name = "广播动态注册", description = "监听网络变化示例", tags = ["A-Self_demos"])
class ep9_1 : AppCompatActivity() {
    private var networkChangeReceiver: NetworkChangeReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep9_1)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, intentFilter)
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkChangeReceiver)
    }

    internal inner class NetworkChangeReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val connectivityManager =
                getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connectivityManager.activeNetworkInfo
            if (info != null && info.isAvailable) {
                Toast.makeText(context, "网络已连接", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "网络已断开", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Sample(name = "广播", description = "发送自定义广播示例", tags = ["A-Self_demos"])
class ep9_3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ep9_3)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)

        button2.setOnClickListener {
            val intent1 = Intent()
            intent1.setAction("com.android.MY_BROADCAST")
            intent1.setComponent(
                ComponentName(
                    "com.wintmain.foundation",
                    "com.wintmain.foundation.prejob.MyBroadcastReceiver"
                )
            )
            sendBroadcast(intent1)
        }
        button1.setOnClickListener {
            val intent2 = Intent()
            intent2.setAction("com.android.MY_BROADCAST_EXT")
            intent2.setComponent(
                ComponentName(
                    "com.wintmain.foundation",
                    "com.wintmain.foundation.prejob.AnotherBroadcastReceiver"
                )
            )
            sendBroadcast(intent2)
        }
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // 接受自定义广播， action
        Toast.makeText(context, "收到aaaaaa in MyBroadcastReceiver", Toast.LENGTH_LONG).show()
    }
}

class AnotherBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "received in 其他BroadcastReceiver", Toast.LENGTH_SHORT).show()
    }
}

@Sample(name = "广播", description = "有序广播示例", tags = ["A-Self_demos"])
class ep9_4 : AppCompatActivity() {
    private var receiver1: Receiver1? = null
    private var receiver2: Receiver2? = null
    private var receiver3: Receiver3? = null
    private var receiver4: Receiver4? = null

    @RequiresApi(VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linearLayout = LinearLayout(this)
        val button = Button(this)
        button.text = "测试有序广播"
        linearLayout.addView(button)
        setContentView(linearLayout)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true) // 显示返回箭头
            supportActionBar!!.setHomeButtonEnabled(true) // 设置返回键可点击
        }

        button.setOnClickListener {
            val intent = Intent()
            intent.setAction("com.test.broadcasttest.My_TestBroadcast")
            sendOrderedBroadcast(intent, null)
        }
        // 在配置清单中静态注册广播接收者，会使接收者无法接收到广播
        receiver1 = Receiver1()
        val intentFilter1 = IntentFilter()
        intentFilter1.addAction("com.test.broadcasttest.My_TestBroadcast")
        intentFilter1.priority = 88
        registerReceiver(receiver1, intentFilter1, RECEIVER_NOT_EXPORTED)

        // 优先级比1高，则会先响应
        receiver2 = Receiver2()
        val intentFilter2 = IntentFilter()
        intentFilter2.addAction("com.test.broadcasttest.My_TestBroadcast")
        intentFilter2.priority = 100
        registerReceiver(receiver2, intentFilter2, RECEIVER_NOT_EXPORTED)

        // 3接收到后会截断，导致4接收不到
        receiver3 = Receiver3()
        val intentFilter3 = IntentFilter()
        intentFilter3.addAction("com.test.broadcasttest.My_TestBroadcast")
        registerReceiver(receiver3, intentFilter3, RECEIVER_NOT_EXPORTED)

        receiver4 = Receiver4()
        val intentFilter4 = IntentFilter()
        intentFilter4.addAction("com.test.broadcasttest.My_TestBroadcast")
        registerReceiver(receiver4, intentFilter4, RECEIVER_NOT_EXPORTED)
    }

    // 处理返回键事件
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // 或者你可以 finish() 当前 Activity
        return true
    }
}

class Receiver1 : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "接收者1接受广播", Toast.LENGTH_SHORT).show()
    }
}

class Receiver2 : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "接收者2接受广播。。。", Toast.LENGTH_LONG).show()
    }
}

class Receiver3 : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "接收者3接受广播", Toast.LENGTH_SHORT).show()
        abortBroadcast() // 截断广播
    }
}

class Receiver4 : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "接收者4接受广播", Toast.LENGTH_SHORT).show()
    }
}

class BootCompleteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "已开机", Toast.LENGTH_SHORT).show()
    }
}
