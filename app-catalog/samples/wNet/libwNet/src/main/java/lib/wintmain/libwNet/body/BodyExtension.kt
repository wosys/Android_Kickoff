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

package lib.wintmain.libwNet.body

import lib.wintmain.libwNet.interfaces.ProgressListener
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import okio.ByteString
import java.util.concurrent.ConcurrentLinkedQueue

fun RequestBody.toNetRequestBody(listeners: ConcurrentLinkedQueue<ProgressListener>? = null) =
    NetRequestBody(this, listeners)

fun ResponseBody.toNetResponseBody(
    listeners: ConcurrentLinkedQueue<ProgressListener>? = null, complete: (() -> Unit)? = null
) = NetResponseBody(this, listeners, complete)

/**
 * 复制一段指定长度的字符串内容
 * @param byteCount 复制的字节长度, 允许超过实际长度, 如果-1则返回完整的字符串内容
 */
fun RequestBody.peekBytes(byteCount: Long = 1024 * 1024): ByteString {
    val buffer = Buffer()
    writeTo(buffer)
    val maxSize = if (byteCount < 0) buffer.size else minOf(buffer.size, byteCount)
    return buffer.readByteString(maxSize)
}

/**
 * 复制一段指定长度的字符串内容
 * @param byteCount 复制的字节长度, 允许超过实际长度, 如果-1则返回完整的字符串内容
 */
fun ResponseBody.peekBytes(byteCount: Long = 1024 * 1024): ByteString {
    val peeked = source().peek()
    peeked.request(byteCount)
    val maxSize = if (byteCount < 0) peeked.buffer.size else minOf(byteCount, peeked.buffer.size)
    return peeked.readByteString(maxSize)
}

/**
 * 获取Content-Disposition里面的filename属性值
 * 可以此来判断是否为文件类型
 */
fun MultipartBody.Part.fileName(): String? {
    val contentDisposition = headers?.get("Content-Disposition") ?: return null
    val regex = ";\\s${"filename"}=\"(.+?)\"".toRegex()
    val matchResult = regex.find(contentDisposition)
    return matchResult?.groupValues?.getOrNull(1)
}

/**
 * 获取Content-Disposition里面的字段名称
 */
fun MultipartBody.Part.name(): String? {
    val contentDisposition = headers?.get("Content-Disposition") ?: return null
    val regex = ";\\s${"name"}=\"(.+?)\"".toRegex()
    val matchResult = regex.find(contentDisposition)
    return matchResult?.groupValues?.getOrNull(1)
}

/**
 * 将[MultipartBody.Part.body]作为字符串返回
 * 如果[MultipartBody.Part]有指定fileName那么视为文件类型将返回fileName值而不是文件内容
 */
fun MultipartBody.Part.value(): String? {
    return fileName() ?: body.peekBytes().utf8()
}