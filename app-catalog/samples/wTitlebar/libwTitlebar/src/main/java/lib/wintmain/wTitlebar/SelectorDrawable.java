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

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

/** desc : 状态选择器构建器 */
@SuppressWarnings("unused")
public final class SelectorDrawable extends StateListDrawable {

    public static final class Builder {

        /** 默认状态 */
        private Drawable mDefault;

        /** 焦点状态 */
        private Drawable mFocused;

        /** 按下状态 */
        private Drawable mPressed;

        /** 选中状态 */
        private Drawable mChecked;

        /** 启用状态 */
        private Drawable mEnabled;

        /** 选择状态 */
        private Drawable mSelected;

        /** 光标悬浮状态（4.0新特性） */
        private Drawable mHovered;

        public Builder setDefault(Drawable drawable) {
            mDefault = drawable;
            return this;
        }

        public Builder setFocused(Drawable drawable) {
            mFocused = drawable;
            return this;
        }

        public Builder setPressed(Drawable drawable) {
            mPressed = drawable;
            return this;
        }

        public Builder setChecked(Drawable drawable) {
            mChecked = drawable;
            return this;
        }

        public Builder setEnabled(Drawable drawable) {
            mEnabled = drawable;
            return this;
        }

        public Builder setSelected(Drawable drawable) {
            mSelected = drawable;
            return this;
        }

        public Builder setHovered(Drawable drawable) {
            mHovered = drawable;
            return this;
        }

        public SelectorDrawable build() {
            SelectorDrawable selector = new SelectorDrawable();
            if (mPressed != null) {
                selector.addState(new int[]{android.R.attr.state_pressed}, mPressed);
            }
            if (mFocused != null) {
                selector.addState(new int[]{android.R.attr.state_focused}, mFocused);
            }
            if (mChecked != null) {
                selector.addState(new int[]{android.R.attr.state_checked}, mChecked);
            }
            if (mEnabled != null) {
                selector.addState(new int[]{android.R.attr.state_enabled}, mEnabled);
            }
            if (mSelected != null) {
                selector.addState(new int[]{android.R.attr.state_selected}, mSelected);
            }
            if (mHovered != null) {
                selector.addState(new int[]{android.R.attr.state_hovered}, mHovered);
            }
            if (mDefault != null) {
                selector.addState(new int[]{}, mDefault);
            }
            return selector;
        }
    }
}
