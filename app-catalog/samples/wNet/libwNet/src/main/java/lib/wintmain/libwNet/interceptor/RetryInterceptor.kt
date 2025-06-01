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

package lib.wintmain.libwNet.interceptor

import androidx.annotation.IntRange
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.closeQuietly

/**
 * 重试次数拦截器
 * OkHttp自带重试请求规则, 本拦截器并不推荐使用
 * 因为长时间阻塞用户请求是不合理的, 发生错误请让用户主动重试, 例如显示缺省页或者提示
 * @property retryCount 重试次数
 */
class RetryInterceptor(@IntRange(from = 1) var retryCount: Int = 3) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var retryCount = 0
        val request = chain.request()
        var response = chain.proceed(request)
        while (!response.isSuccessful && retryCount < this.retryCount) {
            retryCount++
            response.closeQuietly()
            response = chain.proceed(request)
        }
        return response
    }
}