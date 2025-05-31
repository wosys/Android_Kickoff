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

import android.view.View;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

/** desc : 点击事件意图 */
public interface ClickAction extends View.OnClickListener {

    <V extends View> V findViewById(@IdRes int id);

    default void setOnClickListener(@IdRes int... ids) {
        setOnClickListener(this, ids);
    }

    default void setOnClickListener(@Nullable View.OnClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    default void setOnClickListener(View... views) {
        setOnClickListener(this, views);
    }

    default void setOnClickListener(@Nullable View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    @Override
    default void onClick(View view) {
        // 默认不实现，让子类实现
    }
}
