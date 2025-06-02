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

import androidx.fragment.app.viewModels
import com.drake.engine.base.EngineFragment
import com.wintmain.wNet.R
import com.wintmain.wNet.databinding.FragmentViewModelRequestBinding
import com.wintmain.wNet.utils.HttpUtils
import com.wintmain.wNet.vm.UserViewModel
import lib.wintmain.libwNet.utils.scopeNetLife

class ViewModelRequestFragment :
    EngineFragment<FragmentViewModelRequestBinding>(R.layout.fragment_view_model_request) {

    private val userViewModel: UserViewModel by viewModels() // 创建ViewModel

    override fun initView() {

        // 直接将用户信息绑定到视图上
        binding.lifecycleOwner = this
        binding.m = userViewModel

        // 动作开始拉取服务器数据
        binding.btnFetchUserinfo.setOnClickListener {
            userViewModel.fetchUserInfo()
        }
    }

    override fun initData() {

        scopeNetLife {
            val configAsync = HttpUtils.getConfigAsync(this)
            // 经常使用的请求可以封装函数
            val userInfo = HttpUtils.getUser()
            configAsync.await() // 实际上在getUser之前就发起请求, 此处只是等待结果, 这就是并发请求
        }
    }
}