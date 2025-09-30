/*
 * Copyright 2023-2025 wintmain
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

package com.wintmain.wBasis.aidl

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.catalog.framework.annotations.Sample
import lib.wintmain.wToaster.toast.ToastUtils

@Sample(name = "AIDL-SampleActivity", description = "AIDL简单示例", tags = ["A-Self_demos"])
class AidlSampleActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "AidlSampleActivity"
    }

    var peopleManager: IPeopleManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 初始化 Toast
        ToastUtils.init(this.application)

        // 页面打开的时候就开始绑定服务
        val intent = Intent(this, PeopleRemoteService::class.java)
        bindService(intent, mConnection, BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        // 移除监听
        peopleManager?.let { manager ->
            if (manager.asBinder().isBinderAlive) {
                try {
                    manager.unregisterListener(mNewPeopleListener)
                } catch (e: RemoteException) {
                    Log.d(TAG, "catch：" + e.message)
                }
            }
        }
        unbindService(mConnection)
        super.onDestroy()
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.d(TAG, "onServiceConnected...")
            peopleManager = IPeopleManager.Stub.asInterface(service)
            peopleManager?.run {
                try {
                    val people = People(1000, "wintmain")
                    val book = Book(1, "Book")
                    // 注册监听
                    registerListener(mNewPeopleListener)
                    addPeople(people)
                    addPeopleAndBook(people, book)
                } catch (e: RemoteException) {
                    Log.d(TAG, "catch：" + e.message)
                }
            }
        }

        override fun onServiceDisconnected(className: ComponentName) {
            Log.d(TAG, "onServiceDisconnected!!!")
            ToastUtils.show("onServiceDisconnected!!!")
            peopleManager = null
        }
    }

    // 定义监听接口
    private val mNewPeopleListener: INewPeopleListener = object : INewPeopleListener.Stub() {
        override fun onNewPeople(newPeople: People) {
            ToastUtils.show("onNewPeople: $newPeople")
        }
    }
}