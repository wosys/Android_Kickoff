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

import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import lib.wintmain.libwNet.NetConfig
import lib.wintmain.libwNet.convert.NetConverter
import lib.wintmain.libwNet.exception.ConvertException
import lib.wintmain.libwNet.exception.RequestParamsException
import lib.wintmain.libwNet.exception.ResponseException
import lib.wintmain.libwNet.exception.ServerResponseException
import lib.wintmain.libwNet.request.kType
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type
import kotlin.reflect.KType

class SerializationConverter(
    val success: String = "0",
    val code: String = "errorCode",
    val message: String = "errorMsg",
) : NetConverter {

    companion object {
        val jsonDecoder = Json {
            ignoreUnknownKeys = true // 数据类可以不用声明Json的所有字段
            coerceInputValues = true // 如果Json字段是Null则使用数据类字段默认值
        }
    }

    override fun <R> onConvert(succeed: Type, response: Response): R? {
        try {
            return NetConverter.onConvert<R>(succeed, response)
        } catch (e: ConvertException) {
            val code = response.code
            when {
                code in 200..299 -> { // 请求成功
                    val bodyString = response.body?.string() ?: return null
                    val kType = response.request.kType
                        ?: throw ConvertException(response, "Request does not contain KType")
                    return try {
                        val json = JSONObject(bodyString) // 获取JSON中后端定义的错误码和错误信息
                        val srvCode = json.getString(this.code)
                        if (srvCode == success) { // 对比后端自定义错误码
                            json.getString("data").parseBody<R>(kType)
                        } else { // 错误码匹配失败, 开始写入错误异常
                            val errorMessage = json.optString(
                                message,
                                NetConfig.app.getString(lib.wintmain.libwNet.R.string.no_error_message)
                            )
                            throw ResponseException(
                                response,
                                errorMessage,
                                tag = srvCode
                            ) // 将业务错误码作为tag传递
                        }
                    } catch (e: JSONException) { // 固定格式JSON分析失败直接解析JSON
                        bodyString.parseBody<R>(kType)
                    }
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

    fun <R> String.parseBody(succeed: KType): R? {
        return jsonDecoder.decodeFromString(Json.serializersModule.serializer(succeed), this) as R
    }
}