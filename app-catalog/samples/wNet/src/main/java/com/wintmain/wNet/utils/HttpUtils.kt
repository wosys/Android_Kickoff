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

package com.wintmain.wNet.utils

import com.wintmain.wNet.model.ConfigModel
import com.wintmain.wNet.model.UserInfoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import lib.wintmain.libwNet.Get

/**
 * 常用的请求方法建议写到一个工具类中
 */
object HttpUtils {

    /**
     * 获取配置信息
     *
     * 本方法需要再调用await()才会返回结果, 属于异步方法
     */
    fun getConfigAsync(scope: CoroutineScope) =
        scope.Get<ConfigModel>(com.wintmain.wNet.constants.Api.CONFIG)

    /**
     * 获取用户信息
     * 阻塞返回可直接返回结果
     *
     * @param userId 如果为空表示请求自身用户信息
     */
    suspend fun getUser(userId: String? = null) = coroutineScope {
        Get<UserInfoModel>(com.wintmain.wNet.constants.Api.USER_INFO) {
            param("userId", userId)
        }.await()
    }
}