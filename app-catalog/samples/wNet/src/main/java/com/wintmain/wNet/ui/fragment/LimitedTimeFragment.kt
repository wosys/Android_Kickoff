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
import android.view.View
import com.drake.engine.base.EngineFragment
import com.drake.tooltip.toast
import com.wintmain.wNet.R
import com.wintmain.wNet.databinding.FragmentLimitedTimeBinding
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout
import lib.wintmain.libwNet.Get
import lib.wintmain.libwNet.utils.scopeDialog

class LimitedTimeFragment :
    EngineFragment<FragmentLimitedTimeBinding>(R.layout.fragment_limited_time) {
    override fun initView() {
        binding.v = this
    }

    override fun initData() {
    }

    override fun onClick(v: View) {
        scopeDialog {
            // 当接口请求在100毫秒内没有完成会抛出异常TimeoutCancellationException
            withTimeout(100) {
                Get<String>(com.wintmain.wNet.constants.Api.TEXT).await()
            }
        }.catch {
            Log.e("日志", "catch", it) // catch无法接收到CancellationException异常
        }.finally {
            Log.e(
                "日志",
                "finally",
                it
            ) // TimeoutCancellationException属于CancellationException子类故只会被finally接收到
            if (it is TimeoutCancellationException) {
                toast("由于未在指定时间完成请求则取消请求")
            }
        }
    }
}