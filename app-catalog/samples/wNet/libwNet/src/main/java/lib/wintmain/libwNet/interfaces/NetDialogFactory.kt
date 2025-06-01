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

package lib.wintmain.libwNet.interfaces

import android.app.Dialog
import android.app.ProgressDialog
import androidx.fragment.app.FragmentActivity
import lib.wintmain.libwNet.R

fun interface NetDialogFactory {

    /**
     * 构建并返回Dialog. 当使用 scopeDialog 作用域时将会自动显示该对话框且作用域完成后关闭对话框
     *
     * @param activity 请求发生所在的[FragmentActivity]
     */
    fun onCreate(activity: FragmentActivity): Dialog

    companion object DEFAULT : NetDialogFactory {
        override fun onCreate(activity: FragmentActivity): Dialog {
            val progress = ProgressDialog(activity)
            progress.setMessage(activity.getString(R.string.net_dialog_msg))
            return progress
        }
    }
}