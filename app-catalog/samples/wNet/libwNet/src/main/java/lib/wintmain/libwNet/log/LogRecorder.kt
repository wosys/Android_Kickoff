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

package lib.wintmain.libwNet.log

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import android.os.Process
import android.util.Log
import lib.wintmain.libwNet.Net
import lib.wintmain.libwNet.log.MessageType.REQUEST_BODY
import lib.wintmain.libwNet.log.MessageType.REQUEST_HEADER
import lib.wintmain.libwNet.log.MessageType.REQUEST_METHOD
import lib.wintmain.libwNet.log.MessageType.REQUEST_TIME
import lib.wintmain.libwNet.log.MessageType.REQUEST_URL
import lib.wintmain.libwNet.log.MessageType.RESPONSE_BODY
import lib.wintmain.libwNet.log.MessageType.RESPONSE_END
import lib.wintmain.libwNet.log.MessageType.RESPONSE_ERROR
import lib.wintmain.libwNet.log.MessageType.RESPONSE_HEADER
import lib.wintmain.libwNet.log.MessageType.RESPONSE_STATUS
import lib.wintmain.libwNet.log.MessageType.RESPONSE_TIME
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.atomic.AtomicLong

/**
 * 日志记录器
 */
object LogRecorder {

    /** 是否启用日志记录器 */
    @JvmStatic
    var enabled = true

    private val handler by lazy {
        val handlerThread = HandlerThread("OkHttpProfiler", Process.THREAD_PRIORITY_BACKGROUND)
        handlerThread.start()
        LogBodyHandler(handlerThread.looper)
    }

    private const val LOG_LENGTH = 4000
    private const val SLOW_DOWN_PARTS_AFTER = 20
    private const val LOG_PREFIX = "OKPRFL"
    private const val DELIMITER = "_"
    private const val HEADER_DELIMITER = ':'
    private const val SPACE = ' '
    private const val KEY_TAG = "TAG"
    private const val KEY_VALUE = "VALUE"
    private const val KEY_PARTS_COUNT = "PARTS_COUNT"
    private val format: DateFormat = SimpleDateFormat("ddhhmmssSSS", Locale.US)
    private val previousTime = AtomicLong()

    /**
     * 产生一个唯一的基于时间戳Id
     */
    @JvmStatic
    @Synchronized
    fun generateId(): String {
        if (!enabled) return ""
        var currentTime: Long = format.format(Date()).toLong()
        var previousTime: Long = previousTime.get()
        if (currentTime <= previousTime) {
            currentTime = ++previousTime
        }
        LogRecorder.previousTime.set(currentTime)
        return currentTime.toString(Character.MAX_RADIX)
    }

    /**
     * 发送请求信息到记录器中
     *
     * @param id 请求的唯一标识符
     * @param url 请求URL地址
     * @param method 请求方法
     * @param headers 请求头
     * @param body 请求体
     */
    @JvmStatic
    fun recordRequest(
        id: String,
        url: String,
        method: String,
        headers: Map<String, List<String>>,
        body: String?
    ) {
        if (!enabled) return
        fastLog(id, REQUEST_METHOD, method)
        fastLog(id, REQUEST_URL, url)
        fastLog(id, REQUEST_TIME, System.currentTimeMillis().toString())

        for ((key, value) in headers) {
            var header = value.toString()
            if (header.length > 2) header = header.substring(1, header.length - 1)
            fastLog(id, REQUEST_HEADER, key + HEADER_DELIMITER + SPACE + header)
        }
        largeLog(id, REQUEST_BODY, body)
    }

    /**
     * 发送响应信息到记录器中
     *
     * @param id 请求的唯一标识符
     * @param code 响应码
     * @param headers 响应头
     * @param body 响应体
     */
    @JvmStatic
    fun recordResponse(
        id: String,
        requestMillis: Long,
        code: Int,
        headers: Map<String, List<String>>,
        body: String?
    ) {
        if (!enabled) return
        largeLog(id, RESPONSE_BODY, body)
        logWithHandler(id, RESPONSE_STATUS, code.toString(), 0)
        for ((key, value) in headers) {
            var header = value.toString()
            if (header.length > 2) header = header.substring(1, header.length - 1)
            logWithHandler(id, RESPONSE_HEADER, key + HEADER_DELIMITER + header, 0)
        }
        logWithHandler(
            id,
            RESPONSE_TIME,
            (System.currentTimeMillis() - requestMillis).toString(),
            0
        )
        logWithHandler(id, RESPONSE_END, "-->", 0)
    }

    /**
     * 发送请求异常到记录器
     *
     * @param id 请求的唯一标识符
     * @param requestMillis 请求时间毫秒值
     * @param error 错误信息, 如果存在\n换行符, 仅接受最后一行
     */
    @JvmStatic
    fun recordException(
        id: String,
        requestMillis: Long,
        code: Int?,
        response: String?,
        error: String?
    ) {
        if (!enabled) return
        largeLog(id, RESPONSE_BODY, response)
        logWithHandler(id, RESPONSE_STATUS, code.toString(), 0)
        logWithHandler(id, RESPONSE_ERROR, error, 0)
        logWithHandler(
            id,
            RESPONSE_TIME,
            (System.currentTimeMillis() - requestMillis).toString(),
            0
        )
        logWithHandler(id, RESPONSE_END, "-->", 0)
    }

    @SuppressLint("LogNotTimber")
    private fun fastLog(id: String, type: MessageType, message: String?) {
        val tag = LOG_PREFIX + DELIMITER + id + DELIMITER + type.type
        if (message != null) {
            Log.v(tag, message)
        }
    }

    private fun logWithHandler(id: String, type: MessageType, message: String?, partsCount: Int) {
        message ?: return
        val handlerMessage = handler.obtainMessage()
        val tag = LOG_PREFIX + DELIMITER + id + DELIMITER + type.type
        val bundle = Bundle()
        bundle.putString(KEY_TAG, tag)
        bundle.putString(KEY_VALUE, message)
        bundle.putInt(KEY_PARTS_COUNT, partsCount)
        handlerMessage.data = bundle
        handler.sendMessage(handlerMessage)
    }

    private fun largeLog(id: String, type: MessageType, content: String?) {
        content ?: return
        val contentLength = content.length
        if (contentLength > LOG_LENGTH) {
            val parts = contentLength / LOG_LENGTH
            for (i in 0..parts) {
                val start = i * LOG_LENGTH
                var end = start + LOG_LENGTH
                if (end > contentLength) {
                    end = contentLength
                }
                logWithHandler(id, type, content.substring(start, end), parts)
            }
        } else {
            logWithHandler(id, type, content, 0)
        }
    }

    private class LogBodyHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            val bundle = msg.data
            if (bundle != null) {
                val partsCount = bundle.getInt(KEY_PARTS_COUNT, 0)
                if (partsCount > SLOW_DOWN_PARTS_AFTER) {
                    try {
                        Thread.sleep(5L)
                    } catch (e: InterruptedException) {
                        Net.debug(e)
                    }
                }
                val data = bundle.getString(KEY_VALUE) ?: "null"
                val key = bundle.getString(KEY_TAG)
                Log.v(key, data)
            }
        }
    }
}