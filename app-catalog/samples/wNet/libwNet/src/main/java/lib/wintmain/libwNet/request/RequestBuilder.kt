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

package lib.wintmain.libwNet.request

import kotlinx.coroutines.CoroutineExceptionHandler
import lib.wintmain.libwNet.OkHttpUtils
import lib.wintmain.libwNet.convert.NetConverter
import lib.wintmain.libwNet.interfaces.ProgressListener
import lib.wintmain.libwNet.tag.NetTag
import okhttp3.Headers
import okhttp3.Request
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.reflect.KType

//<editor-fold desc="Group">
/**
 * 请求Id
 * Group和Id在使用场景上有所区别, 预期上Group允许重复赋值给多个请求, Id仅允许赋值给一个请求, 但实际上都允许重复赋值
 * 在作用域中发起请求时会默认使用协程错误处理器作为Group: `setGroup(coroutineContext[CoroutineExceptionHandler])`
 * 如果你覆盖Group会导致协程结束不会自动取消请求
 */
var Request.Builder.id: Any?
    get() = tagOf<NetTag.RequestId>()?.value
    set(value) {
        tagOf(value?.let { NetTag.RequestId(it) })
    }

/**
 * 请求分组
 * Group和Id在使用场景上有所区别, 预期上Group允许重复赋值给多个请求, Id仅允许赋值给一个请求, 但实际上都允许重复赋值
 * 在作用域中发起请求时会默认使用协程错误处理器作为Group: `setGroup(coroutineContext[CoroutineExceptionHandler])`
 * 如果你覆盖Group会导致协程结束不会自动取消请求
 */
var Request.Builder.group: Any?
    get() = tagOf<NetTag.RequestGroup>()?.value
    set(value) {
        tagOf(value?.let { NetTag.RequestGroup(it) })
    }
//</editor-fold>

/**
 * 为请求附着KType信息
 * KType属于Kotlin特有的Type, 某些Kotlin框架可能会使用到, 例如 kotlin.serialization
 */
var Request.Builder.kType: KType?
    get() = tagOf<NetTag.RequestKType>()?.value
    set(value) {
        tagOf(value?.let { NetTag.RequestKType(it) })
    }

/**
 * 全部的请求头
 */
fun Request.Builder.headers(): Headers.Builder {
    return OkHttpUtils.headers(this)
}
//</editor-fold>

//<editor-fold desc="Extra">
/**
 * 设置额外信息
 * @see extra 读取
 * @see extras 全部额外信息
 */
fun Request.Builder.setExtra(name: String, value: Any?) = apply {
    val extras = extras()
    if (value == null) {
        extras.remove(name)
    } else {
        extras[name] = value
    }
}

/**
 * 全部额外信息
 */
fun Request.Builder.extras(): HashMap<String, Any?> {
    return tagOf<NetTag.Extras>() ?: kotlin.run {
        val tag = NetTag.Extras()
        tagOf(tag)
        tag
    }
}

//</editor-fold>

//<editor-fold desc="Tag">

/**
 * 读取OkHttp的tag(通过Class区分的tag)
 */
inline fun <reified T> Request.Builder.tagOf(): T? {
    return tags()[T::class.java] as? T
}

/**
 * 设置OkHttp的tag(通过Class区分的tag)
 */
inline fun <reified T> Request.Builder.tagOf(value: T?) = apply {
    tag(T::class.java, value)
}

/**
 * 全部tag
 */
fun Request.Builder.tags(): MutableMap<Class<*>, Any?> {
    return OkHttpUtils.tags(this)
}
//</editor-fold>

//<editor-fold desc="Progress">
/**
 * 全部的上传监听器
 */
fun Request.Builder.uploadListeners(): ConcurrentLinkedQueue<ProgressListener> {
    return tagOf<NetTag.UploadListeners>() ?: kotlin.run {
        val tag = NetTag.UploadListeners()
        tagOf(tag)
        tag
    }
}

/**
 * 全部的下载监听器
 */
fun Request.Builder.downloadListeners(): ConcurrentLinkedQueue<ProgressListener> {
    return tagOf<NetTag.DownloadListeners>() ?: kotlin.run {
        val tag = NetTag.DownloadListeners()
        tagOf(tag)
        tag
    }
}
//</editor-fold>

/**
 * 设置转换器
 */
fun Request.Builder.setConverter(converter: NetConverter) = apply {
    tag(NetConverter::class.java, converter)
}