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

import android.os.SystemClock
import lib.wintmain.libwNet.component.Progress
import lib.wintmain.libwNet.interfaces.ProgressListener
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.internal.closeQuietly
import okio.*
import java.io.IOException
import java.util.concurrent.ConcurrentLinkedQueue

class NetRequestBody(
    private val body: RequestBody,
    private val progressListeners: ConcurrentLinkedQueue<ProgressListener>? = null
) : RequestBody() {

    private val progress = Progress()
    private val contentLength by lazy { body.contentLength() }

    override fun contentType(): MediaType? {
        return body.contentType()
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return contentLength
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        if (sink is Buffer ||
            sink.toString()
                .contains("com.android.tools.profiler.support.network.HttpTracker\$OutputStreamTracker")
        ) {
            body.writeTo(sink)
        } else {
            val bufferedSink: BufferedSink = sink.toProgress().buffer()
            body.writeTo(bufferedSink)
            bufferedSink.closeQuietly()
            if (contentLength == -1L) {
                progressListeners?.forEach { progressListener ->
                    progressListener.onProgress(
                        progress.apply {
                            finish = true
                        }
                    )
                }
            }
        }
    }

    private fun Sink.toProgress() = object : ForwardingSink(this) {
        private var writeByteCount = 0L

        @Throws(IOException::class)
        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            if (!progressListeners.isNullOrEmpty()) {
                writeByteCount += byteCount
                val currentElapsedTime = SystemClock.elapsedRealtime()
                progressListeners.forEach { progressListener ->
                    progressListener.intervalByteCount += byteCount
                    val currentInterval = currentElapsedTime - progressListener.elapsedTime
                    if (!progress.finish && (writeByteCount == contentLength || currentInterval >= progressListener.interval)) {
                        if (writeByteCount == contentLength) {
                            progress.finish = true
                        }
                        progressListener.onProgress(
                            progress.apply {
                                currentByteCount = writeByteCount
                                totalByteCount = contentLength
                                intervalByteCount = progressListener.intervalByteCount
                                intervalTime = currentInterval
                            }
                        )
                        progressListener.elapsedTime = currentElapsedTime
                        progressListener.intervalByteCount = 0L
                    }
                }
            }
        }
    }
}