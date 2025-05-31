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

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.annotation.NonNull;

/**
 * desc : 通知栏监听权限兼容类
 */
final class NotificationListenerPermissionCompat {

    /**
     * Settings.Secure.ENABLED_NOTIFICATION_LISTENERS
     */
    private static final String SETTING_ENABLED_NOTIFICATION_LISTENERS =
            "enabled_notification_listeners";

    static boolean isGrantedPermission(@NonNull Context context) {
        // 经过实践得出，通知监听权限是在 Android 4.3 才出现的，所以前面的版本统一返回 true
        if (!AndroidVersion.isAndroid4_3()) {
            return true;
        }
        final String enabledNotificationListeners =
                Settings.Secure.getString(
                        context.getContentResolver(), SETTING_ENABLED_NOTIFICATION_LISTENERS);
        if (TextUtils.isEmpty(enabledNotificationListeners)) {
            return false;
        }
        // com.hjq.permissions.demo/com.hjq.permissions.demo.NotificationMonitorService:com
        // .huawei.health/com.huawei.bone.ui.setting.NotificationPushListener
        final String[] components = enabledNotificationListeners.split(":");
        for (String component : components) {
            ComponentName componentName = ComponentName.unflattenFromString(component);
            if (!TextUtils.equals(componentName.getPackageName(), context.getPackageName())) {
                continue;
            }

            String className = componentName.getClassName();
            try {
                // 判断这个类有是否存在，如果存在的话，证明是有效的
                // 如果不存在的话，证明无效的，也是需要重新授权的
                Class.forName(className);
                return true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    static Intent getPermissionIntent(@NonNull Context context) {
        Intent intent = null;
        if (AndroidVersion.isAndroid11()) {
            AndroidManifestInfo androidManifestInfo =
                    PermissionUtils.getAndroidManifestInfo(context);
            AndroidManifestInfo.ServiceInfo serviceInfo = null;
            if (androidManifestInfo != null) {
                for (AndroidManifestInfo.ServiceInfo info : androidManifestInfo.serviceInfoList) {
                    if (!TextUtils.equals(
                            info.permission, Permission.BIND_NOTIFICATION_LISTENER_SERVICE)) {
                        continue;
                    }

                    if (serviceInfo != null) {
                        // 证明有两个这样的 Service，就不跳转到权限详情页了，而是跳转到权限列表页
                        serviceInfo = null;
                        break;
                    }

                    serviceInfo = info;
                }
            }
            if (serviceInfo != null) {
                intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_DETAIL_SETTINGS);
                intent.putExtra(
                        Settings.EXTRA_NOTIFICATION_LISTENER_COMPONENT_NAME,
                        new ComponentName(context, serviceInfo.name).flattenToString());
                if (!PermissionUtils.areActivityIntent(context, intent)) {
                    intent = null;
                }
            }
        }

        if (intent == null) {
            if (AndroidVersion.isAndroid5_1()) {
                intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            } else {
                // android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
                intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            }
        }

        if (!PermissionUtils.areActivityIntent(context, intent)) {
            intent = PermissionUtils.getApplicationDetailsIntent(context);
        }
        return intent;
    }
}
