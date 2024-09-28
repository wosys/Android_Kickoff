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

package lib.wintmain.toaster.toast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * @Description @Author wintmain
 * @mailto wosintmain@gmail.com @Date 2022-11-27 17:01:46
 */
@SuppressLint("NewApi")
final class WindowHelper implements Application.ActivityLifecycleCallbacks {

    // 当前 Activity 的 WindowManager 对象
    private WindowManager mWindowManager;

    // 当前 Activity 对象标记
    private String mCurrentTag;

    WindowHelper(Application application) {
        // 这个报错不用管，如果当前 API < 19 不会创建 SupportToast 和 ToastHelper，从而不会触发 WindowHelper 实例化
        application.registerActivityLifecycleCallbacks(this);
    }

    /**
     * 如果使用的 WindowManager 对象不是当前 Activity 的，则会抛出异常 android.view.WindowManager$BadTokenException:
     * Unable to add window -- token null is not for an application
     */
    private static WindowManager getActivityWindowManager(Activity activity) {
        return (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
    }

    /** 获取一个对象的独立无二的标记 */
    private static String getObjectTag(Object object) {
        // 对象所在的包名 + 对象的内存地址
        return object.getClass().getName() + Integer.toHexString(object.hashCode());
    }

    WindowManager getWindowManager() {
        return mWindowManager;
    }

    /** {@link Application.ActivityLifecycleCallbacks} */
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mCurrentTag = getObjectTag(activity);
        mWindowManager = getActivityWindowManager(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mCurrentTag = getObjectTag(activity);
        mWindowManager = getActivityWindowManager(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (getObjectTag(activity).equals(mCurrentTag)) {
            // 移除 WindowManager 引用，避免内存泄露
            mWindowManager = null;
            mCurrentTag = null;
        }
    }
}
