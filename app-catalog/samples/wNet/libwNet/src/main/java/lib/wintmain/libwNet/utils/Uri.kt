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

import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.documentfile.provider.DocumentFile
import lib.wintmain.libwNet.NetConfig
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import java.io.FileNotFoundException

fun Uri.fileName(): String? {
    return DocumentFile.fromSingleUri(NetConfig.app, this)?.name
}

fun Uri.mediaType(): MediaType? {
    val fileName = DocumentFile.fromSingleUri(NetConfig.app, this)?.name
    val fileExtension = MimeTypeMap.getFileExtensionFromUrl(fileName)
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)?.toMediaTypeOrNull()
}

/**
 * 当Uri指向的文件不存在时将抛出异常[FileNotFoundException]
 */
@Throws(FileNotFoundException::class)
fun Uri.toRequestBody(): RequestBody {
    val document = DocumentFile.fromSingleUri(NetConfig.app, this)
    val contentResolver = NetConfig.app.contentResolver
    val contentLength = document?.length() ?: -1L
    val contentType = mediaType()
    return object : RequestBody() {
        override fun contentType(): MediaType? {
            return contentType
        }

        override fun contentLength() = contentLength

        override fun writeTo(sink: BufferedSink) {
            contentResolver.openInputStream(this@toRequestBody)?.use {
                sink.writeAll(it.source())
            }
        }
    }
}