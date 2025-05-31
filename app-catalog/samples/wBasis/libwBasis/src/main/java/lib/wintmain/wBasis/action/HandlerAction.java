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

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

/** desc : Handler 意图处理 */
public interface HandlerAction {

    Handler HANDLER = new Handler(Looper.getMainLooper());

    /** 获取 Handler */
    default Handler getHandler() {
        return HANDLER;
    }

    /** 延迟执行 */
    default boolean post(Runnable runnable) {
        return postDelayed(runnable, 0);
    }

    /** 延迟一段时间执行 */
    default boolean postDelayed(Runnable runnable, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return postAtTime(runnable, SystemClock.uptimeMillis() + delayMillis);
    }

    /** 在指定的时间执行 */
    default boolean postAtTime(Runnable runnable, long uptimeMillis) {
        // 发送和当前对象相关的消息回调
        return HANDLER.postAtTime(runnable, this, uptimeMillis);
    }

    /** 移除单个消息回调 */
    default void removeCallbacks(Runnable runnable) {
        HANDLER.removeCallbacks(runnable);
    }

    /** 移除全部消息回调 */
    default void removeCallbacks() {
        // 移除和当前对象相关的消息回调
        HANDLER.removeCallbacksAndMessages(this);
    }
}
