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

import com.drake.brv.utils.linear
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.drake.engine.base.EngineFragment
import com.wintmain.wNet.R
import com.wintmain.wNet.databinding.FragmentPushRefreshBinding
import com.wintmain.wNet.model.GameModel
import lib.wintmain.libwNet.Get
import lib.wintmain.libwNet.utils.scope

/** 本页面已禁用上拉加载(添加xml属性app:srlEnableLoadMore="false"), 只允许下拉刷新 */
class PushRefreshFragment :
    EngineFragment<FragmentPushRefreshBinding>(R.layout.fragment_push_refresh) {

    override fun initView() {
        binding.rv.linear().setup {
            addType<GameModel.Data>(R.layout.item_game)
        }

        binding.page.onRefresh {
            scope {
                binding.rv.models =
                    Get<GameModel>(com.wintmain.wNet.constants.Api.GAME).await().list
            }
            // }.autoRefresh() // 首次下拉刷新
        }.showLoading() // 首次加载缺省页
    }

    override fun initData() {
    }
}
