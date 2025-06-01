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

package lib.wintmain.libwNet.internal

import kotlinx.coroutines.Deferred
import lib.wintmain.libwNet.exception.NetException
import lib.wintmain.libwNet.exception.URLParseException

@PublishedApi
internal class NetDeferred<M>(private val deferred: Deferred<M>) : Deferred<M> by deferred {

    override suspend fun await(): M {
        // 追踪到网络请求异常发生位置
        val occurred =
            Throwable().stackTrace.getOrNull(1)?.run { " ...(${fileName}:${lineNumber})" }
        return try {
            deferred.await()
        } catch (e: Exception) {
            when {
                occurred != null && e is NetException -> e.occurred = occurred
                occurred != null && e is URLParseException -> e.occurred = occurred
            }
            throw e
        }
    }
}