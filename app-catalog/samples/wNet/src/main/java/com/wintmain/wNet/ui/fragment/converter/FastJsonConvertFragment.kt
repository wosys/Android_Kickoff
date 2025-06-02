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

package com.wintmain.wNet.ui.fragment.converter

import com.wintmain.wNet.R
import com.wintmain.wNet.converter.FastJsonConverter
import com.wintmain.wNet.databinding.FragmentCustomConvertBinding
import com.wintmain.wNet.model.GameModel
import lib.wintmain.libwNet.Get
import lib.wintmain.libwNet.utils.scopeNetLife

class FastJsonConvertFragment :
    BaseConvertFragment<FragmentCustomConvertBinding>(R.layout.fragment_custom_convert) {

    override fun initView() {
        binding.tvConvertTip.text = """
            1. 阿里巴巴出品的Json解析库
            2. 引入kotlin-reflect库可以支持kotlin默认值
        """.trimIndent()

        scopeNetLife {
            binding.tvFragment.text = Get<GameModel>(com.wintmain.wNet.constants.Api.GAME) {
                converter = FastJsonConverter() // 单例转换器, 此时会忽略全局转换器
            }.await().list[0].name
        }
    }

    override fun initData() {
    }
}
