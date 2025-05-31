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

package lib.wintmain.xplayer.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class KeyBoardUtil private constructor() {
    companion object {
        /**
         * 打卡软键盘
         */
        fun openKeyboard(mEditText: EditText?, mContext: Context) {
            val imm =
                mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        }

        /**
         * 关闭软键盘
         */
        fun closeKeyboard(mEditText: EditText, mContext: Context) {
            val imm =
                mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
        }
    }

    init {
        throw UnsupportedOperationException("KeyBoardUtil cannot be instantiated")
    }
}