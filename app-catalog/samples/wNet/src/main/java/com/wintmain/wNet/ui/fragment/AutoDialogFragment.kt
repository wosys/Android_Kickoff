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
import com.drake.tooltip.toast
import com.wintmain.wNet.R
import com.wintmain.wNet.databinding.FragmentAutoDialogBinding
import kotlinx.coroutines.CancellationException
import lib.wintmain.libwNet.Post
import lib.wintmain.libwNet.utils.scopeDialog

class AutoDialogFragment :
    EngineFragment<FragmentAutoDialogBinding>(R.layout.fragment_auto_dialog) {

    override fun initView() {
        scopeDialog {
            binding.tvFragment.text = Post<String>(com.wintmain.wNet.constants.Api.DELAY) {
                param("username", "你的账号")
                param("password", "123456")
            }.await()
        }.finally {
            // 关闭对话框后执行的异常
            if (it is CancellationException) {
                toast("对话框被关闭, 网络请求自动取消") // 这里存在Handler吐司崩溃, 如果不想处理就直接使用我的吐司库 https://github.com/liangjingkanji/Tooltip
            }
        }
    }

    override fun initData() {
    }
}
