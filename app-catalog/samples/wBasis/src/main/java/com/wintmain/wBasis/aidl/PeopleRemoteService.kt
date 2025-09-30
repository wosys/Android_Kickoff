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

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.RemoteCallbackList
import android.util.Log
import java.util.concurrent.CopyOnWriteArrayList

// 服务端代码，客户端见[AidlSampleActivity.java]
class PeopleRemoteService : Service() {
    val mPeopleList = CopyOnWriteArrayList<People>()

    // 客户端注册和移除注册过程中使用的虽是同一个客户端对象，
    // 但通过Binder传递到服务端后，产生了两个不同的对象。
    // 因为对象是不能跨进程直接传输的，对象的跨进程传输本质上都是反序列化的过程
    val mListenList = RemoteCallbackList<INewPeopleListener>()

    private val mBinder: Binder = object : IPeopleManager.Stub() {
        override fun getPeopleList(): List<People> {
            return mPeopleList
        }

        override fun addPeople(people: People) {
            mPeopleList.add(people)

            // 扩展新增代码：新加对象后执行监听回调
            for (i in 0 until mListenList.beginBroadcast()) {
                val listener: INewPeopleListener = mListenList.getBroadcastItem(i)
                listener.onNewPeople(people)
            }
            mListenList.finishBroadcast()
        }

        override fun addPeopleAndBook(people: People, book: Book) {
            Log.d("wintmain", "addPeopleAndBook: people = $people, book = $book")
        }

        // RemoteCallbackList是系统专门提供的用于删除跨进程listener接口，它是一个泛型，支持管理任意AIDL接口。
        // 另外它内部自动实现了线程同步的功能。
        // beginBroadcast和finishBroadcast必须要配对使用，哪怕仅仅获取RemoteCallbackList中的元素个数
        override fun registerListener(listener: INewPeopleListener) {
            mListenList.beginBroadcast()
            mListenList.register(listener)
            mListenList.finishBroadcast()
        }

        override fun unregisterListener(listener: INewPeopleListener) {
            mListenList.beginBroadcast()
            mListenList.unregister(listener)
            mListenList.finishBroadcast()
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }
}