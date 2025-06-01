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

package lib.wintmain.libwNet.exception

/**
 * URL地址错误
 *
 * @param message 错误描述信息
 * @param cause 错误原因
 */
open class URLParseException(
    message: String? = null,
    cause: Throwable? = null,
) : Exception(message, cause) {

    var occurred: String = ""

    override fun getLocalizedMessage(): String? {
        return super.getLocalizedMessage() + occurred
    }
}