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

package com.wintmain.wNet.interceptor

import com.wintmain.wNet.constants.UserConfig
import com.wintmain.wNet.model.TokenModel
import lib.wintmain.libwNet.Net
import lib.wintmain.libwNet.exception.ResponseException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 客户端token自动续期示例
 */
class RefreshTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        return synchronized(RefreshTokenInterceptor::class.java) {
            if (response.code == 401 && UserConfig.isLogin && !request.url.encodedPath.contains(com.wintmain.wNet.constants.Api.Token)) {
                val tokenInfo = Net.get(com.wintmain.wNet.constants.Api.Token)
                    .execute<TokenModel>() // 同步请求token
                if (tokenInfo.isExpired) {
                    // token过期抛出异常, 由全局错误处理器处理, 在其中可以跳转到登陆界面提示用户重新登陆
                    throw ResponseException(response, "登录状态失效")
                } else {
                    UserConfig.token = tokenInfo.token
                }
                chain.proceed(request)
            } else {
                response
            }
        }
    }
}