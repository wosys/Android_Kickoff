/*
 * Copyright 2023-2024 wintmain
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

package lib.wintmain.xplayer.base

import kotlinx.coroutines.*
import lib.wintmain.xplayer.http.ApiException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * des 基础数据层
 *
 * @param coroutineScope 注入viewModel的coroutineScope用于协程管理
 * @param errorLiveData 业务出错或者爆发异常，由errorLiveData通知视图层去处理
 */
open class BaseRepository {

    /**
     * 在协程作用域中切换至IO线程
     */
    protected suspend fun <T> withIO(block: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            block.invoke()
        }
    }

    /**
     * 捕获异常信息
     */
    @Deprecated("后期会被剔除")
    private fun getApiException(e: Throwable): ApiException {
        return when (e) {
            is UnknownHostException -> {
                ApiException("网络异常", -100)
            }

            is JSONException -> {//|| e is JsonParseException
                ApiException("数据异常", -100)
            }

            is SocketTimeoutException -> {
                ApiException("连接超时", -100)
            }

            is ConnectException -> {
                ApiException("连接错误", -100)
            }

            is HttpException -> {
                ApiException("http code ${e.code()}", -100)
            }

            is ApiException -> {
                e
            }
            /**
             * 如果协程还在运行，个别机型退出当前界面时，viewModel会通过抛出CancellationException，
             * 强行结束协程，与java中InterruptException类似，所以不必理会,只需将toast隐藏即可
             */
            is CancellationException -> {
                ApiException("", -10)
            }

            else -> {
                ApiException("未知错误", -100)
            }
        }
    }
}