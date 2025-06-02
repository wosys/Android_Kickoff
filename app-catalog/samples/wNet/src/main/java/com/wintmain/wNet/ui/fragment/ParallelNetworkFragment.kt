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
import com.wintmain.wNet.databinding.FragmentParallelNetworkBinding
import lib.wintmain.libwNet.Get
import lib.wintmain.libwNet.Post
import lib.wintmain.libwNet.Trace
import lib.wintmain.libwNet.utils.scopeNetLife

class ParallelNetworkFragment :
    EngineFragment<FragmentParallelNetworkBinding>(R.layout.fragment_parallel_network) {

    override fun initView() {
        scopeNetLife {

            // 同时发起三个请求
            val deferred = Get<String>(com.wintmain.wNet.constants.Api.TEXT)
            val deferred1 = Post<String>(com.wintmain.wNet.constants.Api.TEXT)
            val deferred2 = Trace<String>(com.wintmain.wNet.constants.Api.TEXT)

            // 同时接收三个请求数据
            deferred.await()
            deferred1.await()
            deferred2.await()
        }
    }

    override fun initData() {
    }
}
