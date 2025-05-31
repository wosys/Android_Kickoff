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
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * desc : Android 4.4 权限委托实现
 */
@RequiresApi(api = AndroidVersion.ANDROID_4_4)
class PermissionDelegateImplV19 extends PermissionDelegateImplV18 {

    @Override
    public boolean isGrantedPermission(@NonNull Context context, @NonNull String permission) {
        // 检测悬浮窗权限
        if (PermissionUtils.equalsPermission(permission, Permission.SYSTEM_ALERT_WINDOW)) {
            return WindowPermissionCompat.isGrantedPermission(context);
        }

        // 检查读取应用列表权限
        if (PermissionUtils.equalsPermission(permission, Permission.GET_INSTALLED_APPS)) {
            return GetInstalledAppsPermissionCompat.isGrantedPermission(context);
        }

        // 检测通知栏权限
        if (PermissionUtils.equalsPermission(permission, Permission.NOTIFICATION_SERVICE)) {
            return NotificationPermissionCompat.isGrantedPermission(context);
        }
        // 向下兼容 Android 13 新权限
        if (!AndroidVersion.isAndroid13()) {

            if (PermissionUtils.equalsPermission(permission, Permission.POST_NOTIFICATIONS)) {
                return NotificationPermissionCompat.isGrantedPermission(context);
            }
        }
        return super.isGrantedPermission(context, permission);
    }

    @Override
    public boolean isPermissionPermanentDenied(
            @NonNull Activity activity, @NonNull String permission) {
        if (PermissionUtils.equalsPermission(permission, Permission.SYSTEM_ALERT_WINDOW)) {
            return false;
        }

        if (PermissionUtils.equalsPermission(permission, Permission.GET_INSTALLED_APPS)) {
            return GetInstalledAppsPermissionCompat.isPermissionPermanentDenied(activity);
        }

        if (PermissionUtils.equalsPermission(permission, Permission.NOTIFICATION_SERVICE)) {
            return false;
        }
        // 向下兼容 Android 13 新权限
        if (!AndroidVersion.isAndroid13()) {

            if (PermissionUtils.equalsPermission(permission, Permission.POST_NOTIFICATIONS)) {
                return false;
            }
        }
        return super.isPermissionPermanentDenied(activity, permission);
    }

    @Override
    public Intent getPermissionIntent(@NonNull Context context, @NonNull String permission) {
        if (PermissionUtils.equalsPermission(permission, Permission.SYSTEM_ALERT_WINDOW)) {
            return WindowPermissionCompat.getPermissionIntent(context);
        }

        if (PermissionUtils.equalsPermission(permission, Permission.GET_INSTALLED_APPS)) {
            return GetInstalledAppsPermissionCompat.getPermissionIntent(context);
        }

        if (PermissionUtils.equalsPermission(permission, Permission.NOTIFICATION_SERVICE)) {
            return NotificationPermissionCompat.getPermissionIntent(context);
        }

        // 向下兼容 Android 13 新权限
        if (!AndroidVersion.isAndroid13()) {

            if (PermissionUtils.equalsPermission(permission, Permission.POST_NOTIFICATIONS)) {
                return NotificationPermissionCompat.getPermissionIntent(context);
            }
        }
        return super.getPermissionIntent(context, permission);
    }
}
