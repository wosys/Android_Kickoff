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

package lib.wintmain.wTitlebar;

/** desc : 标题栏点击监听接口 */
public interface OnTitleBarListener {

    /**
     * 左边的标题被点击
     *
     * @param titleBarExt 标题栏对象（非空）
     */
    default void onLeftClick(TitleBarExt titleBarExt) {
    }

    /**
     * 中间的标题被点击
     *
     * @param titleBarExt 标题栏对象（非空）
     */
    default void onTitleClick(TitleBarExt titleBarExt) {
    }

    /**
     * 右边的标题被点击
     *
     * @param titleBarExt 标题栏对象（非空）
     */
    default void onRightClick(TitleBarExt titleBarExt) {
    }
}
