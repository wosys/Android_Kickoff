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

package com.wintmain.wNet.constants

/*
建议请求路径都写在一个单例类中, 方便查找和替换
*/
object Api {
    const val HOST = "http://127.0.0.1:8091"

    const val TEXT = "/text"
    const val DELAY = "/delay"
    const val UPLOAD = "/upload"
    const val GAME = "/game"
    const val DATA = "/data"
    const val ARRAY = "/array"
    const val CONFIG = "/config"
    const val USER_INFO = "/userInfo"
    const val TIME = "/time"
    const val Token = "/token"
}