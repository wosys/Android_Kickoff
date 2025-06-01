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

import android.content.Context
import androidx.startup.Initializer
import lib.wintmain.libwNet.NetConfig

/**
 * 使用AppStartup默认初始化[NetConfig.app], 仅应用主进程下有效
 * 在其他进程使用Net请手动在Application中初始化[NetConfig.initialize]
 */
internal class NetInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        NetConfig.app = context
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}