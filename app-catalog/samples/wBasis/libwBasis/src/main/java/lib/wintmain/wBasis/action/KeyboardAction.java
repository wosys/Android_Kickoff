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

package lib.wintmain.wBasis.action;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/** desc : 软键盘相关意图 */
public interface KeyboardAction {

    /** 显示软键盘，需要先 requestFocus 获取焦点，如果是在 Activity Create，那么需要延迟一段时间 */
    default void showKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager =
                (InputMethodManager)
                        view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager == null) {
            return;
        }
        manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /** 隐藏软键盘 */
    default void hideKeyboard(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager =
                (InputMethodManager)
                        view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager == null) {
            return;
        }
        manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /** 切换软键盘 */
    default void toggleSoftInput(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager =
                (InputMethodManager)
                        view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager == null) {
            return;
        }
        manager.toggleSoftInput(0, 0);
    }
}
