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

package com.wintmain.wNet.model

@kotlinx.serialization.Serializable
data class GameModel(
    var total: Int = 0,
    var list: List<Data> = listOf()
) {

    @kotlinx.serialization.Serializable
    data class Data(
        var id: Int = 0,
        var img: String = "",
        var name: String = "",
        var label: List<String> = listOf(),
        var price: String = "",
        var initialPrice: String = "",
        var grade: Int = 0,
        var discount: Double = 0.0,
        var endTime: Int = 0
    )
}