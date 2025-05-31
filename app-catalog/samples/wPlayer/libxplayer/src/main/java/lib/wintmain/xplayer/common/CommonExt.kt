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

package lib.wintmain.xplayer.common

import android.content.Context
import java.util.Formatter

/**
 * 通用扩展方法
 */

/**
 * 当集合为空或者长度为0
 */
fun isListEmpty(list: List<Any>?): Boolean = list == null || list.isEmpty()

/**
 * 当泛型集合为空或者长度为0
 */
fun <T> isGpListEmpty(list: List<T>?): Boolean = list == null || list.isEmpty()

/**
 * 将毫秒转换为分秒-00:00格式
 */
fun Int.stringForTime(): String {
    val totalSeconds = this / 1000
    val seconds = totalSeconds % 60
    val minutes = (totalSeconds / 60) % 60

    return Formatter().format("%02d:%02d", minutes, seconds).toString()
}

/**
 * 获取指定范围内的随机数
 */
fun getRandom(start: Int, end: Int): Int {
    return ((start + Math.random() * (end - start + 1)).toInt())
}

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun dip2px(context: Context, dpValue: Float): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun px2dip(context: Context, dpValue: Float): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (dpValue / scale + 0.5f).toInt()
}
