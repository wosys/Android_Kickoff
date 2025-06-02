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

package com.wintmain.wNet.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import lib.wintmain.libwNet.Get
import lib.wintmain.libwNet.scopeNetLife

/**
 * 不要将请求结果抛来抛去, 增加代码复杂度
 */
class UserViewModel : ViewModel() {

    // 用户信息
    var userInfo: MutableLiveData<String> = MutableLiveData()

    /**
     * 使用LiveData接受请求结果, 将该liveData直接使用DataBinding绑定到页面上, 会在请求成功自动更新视图
     */
    fun fetchUserInfo() = scopeNetLife {
        userInfo.value = Get<String>(com.wintmain.wNet.constants.Api.GAME).await()
    }

    /**
     * 开始非阻塞异步任务
     *  返回Deferred, 调用await()才会返回结果
     */
    fun fetchList(scope: CoroutineScope) = scope.Get<String>(com.wintmain.wNet.constants.Api.TEXT)

    /**
     * 开始阻塞异步任务
     * 直接返回结果
     */
    suspend fun fetchPrecessData() = coroutineScope {
        val response = Get<String>(com.wintmain.wNet.constants.Api.TEXT).await()
        response + "处理数据"
    }
}