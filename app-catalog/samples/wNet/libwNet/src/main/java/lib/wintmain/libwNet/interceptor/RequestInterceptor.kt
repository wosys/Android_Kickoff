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

package lib.wintmain.libwNet.interceptor

import lib.wintmain.libwNet.request.BaseRequest

interface RequestInterceptor {

    /**
     * 当你使用Net发起请求的时候就会触发该拦截器
     * 该拦截器属于轻量级不具备重发的功能, 一般用于请求参数的修改
     * 请勿在这里进行请求重发可能会导致死循环
     */
    fun interceptor(request: BaseRequest)
}