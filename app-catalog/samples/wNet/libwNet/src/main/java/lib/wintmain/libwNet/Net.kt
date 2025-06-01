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

@file:Suppress("unused", "FunctionName") @file:JvmName("NetKt")

package lib.wintmain.libwNet

import android.util.Log
import lib.wintmain.libwNet.interfaces.ProgressListener
import lib.wintmain.libwNet.request.BodyRequest
import lib.wintmain.libwNet.request.Method
import lib.wintmain.libwNet.request.UrlRequest
import lib.wintmain.libwNet.request.downloadListeners
import lib.wintmain.libwNet.request.group
import lib.wintmain.libwNet.request.id
import lib.wintmain.libwNet.request.uploadListeners
import okhttp3.Request

object Net {

    //<editor-fold desc="同步请求">
    /**
     * 同步网络请求
     *
     * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
     * @param tag 可以传递对象给Request请求, 一般用于在拦截器/转换器中进行针对某个接口行为判断
     * @param block 函数中可以配置请求参数
     */
    @JvmOverloads
    @JvmStatic
    fun get(
        path: String,
        tag: Any? = null,
        block: (UrlRequest.() -> Unit)? = null
    ) = UrlRequest().apply {
        setPath(path)
        method = Method.GET
        tag(tag)
        block?.invoke(this)
    }

    /**
     * 同步网络请求
     *
     * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
     * @param tag 可以传递对象给Request请求, 一般用于在拦截器/转换器中进行针对某个接口行为判断
     * @param block 函数中可以配置请求参数
     */
    @JvmOverloads
    @JvmStatic
    fun post(
        path: String,
        tag: Any? = null,
        block: (BodyRequest.() -> Unit)? = null
    ) = BodyRequest().apply {
        setPath(path)
        method = Method.POST
        tag(tag)
        block?.invoke(this)
    }

    /**
     * 同步网络请求
     *
     * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
     * @param tag 可以传递对象给Request请求, 一般用于在拦截器/转换器中进行针对某个接口行为判断
     * @param block 函数中可以配置请求参数
     */
    @JvmOverloads
    @JvmStatic
    fun head(
        path: String,
        tag: Any? = null,
        block: (UrlRequest.() -> Unit)? = null
    ) = UrlRequest().apply {
        setPath(path)
        method = Method.HEAD
        tag(tag)
        block?.invoke(this)
    }

    /**
     * 同步网络请求
     *
     * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
     * @param tag 可以传递对象给Request请求, 一般用于在拦截器/转换器中进行针对某个接口行为判断
     * @param block 函数中可以配置请求参数
     */
    @JvmOverloads
    @JvmStatic
    fun options(
        path: String,
        tag: Any? = null,
        block: (UrlRequest.() -> Unit)? = null
    ) = UrlRequest().apply {
        setPath(path)
        method = Method.OPTIONS
        tag(tag)
        block?.invoke(this)
    }

    /**
     * 同步网络请求
     *
     * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
     * @param tag 可以传递对象给Request请求, 一般用于在拦截器/转换器中进行针对某个接口行为判断
     * @param block 函数中可以配置请求参数
     */
    @JvmOverloads
    @JvmStatic
    fun trace(
        path: String,
        tag: Any? = null,
        block: (UrlRequest.() -> Unit)? = null
    ) = UrlRequest().apply {
        setPath(path)
        method = Method.TRACE
        tag(tag)
        block?.invoke(this)
    }

    /**
     * 同步网络请求
     *
     * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
     * @param tag 可以传递对象给Request请求, 一般用于在拦截器/转换器中进行针对某个接口行为判断
     * @param block 函数中可以配置请求参数
     */
    @JvmOverloads
    @JvmStatic
    fun delete(
        path: String,
        tag: Any? = null,
        block: (BodyRequest.() -> Unit)? = null
    ) = BodyRequest().apply {
        setPath(path)
        method = Method.DELETE
        tag(tag)
        block?.invoke(this)
    }

    /**
     * 同步网络请求
     *
     * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
     * @param tag 可以传递对象给Request请求, 一般用于在拦截器/转换器中进行针对某个接口行为判断
     * @param block 函数中可以配置请求参数
     */
    @JvmOverloads
    @JvmStatic
    fun put(
        path: String,
        tag: Any? = null,
        block: (BodyRequest.() -> Unit)? = null
    ) = BodyRequest().apply {
        setPath(path)
        method = Method.PUT
        tag(tag)
        block?.invoke(this)
    }

    /**
     * 同步网络请求
     *
     * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
     * @param tag 可以传递对象给Request请求, 一般用于在拦截器/转换器中进行针对某个接口行为判断
     * @param block 函数中可以配置请求参数
     */
    @JvmOverloads
    @JvmStatic
    fun patch(
        path: String,
        tag: Any? = null,
        block: (BodyRequest.() -> Unit)? = null
    ) = BodyRequest().apply {
        setPath(path)
        method = Method.PATCH
        tag(tag)
        block?.invoke(this)
    }
    //</editor-fold>

