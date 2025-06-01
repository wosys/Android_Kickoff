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

package lib.wintmain.libwNet.utils

import android.annotation.SuppressLint
import android.widget.Toast
import lib.wintmain.libwNet.NetConfig

object TipUtils {

    private var toast: Toast? = null

    /**
     * 重复显示不会覆盖, 可以在子线程显示
     * 本方法会导致报内存泄露, 这是因为为了避免吐司反复显示导致重叠会长期持有Toast引用来保持单例导致, 可以无视或者自己实现吐司
     */
    @SuppressLint("ShowToast")
    @JvmStatic
    fun toast(message: String?) {
        message ?: return
        runMain {
            toast?.cancel()
            toast = Toast.makeText(NetConfig.app, message, Toast.LENGTH_SHORT)
            toast?.show()
        }
    }
}