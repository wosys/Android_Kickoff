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

import lib.wintmain.libwNet.NetConfig
import lib.wintmain.libwNet.body.toNetRequestBody
import lib.wintmain.libwNet.body.toNetResponseBody
import lib.wintmain.libwNet.cache.CacheMode
import lib.wintmain.libwNet.cache.ForceCache
import lib.wintmain.libwNet.exception.HttpFailureException
import lib.wintmain.libwNet.exception.NetConnectException
import lib.wintmain.libwNet.exception.NetException
import lib.wintmain.libwNet.exception.NetSocketTimeoutException
import lib.wintmain.libwNet.exception.NetUnknownHostException
import lib.wintmain.libwNet.exception.NoCacheException
import lib.wintmain.libwNet.request.tagOf
import lib.wintmain.libwNet.tag.NetTag
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.ref.WeakReference
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Net代理OkHttp的拦截器
 */
object NetOkHttpInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val reqBody = request.body?.toNetRequestBody(request.tagOf<NetTag.UploadListeners>())
        val cache = request.tagOf<ForceCache>() ?: NetConfig.forceCache
        val cacheMode = request.tagOf<CacheMode>()
        request = request.newBuilder().apply {
            if (cache != null && cacheMode != null) {
                cacheControl(CacheControl.Builder().noCache().noStore().build())
            }
        }.method(request.method, reqBody).build()

        var response: Response? = null
        try {
            appendRunningCall(chain)
            response = if (cache != null) {
                when (cacheMode) {
                    CacheMode.READ -> cache.get(request) ?: throw NoCacheException(request)
                    CacheMode.READ_THEN_REQUEST -> cache.get(request) ?: chain.proceed(request)
                        .run {
                            cache.put(this)
                        }

                    CacheMode.REQUEST_THEN_READ -> try {
                        chain.proceed(request).run {
                            cache.put(this)
                        }
                    } catch (e: Exception) {
                        cache.get(request) ?: throw NoCacheException(request)
                    }

                    CacheMode.WRITE -> chain.proceed(request).run {
                        cache.put(this)
                    }

                    else -> chain.proceed(request)
                }
            } else {
                chain.proceed(request)
            }
            val respBody =
                response.body?.toNetResponseBody(request.tagOf<NetTag.DownloadListeners>()) {
                    removeRunningCall(chain)
                }
            response = response.newBuilder().body(respBody).build()
            return response
        } catch (e: SocketTimeoutException) {
            throw NetSocketTimeoutException(request, e.message, e)
        } catch (e: ConnectException) {
            throw NetConnectException(request, cause = e)
        } catch (e: UnknownHostException) {
            throw NetUnknownHostException(request, message = e.message)
        } catch (e: NetException) {
            throw e
        } catch (e: Throwable) {
            throw HttpFailureException(request, cause = e)
        } finally {
            if (response?.body == null) {
                removeRunningCall(chain)
            }
        }
    }

    /**
     * 将请求添加到请求队列
     */
    private fun appendRunningCall(chain: Interceptor.Chain) {
        NetConfig.runningCalls.add(WeakReference(chain.call()))
    }

    /**
     * 将请求从请求队列移除
     */
    private fun removeRunningCall(chain: Interceptor.Chain) {
        val iterator = NetConfig.runningCalls.iterator()
        while (iterator.hasNext()) {
            val call = iterator.next().get()
            if (call == null) {
                iterator.remove()
                continue
            }
            if (call == chain.call()) {
                iterator.remove()
                return
            }
        }
    }
}