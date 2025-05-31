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

package lib.wintmain.wToaster.style;

import android.view.Gravity;
import lib.wintmain.wToaster.toast.IToastStyle;

/** desc : 默认黑色样式实现 */
public class ToastBlackStyle implements IToastStyle {

    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getXOffset() {
        return 0;
    }

    @Override
    public int getYOffset() {
        return 0;
    }

    @Override
    public int getZ() {
        return 30;
    }

    @Override
    public int getCornerRadius() {
        return 6;
    }

    @Override
    public int getBackgroundColor() {
        return 0X88000000;
    }

    @Override
    public int getTextColor() {
        return 0XEEFFFFFF;
    }

    @Override
    public float getTextSize() {
        return 14;
    }

    @Override
    public int getMaxLines() {
        return 3;
    }

    @Override
    public int getPaddingLeft() {
        return 24;
    }

    @Override
    public int getPaddingTop() {
        return 16;
    }

    @Override
    public int getPaddingRight() {
        return getPaddingLeft();
    }

    @Override
    public int getPaddingBottom() {
        return getPaddingTop();
    }
}
