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

package lib.wintmain.wToaster.toast;

/** desc : 默认样式接口 */
public interface IToastStyle {

    public int getGravity(); // 吐司的重心

    public int getXOffset(); // X轴偏移

    public int getYOffset(); // Y轴偏移

    int getZ(); // 吐司Z轴作标

    public int getCornerRadius(); // 圆角大小

    public int getBackgroundColor(); // 背景颜色

    public int getTextColor(); // 文本颜色

    public float getTextSize(); // 文本大小

    public int getMaxLines(); // 最大行数

    public int getPaddingLeft(); // 左边内边距

    public int getPaddingTop(); // 顶部内边距

    public int getPaddingRight(); // 右边内边距

    public int getPaddingBottom(); // 底部内边距
}
