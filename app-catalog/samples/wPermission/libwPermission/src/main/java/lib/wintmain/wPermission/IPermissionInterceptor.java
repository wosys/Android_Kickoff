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

package lib.wintmain.wPermission;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * desc : 权限请求拦截器
 */
public interface IPermissionInterceptor {

    /**
     * 发起权限申请（可在此处先弹 Dialog 再申请权限，如果用户已经授予权限，则不会触发此回调）
     *
     * @param allPermissions 申请的权限
     * @param callback       权限申请回调
     */
    default void launchPermissionRequest(
            @NonNull Activity activity,
            @NonNull List<String> allPermissions,
            @Nullable OnPermissionCallback callback) {
        PermissionFragment.launch(activity, new ArrayList<>(allPermissions), this, callback);
    }

    /**
     * 用户授予了权限（注意需要在此处回调 {@link OnPermissionCallback#onGranted(List, boolean)}）
     *
     * @param allPermissions     申请的权限
     * @param grantedPermissions 已授予的权限
     * @param allGranted         是否全部授予
     * @param callback           权限申请回调
     */
    default void grantedPermissionRequest(
            @NonNull Activity activity,
            @NonNull List<String> allPermissions,
            @NonNull List<String> grantedPermissions,
            boolean allGranted,
            @Nullable OnPermissionCallback callback) {
        if (callback == null) {
            return;
        }
        callback.onGranted(grantedPermissions, allGranted);
    }

    /**
     * 用户拒绝了权限（注意需要在此处回调 {@link OnPermissionCallback#onDenied(List, boolean)}）
     *
     * @param allPermissions    申请的权限
     * @param deniedPermissions 已拒绝的权限
     * @param doNotAskAgain     是否勾选了不再询问选项
     * @param callback          权限申请回调
     */
    default void deniedPermissionRequest(
            @NonNull Activity activity,
            @NonNull List<String> allPermissions,
            @NonNull List<String> deniedPermissions,
            boolean doNotAskAgain,
            @Nullable OnPermissionCallback callback) {
        if (callback == null) {
            return;
        }
        callback.onDenied(deniedPermissions, doNotAskAgain);
    }

    /**
     * 权限请求完成
     *
     * @param allPermissions 申请的权限
     * @param skipRequest    是否跳过了申请过程
     * @param callback       权限申请回调
     */
    default void finishPermissionRequest(
            @NonNull Activity activity,
            @NonNull List<String> allPermissions,
            boolean skipRequest,
            @Nullable OnPermissionCallback callback) {
    }
}
