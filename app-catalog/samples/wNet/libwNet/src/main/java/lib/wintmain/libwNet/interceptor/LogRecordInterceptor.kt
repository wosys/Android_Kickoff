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

import lib.wintmain.libwNet.body.name
import lib.wintmain.libwNet.body.peekBytes
import lib.wintmain.libwNet.body.value
import lib.wintmain.libwNet.log.LogRecorder
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.Response

/**
 * 网络日志记录器
 * 可以参考此拦截器为项目中其他网络请求库配置. 本拦截器属于标准的OkHttp拦截器适用于所有OkHttp拦截器内核的网络请求库
 *
 * 在正式环境下请禁用此日志记录器. 因为他会消耗少量网络速度
 *
 * @property enabled 是否启用日志输出
 * @property requestByteCount 请求日志输出字节数, -1 则为全部
 * @property responseByteCount 响应日志输出字节数, -1 则为全部
 */
open class LogRecordInterceptor @JvmOverloads constructor(
    var enabled: Boolean,
    var requestByteCount: Long = 1024 * 1024,
    var responseByteCount: Long = 1024 * 1024 * 4
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!enabled) {
            return chain.proceed(request)
        }

        val generateId = LogRecorder.generateId()
        LogRecorder.recordRequest(
            generateId,
            request.url.toString(),
            request.method,
            request.headers.toMultimap(),
            getRequestLog(request)
        )
        try {
            val response = chain.proceed(request)
            LogRecorder.recordResponse(
                generateId,
                System.currentTimeMillis(),
                response.code,
                response.headers.toMultimap(),
                getResponseLog(response)
            )
            return response
        } catch (e: Exception) {
            val error = "Review LogCat for details, occurred exception: ${e.javaClass.simpleName}"
            LogRecorder.recordException(generateId, System.currentTimeMillis(), -1, null, error)
            throw e
        }
    }

    /**
     * 请求字符串
     */
    protected open fun getRequestLog(request: Request): String? {
        val body = request.body ?: return null
        val mediaType = body.contentType()
        return when {
            body is MultipartBody -> {
                body.parts.joinToString("&") {
                    "${it.name()}=${it.value()}"
                }
            }

            body is FormBody -> {
                body.peekBytes(requestByteCount).utf8()
            }

            arrayOf("plain", "json", "xml", "html").contains(mediaType?.subtype) -> {
                body.peekBytes(requestByteCount).utf8()
            }

            else -> {
                "$mediaType does not support output logs"
            }
        }
    }

    /**
     * 响应字符串
     */
    protected open fun getResponseLog(response: Response): String? {
        val body = response.body ?: return null
        val mediaType = body.contentType()
        val isPrintType = arrayOf("plain", "json", "xml", "html").contains(mediaType?.subtype)
        return if (isPrintType) {
            body.peekBytes(responseByteCount).utf8()
        } else {
            "$mediaType does not support output logs"
        }
    }
}