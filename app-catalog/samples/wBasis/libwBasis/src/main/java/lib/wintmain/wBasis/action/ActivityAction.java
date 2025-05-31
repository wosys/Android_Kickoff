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

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

/** desc : Activity 相关意图 */
public interface ActivityAction {

    /** 获取 Context 对象 */
    Context getContext();

    /** 获取 Activity 对象 */
    default Activity getActivity() {
        Context context = getContext();
        do {
            if (context instanceof Activity) {
                return (Activity) context;
            } else if (context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            } else {
                return null;
            }
        } while (context != null);
        return null;
    }

    /** 跳转 Activity 简化版 */
    default void startActivity(Class<? extends Activity> clazz) {
        startActivity(new Intent(getContext(), clazz));
    }

    /** 跳转 Activity */
    default void startActivity(Intent intent) {
        if (!(getContext() instanceof Activity)) {
            // 如果当前的上下文不是 Activity，调用 startActivity
            // 必须加入新任务栈的标记，否则会报错：android.util.AndroidRuntimeException
            // Calling startActivity() from outside of an Activity context requires the
            // FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        getContext().startActivity(intent);
    }
}
