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

package lib.wintmain.xplayer.utils;

import android.graphics.Color;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import lib.wintmain.xplayer.BaseApp;


/**
 * des 颜色处理工具类
 */
public class ColorUtils {
    /**
     * 解析颜色
     */
    public static int parseColor(String colorStr, int defaultColor) {
        if (TextUtils.isEmpty(colorStr)) {
            return defaultColor;
        }
        try {
            if (!colorStr.startsWith("#")) {
                colorStr = "#" + colorStr;
            }
            int color = Color.parseColor(colorStr);
            return color;
        } catch (Exception e) {
            return defaultColor;
        }
    }

    public static int parseColor(String colorStr) {
        if (TextUtils.isEmpty(colorStr)) {
            return 0;
        }
        try {
            if (!colorStr.startsWith("#")) {
                colorStr = "#" + colorStr;
            }
            return Color.parseColor(colorStr);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 解析颜色
     */
    public static int parseColor(int color) {
        return ContextCompat.getColor(BaseApp.Companion.getContext(), color);
    }

    /**
     * 设置html字体色值
     */
    public static String setTextColor(String text, String color) {
        return "<font color=#" + color + ">" + text + "</font>";
    }

}
