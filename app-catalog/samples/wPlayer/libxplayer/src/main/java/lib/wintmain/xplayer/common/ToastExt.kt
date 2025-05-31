/*
 * Copyright 2023-2024 wintmain
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

package lib.wintmain.xplayer.common

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.StringRes
import lib.wintmain.xplayer.BaseApp

/**
 * des Toast工具类
 */
fun Context.toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    if (TextUtils.isEmpty(content)) return
    Toast.makeText(this, content, duration).apply {
        show()
    }
}

fun Context.toast(@StringRes id: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast(getString(id), duration)
}

fun toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    if (TextUtils.isEmpty(content)) return
    BaseApp.getContext().toast(content, duration)
}

fun toast(@StringRes id: Int, duration: Int = Toast.LENGTH_SHORT) {
    BaseApp.getContext().toast(id, duration)
}

fun longToast(content: String, duration: Int = Toast.LENGTH_LONG) {
    if (TextUtils.isEmpty(content)) return
    BaseApp.getContext().toast(content, duration)
}

fun longToast(@StringRes id: Int, duration: Int = Toast.LENGTH_LONG) {
    BaseApp.getContext().toast(id, duration)
}