    //<editor-fold desc="取消请求">
    /**
     * 取消全部网络请求
     */
    @JvmStatic
    fun cancelAll() {
        NetConfig.okHttpClient.dispatcher.cancelAll()
        val iterator = NetConfig.runningCalls.iterator()
        while (iterator.hasNext()) {
            iterator.next().get()?.cancel()
            iterator.remove()
        }
    }

    /**
     * 取消指定的网络请求, Id理论上是唯一的, 所以该函数一次只能取消一个请求
     * @return 如果成功取消返回true
     * @see lib.wintmain.libwNet.request.BaseRequest.setId
     */
    @JvmStatic
    fun cancelId(id: Any?): Boolean {
        if (id == null) return false
        val iterator = NetConfig.runningCalls.iterator()
        while (iterator.hasNext()) {
            val call = iterator.next().get()
            if (call == null) {
                iterator.remove()
                continue
            }
            if (id == call.request().id) {
                call.cancel()
                iterator.remove()
                return true
            }
        }
        return false
    }

    /**
     * 根据分组取消网络请求
     * @return 如果成功取消返回true, 无论取消个数
     * @see lib.wintmain.libwNet.request.BaseRequest.setGroup
     */
    @JvmStatic
    fun cancelGroup(group: Any?): Boolean {
        if (group == null) return false
        val iterator = NetConfig.runningCalls.iterator()
        var hasCancel = false
        while (iterator.hasNext()) {
            val call = iterator.next().get()
            if (call == null) {
                iterator.remove()
                continue
            }
            if (group == call.request().group) {
                call.cancel()
                iterator.remove()
                hasCancel = true
            }
        }
        return hasCancel
    }
    //</editor-fold>

    //<editor-fold desc="进度监听">
    /**
     * 监听正在请求的上传进度
     * @param id 请求的Id
     * @see lib.wintmain.libwNet.request.BaseRequest.setId
     */
    @JvmStatic
    fun addUploadListener(id: Any, progressListener: ProgressListener): Boolean {
        getRequestById(id)?.let { request ->
            request.uploadListeners().add(progressListener)
            return true
        }
        return false
    }

    /**
     * 删除正在请求的上传进度监听器
     * @param id 请求的Id
     * @see lib.wintmain.libwNet.request.BaseRequest.setId
     */
    @JvmStatic
    fun removeUploadListener(id: Any, progressListener: ProgressListener): Boolean {
        getRequestById(id)?.let { request ->
            request.uploadListeners().remove(progressListener)
            return true
        }
        return false
    }

    /**
     * 监听正在请求的下载进度
     * @param id 请求的Id
     * @see lib.wintmain.libwNet.request.BaseRequest.setId
     */
    @JvmStatic
    fun addDownloadListener(id: Any, progressListener: ProgressListener): Boolean {
        getRequestById(id)?.let { request ->
            request.downloadListeners().add(progressListener)
            return true
        }
        return false
    }

    /**
     * 删除正在请求的下载进度监听器
     *
     * @param id 请求的Id
     * @see lib.wintmain.libwNet.request.BaseRequest.setId
     */
    @JvmStatic
    fun removeDownloadListener(id: Any, progressListener: ProgressListener): Boolean {
        getRequestById(id)?.let { request ->
            request.downloadListeners().remove(progressListener)
            return true
        }
        return false
    }

    //</editor-fold>

    //<editor-fold desc="获取请求">

    /**
     * 根据ID获取请求对象
     * @see lib.wintmain.libwNet.request.BaseRequest.setId
     */
    @JvmStatic
    fun getRequestById(id: Any): Request? {
        val iterator = NetConfig.runningCalls.iterator()
        while (iterator.hasNext()) {
            val call = iterator.next().get()
            if (call == null) {
                iterator.remove()
                continue
            }
            val request = call.request()
            if (id == request.id) {
                return request
            }
        }
        return null
    }

    /**
     * 根据Group获取请求对象
     * @see lib.wintmain.libwNet.request.BaseRequest.setGroup
     */
    @JvmStatic
    fun getRequestByGroup(group: Any): MutableList<Request> {
        val requests = mutableListOf<Request>()
        val iterator = NetConfig.runningCalls.iterator()
        while (iterator.hasNext()) {
            val call = iterator.next().get()
            if (call == null) {
                iterator.remove()
                continue
            }
            val request = call.request()
            if (group == request.group) {
                requests.add(request)
            }
        }
        return requests
    }
    //</editor-fold>

    //<editor-fold desc="日志">
    /**
     * 输出异常日志
     * @param message 如果非[Throwable]则会自动追加代码位置(文件:行号)
     * @see NetConfig.debug
     */
    @JvmStatic
    fun debug(message: Any) {
        if (NetConfig.debug) {
            val adjustMessage = if (message is Throwable) {
                message.stackTraceToString()
            } else {
                val occurred =
                    Throwable().stackTrace.getOrNull(1)?.run { " (${fileName}:${lineNumber})" }
                        ?: ""
                message.toString() + occurred
            }
            Log.d(NetConfig.TAG, adjustMessage)
        }
    }
    //</editor-fold>
}
