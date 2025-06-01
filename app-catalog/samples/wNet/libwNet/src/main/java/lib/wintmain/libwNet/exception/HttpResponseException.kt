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

package lib.wintmain.libwNet.exception

import okhttp3.Response

/**
 * 该类表示Http请求在服务器响应成功后失败
 * @param response 响应信息
 * @param message 错误描述信息
 * @param cause 错误原因
 *
 * @see ResponseException HttpStatusCode 200...299
 * @see RequestParamsException HttpStatusCode 400...499
 * @see ServerResponseException HttpStatusCode 500...599
 */
open class HttpResponseException(
    open val response: Response,
    message: String? = null,
    cause: Throwable? = null
) : NetException(response.request, message, cause)