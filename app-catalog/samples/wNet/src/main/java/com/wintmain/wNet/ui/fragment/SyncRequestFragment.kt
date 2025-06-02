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
import com.wintmain.wNet.databinding.FragmentSyncRequestBinding
import lib.wintmain.libwNet.Net
import kotlin.concurrent.thread

class SyncRequestFragment :
    EngineFragment<FragmentSyncRequestBinding>(R.layout.fragment_sync_request) {

    override fun initView() {
        thread { // 网络请求不允许在主线程
            val result = try {
                Net.post(com.wintmain.wNet.constants.Api.TEXT).execute<String>()
            } catch (e: Exception) { // 同步请求失败会导致崩溃要求捕获异常
                "请求错误 = ${e.message}"
            }

            // val result = Net.post(Api.BANNER).toResult<String>().getOrDefault("请求发生错误, 我这是默认值")

            binding.tvFragment?.post { // 这里用?号是避免界面被销毁依然赋值
                binding.tvFragment?.text = result  // view要求在主线程更新
            }
        }
    }

    override fun initData() {
    }
}