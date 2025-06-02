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

package com.wintmain.wNet.ui.fragment

import com.drake.engine.base.EngineFragment
import com.wintmain.wNet.R
import com.wintmain.wNet.databinding.FragmentSwitchDispatcherBinding
import kotlinx.coroutines.launch
import lib.wintmain.libwNet.utils.scopeLife
import lib.wintmain.libwNet.utils.withIO
import lib.wintmain.libwNet.utils.withMain

class SwitchDispatcherFragment :
    EngineFragment<FragmentSwitchDispatcherBinding>(R.layout.fragment_switch_dispatcher) {

    override fun initView() {
        scopeLife {

            // 点击函数名查看更多相关函数
            launch {
                val data = withMain {
                    "异步调度器切换到主线程"
                }
            }

            val data = withIO {
                "主线程切换到IO调度器"
            }
        }
    }

    override fun initData() {
    }
}
