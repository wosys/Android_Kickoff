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

@file:Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate")

package com.wintmain.wNet.converter

import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer
import lib.wintmain.libwNet.convert.NetConverter
import lib.wintmain.libwNet.exception.ConvertException
import lib.wintmain.libwNet.exception.RequestParamsException
import lib.wintmain.libwNet.exception.ServerResponseException
import lib.wintmain.libwNet.request.kType
import okhttp3.Response
import java.lang.reflect.Type

class ProtobufConverter : NetConverter {

    override fun <R> onConvert(succeed: Type, response: Response): R? {
        try {
            return NetConverter.onConvert<R>(succeed, response)
        } catch (e: ConvertException) {
            val code = response.code
            when {
                code in 200..299 -> { // 请求成功
                    val bytes = response.body?.bytes() ?: return null
                    val kType = response.request.kType ?: throw ConvertException(
                        response,
                        "Request does not contain KType"
                    )
                    return ProtoBuf.decodeFromByteArray(
                        ProtoBuf.serializersModule.serializer(kType),
                        bytes
                    ) as R
                }

                code in 400..499 -> throw RequestParamsException(
                    response,
                    code.toString()
                ) // 请求参数错误
                code >= 500 -> throw ServerResponseException(response, code.toString()) // 服务器异常错误
                else -> throw ConvertException(
                    response,
                    message = "Http status code not within range"
                )
            }
        }
    }
}