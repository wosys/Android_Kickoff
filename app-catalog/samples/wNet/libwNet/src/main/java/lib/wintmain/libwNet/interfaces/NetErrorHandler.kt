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

package lib.wintmain.libwNet.interfaces

import android.view.View
import lib.wintmain.libwNet.Net
import lib.wintmain.libwNet.NetConfig
import lib.wintmain.libwNet.R
import lib.wintmain.libwNet.exception.ConvertException
import lib.wintmain.libwNet.exception.DownloadFileException
import lib.wintmain.libwNet.exception.HttpFailureException
import lib.wintmain.libwNet.exception.NetConnectException
import lib.wintmain.libwNet.exception.NetException
import lib.wintmain.libwNet.exception.NetSocketTimeoutException
import lib.wintmain.libwNet.exception.NoCacheException
import lib.wintmain.libwNet.exception.RequestParamsException
import lib.wintmain.libwNet.exception.ResponseException
import lib.wintmain.libwNet.exception.ServerResponseException
import lib.wintmain.libwNet.exception.URLParseException
import lib.wintmain.libwNet.utils.TipUtils
import java.net.UnknownHostException

interface NetErrorHandler {

    companion object DEFAULT : NetErrorHandler

    /**
     * 全局的网络错误处理
     *
     * @param e 发生的错误
     */
    fun onError(e: Throwable) {
        val message = when (e) {
            is UnknownHostException -> NetConfig.app.getString(R.string.net_host_error)
            is URLParseException -> NetConfig.app.getString(R.string.net_url_error)
            is NetConnectException -> NetConfig.app.getString(R.string.net_connect_error)
            is NetSocketTimeoutException -> NetConfig.app.getString(
                R.string.net_connect_timeout_error,
                e.message
            )

            is DownloadFileException -> NetConfig.app.getString(R.string.net_download_error)
            is ConvertException -> NetConfig.app.getString(R.string.net_parse_error)
            is RequestParamsException -> NetConfig.app.getString(R.string.net_request_error)
            is ServerResponseException -> NetConfig.app.getString(R.string.net_server_error)
            is NullPointerException -> NetConfig.app.getString(R.string.net_null_error)
            is NoCacheException -> NetConfig.app.getString(R.string.net_no_cache_error)
            is ResponseException -> e.message
            is HttpFailureException -> NetConfig.app.getString(R.string.request_failure)
            is NetException -> NetConfig.app.getString(R.string.net_error)
            else -> NetConfig.app.getString(R.string.net_other_error)
        }

        Net.debug(e)
        TipUtils.toast(message)
    }

    /**
     * 当你使用包含缺省页功能的作用域中发生错误将回调本函数处理错误
     *
     * @param e 发生的错误
     * @param view 缺省页, StateLayout或者PageRefreshLayout
     */
    fun onStateError(e: Throwable, view: View) {
        when (e) {
            is ConvertException,
            is RequestParamsException,
            is ResponseException,
            is NullPointerException -> onError(e)

            else -> Net.debug(e)
        }
    }
}