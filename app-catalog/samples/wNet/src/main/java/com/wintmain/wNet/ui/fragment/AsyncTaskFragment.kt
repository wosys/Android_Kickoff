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
import com.wintmain.wNet.databinding.FragmentAsyncTaskBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import lib.wintmain.libwNet.utils.scope

class AsyncTaskFragment : EngineFragment<FragmentAsyncTaskBinding>(R.layout.fragment_async_task) {

    override fun initView() {
        scope {
            binding.tvFragment.text = withContext(Dispatchers.IO) {
                delay(2000)
                "结果"
            }
        }
    }

    override fun initData() {
    }

    /**
     * 抽出异步任务为一个函数
     */
    private suspend fun withDownloadFile() = withContext(Dispatchers.IO) {
        delay(200)
        "结果"
    }

    private fun CoroutineScope.asyncDownloadFile() = async {
        "结果"
    }
}
