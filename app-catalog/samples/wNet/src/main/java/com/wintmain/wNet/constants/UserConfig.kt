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

import com.drake.serialize.serialize.annotation.SerializeConfig
import com.drake.serialize.serialize.serialLazy

/**
 * 本单例类使用 https://github.com/liangjingkanji/Serialize 为字段提供持久化存储
 */
@SerializeConfig(mmapID = "user_config")
object UserConfig {

    var token by serialLazy(default = "6cad0ff06f5a214b9cfdf2a4a7c432339")

    var isLogin by serialLazy(default = false)
}