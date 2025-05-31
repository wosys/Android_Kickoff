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
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * Android 12 权限委托实现
 */
@RequiresApi(api = AndroidVersion.ANDROID_12)
class PermissionDelegateImplV31 extends PermissionDelegateImplV30 {

    /**
     * 是否有闹钟权限
     */
    private static boolean isGrantedAlarmPermission(@NonNull Context context) {
        return context.getSystemService(AlarmManager.class).canScheduleExactAlarms();
    }

    /**
     * 获取闹钟权限设置界面意图
     */
    private static Intent getAlarmPermissionIntent(@NonNull Context context) {
        Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
        intent.setData(PermissionUtils.getPackageNameUri(context));
        if (!PermissionUtils.areActivityIntent(context, intent)) {
            intent = PermissionUtils.getApplicationDetailsIntent(context);
        }
        return intent;
    }

    @Override
    public boolean isGrantedPermission(@NonNull Context context, @NonNull String permission) {
        // 检测闹钟权限
        if (PermissionUtils.equalsPermission(permission, Permission.SCHEDULE_EXACT_ALARM)) {
            return isGrantedAlarmPermission(context);
        }

        if (PermissionUtils.equalsPermission(permission, Permission.BLUETOOTH_SCAN)
                || PermissionUtils.equalsPermission(permission, Permission.BLUETOOTH_CONNECT)
                || PermissionUtils.equalsPermission(permission, Permission.BLUETOOTH_ADVERTISE)) {
            return PermissionUtils.checkSelfPermission(context, permission);
        }
        return super.isGrantedPermission(context, permission);
    }

    @Override
    public boolean isPermissionPermanentDenied(
            @NonNull Activity activity, @NonNull String permission) {
        if (PermissionUtils.equalsPermission(permission, Permission.SCHEDULE_EXACT_ALARM)) {
            return false;
        }

        if (PermissionUtils.equalsPermission(permission, Permission.BLUETOOTH_SCAN)
                || PermissionUtils.equalsPermission(permission, Permission.BLUETOOTH_CONNECT)
                || PermissionUtils.equalsPermission(permission, Permission.BLUETOOTH_ADVERTISE)) {
            return !PermissionUtils.checkSelfPermission(activity, permission)
                    && !PermissionUtils.shouldShowRequestPermissionRationale(activity, permission);
        }

        if (activity.getApplicationInfo().targetSdkVersion >= AndroidVersion.ANDROID_12
                && PermissionUtils.equalsPermission(
                permission, Permission.ACCESS_BACKGROUND_LOCATION)) {
            if (!PermissionUtils.checkSelfPermission(activity, Permission.ACCESS_FINE_LOCATION)
                    && !PermissionUtils.checkSelfPermission(
                    activity, Permission.ACCESS_COARSE_LOCATION)) {
                return !PermissionUtils.shouldShowRequestPermissionRationale(
                        activity, Permission.ACCESS_FINE_LOCATION)
                        && !PermissionUtils.shouldShowRequestPermissionRationale(
                        activity, Permission.ACCESS_COARSE_LOCATION);
            }

            return !PermissionUtils.checkSelfPermission(activity, permission)
                    && !PermissionUtils.shouldShowRequestPermissionRationale(activity, permission);
        }
        return super.isPermissionPermanentDenied(activity, permission);
    }

    @Override
    public Intent getPermissionIntent(@NonNull Context context, @NonNull String permission) {
        if (PermissionUtils.equalsPermission(permission, Permission.SCHEDULE_EXACT_ALARM)) {
            return getAlarmPermissionIntent(context);
        }

        return super.getPermissionIntent(context, permission);
    }
}
