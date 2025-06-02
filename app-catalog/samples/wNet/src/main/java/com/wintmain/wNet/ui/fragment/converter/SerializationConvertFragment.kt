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
import com.wintmain.wNet.converter.SerializationConverter
import com.wintmain.wNet.databinding.FragmentCustomConvertBinding
import com.wintmain.wNet.model.GameModel
import lib.wintmain.libwNet.Get
import lib.wintmain.libwNet.utils.scopeNetLife

class SerializationConvertFragment :
    BaseConvertFragment<FragmentCustomConvertBinding>(R.layout.fragment_custom_convert) {

    override fun initView() {
        binding.tvConvertTip.text = """
            1. kotlin官方出品, 推荐使用 
            2. kotlinx.serialization 是Kotlin上是最完美的序列化工具 
            3. 支持多种反序列化数据类型Pair/枚举/Map...
            4. 多配置选项
            5. 支持动态解析
            6. 支持ProtoBuf/CBOR/JSON等数据
        """.trimIndent()

        scopeNetLife {
            val data = Get<GameModel>(com.wintmain.wNet.constants.Api.GAME) {
                // 该转换器直接解析JSON中的data字段, 而非返回的整个JSON字符串
                converter = SerializationConverter() // 单例转换器, 此时会忽略全局转换器
            }.await()

            binding.tvFragment.text = data.list[0].name
        }
    }

    override fun initData() {
    }
}
