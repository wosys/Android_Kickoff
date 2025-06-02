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

import android.util.Log
import com.drake.engine.base.EngineFragment
import com.wintmain.wNet.R
import com.wintmain.wNet.databinding.FragmentUniqueRequestBinding
import lib.wintmain.libwNet.Post
import lib.wintmain.libwNet.scope.AndroidScope
import lib.wintmain.libwNet.utils.scopeNetLife

class UniqueRequestFragment :
    EngineFragment<FragmentUniqueRequestBinding>(R.layout.fragment_unique_request) {

    private var scope: AndroidScope? = null

    override fun initView() {
        binding.btnRequest.setOnClickListener {
            binding.tvResult.text = "请求中"
            scope?.cancel() // 如果存在则取消

            scope = scopeNetLife {
                val result = Post<String>(com.wintmain.wNet.constants.Api.TEXT).await()
                Log.d("日志", "请求到结果") // 你一直重复点击"发起请求"按钮会发现永远无法拿到请求结果, 因为每次发起新的请求会取消未完成的
                binding.tvResult.text = result
            }
        }
    }

    override fun initData() {
    }
}