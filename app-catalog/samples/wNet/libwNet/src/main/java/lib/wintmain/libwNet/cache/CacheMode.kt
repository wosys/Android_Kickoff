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

package lib.wintmain.libwNet.cache

enum class CacheMode {

    /** 只读取缓存, 本操作并不会请求网络故不存在写入缓存 */
    READ,

    /** 只请求网络, 强制写入缓存 */
    WRITE,

    /** 先从缓存读取，如果失败再从网络读取, 强制写入缓存 */
    READ_THEN_REQUEST,

    /** 先从网络读取，如果失败再从缓存读取, 强制写入缓存 */
    REQUEST_THEN_READ,
}