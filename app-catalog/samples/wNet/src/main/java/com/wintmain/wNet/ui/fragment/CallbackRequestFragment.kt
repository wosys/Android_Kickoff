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
import com.wintmain.wNet.databinding.FragmentCallbackRequestBinding
import lib.wintmain.libwNet.Net
import lib.wintmain.libwNet.utils.runMain
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class CallbackRequestFragment :
    EngineFragment<FragmentCallbackRequestBinding>(R.layout.fragment_callback_request) {

    override fun initData() {
    }

    override fun initView() {
        // Net同样支持OkHttp原始的队列任务
        Net.post(com.wintmain.wNet.constants.Api.TEXT).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                // 此处为子线程
                val body = response.body?.string() ?: "无数据"
                runMain {
                    // 此处为主线程
                    binding.tvFragment.text = body
                }
            }
        })
    }
}