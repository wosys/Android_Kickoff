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

package lib.wintmain.libwNet.utils

import android.webkit.MimeTypeMap
import lib.wintmain.libwNet.Net
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import okio.ByteString.Companion.toByteString
import okio.source
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.DigestInputStream
import java.security.MessageDigest

/**
 * 返回文件的MD5值
 * @param base64 是否将md5值进行base64编码, 否则将返回hex编码
 */
fun File.md5(base64: Boolean = false): String? {
    try {
        val fileInputStream = FileInputStream(this)
        val digestInputStream = DigestInputStream(fileInputStream, MessageDigest.getInstance("MD5"))
        val buffer = ByteArray(1024 * 256)
        digestInputStream.use {
            while (true) if (digestInputStream.read(buffer) <= 0) break
        }
        val md5 = digestInputStream.messageDigest.digest()
        return if (base64) {
            md5.toByteString().base64()
        } else {
            md5.toByteString().hex()
        }
    } catch (e: IOException) {
        Net.debug(e)
    }
    return null
}

/**
 * 返回文件的MediaType值, 如果不存在返回null
 */
fun File.mediaType(): MediaType? {
    val fileExtension = MimeTypeMap.getFileExtensionFromUrl(absolutePath)
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)?.toMediaTypeOrNull()
}

/**
 * 创建File的RequestBody
 * @param contentType 如果为null则通过判断扩展名来生成MediaType
 */
fun File.toRequestBody(contentType: MediaType? = null): RequestBody {
    val fileMediaType = contentType ?: mediaType()
    return object : RequestBody() {

        override fun contentType(): MediaType? {
            return fileMediaType
        }

        override fun contentLength() = length()

        override fun writeTo(sink: BufferedSink) {
            source().use { source ->
                sink.writeAll(source)
            }
        }
    }
}