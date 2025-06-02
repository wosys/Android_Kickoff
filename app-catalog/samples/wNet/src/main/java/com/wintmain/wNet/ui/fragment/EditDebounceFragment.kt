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

import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.wintmain.wNet.R
import com.wintmain.wNet.databinding.FragmentEditDebounceBinding
import com.wintmain.wNet.model.GameModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.distinctUntilChanged
import lib.wintmain.libwNet.Get
import lib.wintmain.libwNet.utils.debounce
import lib.wintmain.libwNet.utils.launchIn
import lib.wintmain.libwNet.utils.scope

class EditDebounceFragment :
    EngineFragment<FragmentEditDebounceBinding>(R.layout.fragment_edit_debounce) {

    override fun initData() {
    }

    override fun initView() {
        var searchText = ""
        var scope: CoroutineScope? = null

        // 配置列表
        binding.rv.setup {
            addType<GameModel.Data>(R.layout.item_game)
        }

        // 监听分页
        binding.page.onRefresh {
            scope = scope {
                val data = Get<GameModel>(com.wintmain.wNet.constants.Api.GAME) {
                    param("search", searchText)
                    param("page", index)
                }.await()
                addData(data.list) {
                    itemCount < data.total
                }
            }
        }

        // distinctUntilChanged 表示过滤掉重复结果
        binding.etInput.debounce().distinctUntilChanged().launchIn(this) {
            scope?.cancel() // 发起新的请求前取消旧的请求, 避免旧数据覆盖新数据
            searchText = it
            if (it.isBlank()) {
                binding.rv.models = null
            } else {
                binding.page.showLoading()
            }
        }
    }
}