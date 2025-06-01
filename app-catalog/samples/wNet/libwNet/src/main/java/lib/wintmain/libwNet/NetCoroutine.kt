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

@file:Suppress("FunctionName")

package lib.wintmain.libwNet

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.ensureActive
import lib.wintmain.libwNet.internal.NetDeferred
import lib.wintmain.libwNet.request.BodyRequest
import lib.wintmain.libwNet.request.Method.DELETE
import lib.wintmain.libwNet.request.Method.GET
import lib.wintmain.libwNet.request.Method.HEAD
import lib.wintmain.libwNet.request.Method.OPTIONS
import lib.wintmain.libwNet.request.Method.PATCH
import lib.wintmain.libwNet.request.Method.POST
import lib.wintmain.libwNet.request.Method.PUT
import lib.wintmain.libwNet.request.Method.TRACE
import lib.wintmain.libwNet.request.UrlRequest

// <editor-fold desc="异步请求">

/**
 * 异步网络请求
 *
 * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
 * @param tag 可以传递对象给Request, 一般用于在拦截器/转换器中进行针对某个接口行为判断
 * @param block 函数中可以配置请求参数
 */
inline fun <reified M> CoroutineScope.Get(
    path: String,
    tag: Any? = null,
    noinline block: (UrlRequest.() -> Unit)? = null
): Deferred<M> = NetDeferred(async(Dispatchers.IO + SupervisorJob()) {
    coroutineContext.ensureActive()
    UrlRequest().apply {
        setPath(path)
        method = GET
        setGroup(coroutineContext[CoroutineExceptionHandler])
        tag(tag)
        block?.invoke(this)
    }.execute()
})

/**
 * 异步网络请求
 *
 * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
 * @param tag 可以传递对象给Request, 一般用于在拦截器/转换器中进行针对某个接口行为判断
 * @param block 函数中可以配置请求参数
 */
inline fun <reified M> CoroutineScope.Post(
    path: String,
    tag: Any? = null,
    noinline block: (BodyRequest.() -> Unit)? = null
): Deferred<M> = NetDeferred(async(Dispatchers.IO + SupervisorJob()) {
    coroutineContext.ensureActive()
    BodyRequest().apply {
        setPath(path)
        method = POST
        setGroup(coroutineContext[CoroutineExceptionHandler])
        tag(tag)
        block?.invoke(this)
    }.execute()
})

/**
 * 异步网络请求
 *
 * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
 * @param tag 可以传递对象给Request, 一般用于在拦截器/转换器中进行针对某个接口行为判断
 * @param block 函数中可以配置请求参数
 */
inline fun <reified M> CoroutineScope.Head(
    path: String,
    tag: Any? = null,
    noinline block: (UrlRequest.() -> Unit)? = null
): Deferred<M> = NetDeferred(async(Dispatchers.IO + SupervisorJob()) {
    coroutineContext.ensureActive()
    UrlRequest().apply {
        setPath(path)
        method = HEAD
        setGroup(coroutineContext[CoroutineExceptionHandler])
        tag(tag)
        block?.invoke(this)
    }.execute()
})

/**
 * 异步网络请求
 *
 * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
 * @param tag 可以传递对象给Request, 一般用于在拦截器/转换器中进行针对某个接口行为判断
 * @param block 函数中可以配置请求参数
 */
inline fun <reified M> CoroutineScope.Options(
    path: String,
    tag: Any? = null,
    noinline block: (UrlRequest.() -> Unit)? = null
): Deferred<M> = NetDeferred(async(Dispatchers.IO + SupervisorJob()) {
    coroutineContext.ensureActive()
    UrlRequest().apply {
        setPath(path)
        method = OPTIONS
        setGroup(coroutineContext[CoroutineExceptionHandler])
        tag(tag)
        block?.invoke(this)
    }.execute()
})

/**
 * 异步网络请求
 *
 * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
 * @param tag 可以传递对象给Request, 一般用于在拦截器/转换器中进行针对某个接口行为判断
 * @param block 函数中可以配置请求参数
 */
inline fun <reified M> CoroutineScope.Trace(
    path: String,
    tag: Any? = null,
    noinline block: (UrlRequest.() -> Unit)? = null
): Deferred<M> = NetDeferred(async(Dispatchers.IO + SupervisorJob()) {
    coroutineContext.ensureActive()
    UrlRequest().apply {
        setPath(path)
        method = TRACE
        setGroup(coroutineContext[CoroutineExceptionHandler])
        tag(tag)
        block?.invoke(this)
    }.execute()
})

/**
 * 异步网络请求
 *
 * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
 * @param tag 可以传递对象给Request, 一般用于在拦截器/转换器中进行针对某个接口行为判断
 * @param block 函数中可以配置请求参数
 */
inline fun <reified M> CoroutineScope.Delete(
    path: String,
    tag: Any? = null,
    noinline block: (BodyRequest.() -> Unit)? = null
): Deferred<M> = NetDeferred(async(Dispatchers.IO + SupervisorJob()) {
    coroutineContext.ensureActive()
    BodyRequest().apply {
        setPath(path)
        method = DELETE
        setGroup(coroutineContext[CoroutineExceptionHandler])
        tag(tag)
        block?.invoke(this)
    }.execute()
})

/**
 * 异步网络请求
 *
 * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
 * @param tag 可以传递对象给Request, 一般用于在拦截器/转换器中进行针对某个接口行为判断
 * @param block 函数中可以配置请求参数
 */
inline fun <reified M> CoroutineScope.Put(
    path: String,
    tag: Any? = null,
    noinline block: (BodyRequest.() -> Unit)? = null
): Deferred<M> = NetDeferred(async(Dispatchers.IO + SupervisorJob()) {
    coroutineContext.ensureActive()
    BodyRequest().apply {
        setPath(path)
        method = PUT
        setGroup(coroutineContext[CoroutineExceptionHandler])
        tag(tag)
        block?.invoke(this)
    }.execute()
})

/**
 * 异步网络请求
 *
 * @param path 请求路径, 如果其不包含http/https则会自动拼接[NetConfig.host]
 * @param tag 可以传递对象给Request, 一般用于在拦截器/转换器中进行针对某个接口行为判断
 * @param block 函数中可以配置请求参数
 */
inline fun <reified M> CoroutineScope.Patch(
    path: String,
    tag: Any? = null,
    noinline block: (BodyRequest.() -> Unit)? = null
): Deferred<M> = NetDeferred(async(Dispatchers.IO + SupervisorJob()) {
    coroutineContext.ensureActive()
    BodyRequest().apply {
        setPath(path)
        method = PATCH
        setGroup(coroutineContext[CoroutineExceptionHandler])
        tag(tag)
        block?.invoke(this)
    }.execute()
}
)
// </editor-fold>