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

package lib.wintmain.wTitlebar.style;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import lib.wintmain.wTitlebar.TitleBarSupport;

/** desc : 水波纹样式实现（对应布局属性：app:barStyle="ripple"） */
public class RippleBarStyle extends TransparentBarStyle {

    @Override
    public Drawable getLeftTitleBackground(Context context) {
        Drawable drawable = createRippleDrawable(context);
        if (drawable != null) {
            return drawable;
        }
        return super.getLeftTitleBackground(context);
    }

    @Override
    public Drawable getRightTitleBackground(Context context) {
        Drawable drawable = createRippleDrawable(context);
        if (drawable != null) {
            return drawable;
        }
        return super.getRightTitleBackground(context);
    }

    /** 获取水波纹的点击效果 */
    public Drawable createRippleDrawable(Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme()
                .resolveAttribute(
                        android.R.attr.selectableItemBackgroundBorderless, typedValue, true)) {
            return TitleBarSupport.getDrawable(context, typedValue.resourceId);
        }
        return null;
    }
}
