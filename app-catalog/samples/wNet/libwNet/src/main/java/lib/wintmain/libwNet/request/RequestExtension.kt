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
import lib.wintmain.libwNet.NetConfig
import lib.wintmain.libwNet.OkHttpUtils
import lib.wintmain.libwNet.convert.NetConverter
import lib.wintmain.libwNet.interfaces.ProgressListener
import lib.wintmain.libwNet.tag.NetTag
import okhttp3.Request
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.reflect.KType

//<editor-fold desc="ID">
/**
 * 请求Id
 * Group和Id在使用场景上有所区别, 预期上Group允许重复赋值给多个请求, Id仅允许赋值给一个请求, 但实际上都允许重复赋值
 * 在作用域中发起请求时会默认使用协程错误处理器作为Group: `setGroup(coroutineContext[CoroutineExceptionHandler])`
 * 如果你覆盖Group会导致协程结束不会自动取消请求
 */
var Request.id: Any?
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
var Request.group: Any?
    get() = tagOf<NetTag.RequestGroup>()?.value
    set(value) {
        tagOf(value?.let { NetTag.RequestGroup(it) })
    }
//</editor-fold>

/**
 * 为请求附着KType信息
 * KType属于Kotlin特有的Type, 某些Kotlin框架可能会使用到, 例如 kotlin.serialization
 */
var Request.kType: KType?
    get() = tagOf<NetTag.RequestKType>()?.value
    set(value) {
        tagOf(value?.let { NetTag.RequestKType(it) })
    }

//<editor-fold desc="Extra">
/**
 * 读取额外信息
 */
fun Request.extra(name: String): Any? {
    return tagOf<NetTag.Extras>()?.get(name)
}

/**
 * 全部额外信息
 */
fun Request.extras(): HashMap<String, Any?> {
    val tags = tags()
    return tags[NetTag.Extras::class.java] as NetTag.Extras? ?: kotlin.run {
        val tag = NetTag.Extras()
        tags[NetTag.Extras::class.java] = tag
        tag
    }
}
//</editor-fold>

//<editor-fold desc="Tag">
/**
 * 读取OkHttp的tag(通过Class区分的tag)
 */
inline fun <reified T> Request.tagOf(): T? {
    return tag(T::class.java)
}

/**
 * 设置OkHttp的tag(通过Class区分的tag)
 */
inline fun <reified T> Request.tagOf(value: T?) = apply {
    if (value == null) {
        tags().remove(T::class.java)
    } else {
        tags()[T::class.java] = value
    }
}

/**
 * 全部tag
 */
fun Request.tags(): MutableMap<Class<*>, Any?> {
    return OkHttpUtils.tags(this)
}

//</editor-fold>

//<editor-fold desc="Progress">
/**
 * 全部的上传监听器
 */
fun Request.uploadListeners(): ConcurrentLinkedQueue<ProgressListener> {
    return tagOf<NetTag.UploadListeners>() ?: kotlin.run {
        val tag = NetTag.UploadListeners()
        tagOf(tag)
        tag
    }
}

/**
 * 全部的下载监听器
 */
fun Request.downloadListeners(): ConcurrentLinkedQueue<ProgressListener> {
    return tagOf<NetTag.DownloadListeners>() ?: kotlin.run {
        val tag = NetTag.DownloadListeners()
        tagOf(tag)
        tag
    }
}

//</editor-fold>

//<editor-fold desc="Download">
/**
 * 下载文件路径存在同名文件时是覆盖或创建新文件(添加序号)
 * 重命名规则是: $文件名_($序号).$后缀, 例如`file_name(1).apk`
 */
fun Request.downloadConflictRename(): Boolean {
    return tagOf<NetTag.DownloadFileConflictRename>()?.value == true
}

/**
 * 下载文件MD5校验
 * 如果服务器响应头`Content-MD5`值和指定路径已经存在的文件MD5相同, 则跳过下载直接返回该File
 */
fun Request.downloadMd5Verify(): Boolean {
    return tagOf<NetTag.DownloadFileMD5Verify>()?.value == true
}

/**
 * 下载文件目录
 */
fun Request.downloadFileDir(): String {
    return tagOf<NetTag.DownloadFileDir>()?.value ?: NetConfig.app.filesDir.absolutePath
}

/**
 * 下载文件名
 */
fun Request.downloadFileName(): String? {
    return tagOf<NetTag.DownloadFileName>()?.value
}

/**
 * 下载的文件名称是否解码
 * 例如下载的文件名如果是中文, 服务器传输给你的会是被URL编码的字符串. 你使用URL解码后才是可读的中文名称
 */
fun Request.downloadFileNameDecode(): Boolean {
    return tagOf<NetTag.DownloadFileNameDecode>()?.value == true
}

/**
 * 下载是否使用临时文件
 * 避免下载失败后覆盖同名文件或者无法判别是否已下载完整, 仅在下载完整以后才会显示为原有文件名
 * 临时文件命名规则: 文件名 + .downloading
 *      下载文件名: install.apk, 临时文件名: install.apk.downloading
 */
fun Request.downloadTempFile(): Boolean {
    return tagOf<NetTag.DownloadTempFile>()?.value == true
}
//</editor-fold>

/**
 * 返回请求包含的转换器
 */
fun Request.converter(): NetConverter {
    return tagOf<NetConverter>() ?: NetConfig.converter
}