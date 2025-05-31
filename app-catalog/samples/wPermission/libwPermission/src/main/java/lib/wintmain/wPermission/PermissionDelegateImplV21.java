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
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * desc : Android 5.0 权限委托实现
 */
@RequiresApi(api = AndroidVersion.ANDROID_5)
class PermissionDelegateImplV21 extends PermissionDelegateImplV19 {

    /**
     * 是否有使用统计权限
     */
    private static boolean isGrantedPackagePermission(@NonNull Context context) {
        return PermissionUtils.checkOpNoThrow(context, AppOpsManager.OPSTR_GET_USAGE_STATS);
    }

    /**
     * 获取使用统计权限设置界面意图
     */
    private static Intent getPackagePermissionIntent(@NonNull Context context) {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        if (AndroidVersion.isAndroid10()) {
            // 经过测试，只有在 Android 10 及以上加包名才有效果
            // 如果在 Android 10 以下加包名会导致无法跳转
            intent.setData(PermissionUtils.getPackageNameUri(context));
        }
        if (!PermissionUtils.areActivityIntent(context, intent)) {
            intent = PermissionUtils.getApplicationDetailsIntent(context);
        }
        return intent;
    }

    @Override
    public boolean isGrantedPermission(@NonNull Context context, @NonNull String permission) {
        // 检测获取使用统计权限
        if (PermissionUtils.equalsPermission(permission, Permission.PACKAGE_USAGE_STATS)) {
            return isGrantedPackagePermission(context);
        }
        return super.isGrantedPermission(context, permission);
    }

    @Override
    public boolean isPermissionPermanentDenied(
            @NonNull Activity activity, @NonNull String permission) {
        if (PermissionUtils.equalsPermission(permission, Permission.PACKAGE_USAGE_STATS)) {
            return false;
        }
        return super.isPermissionPermanentDenied(activity, permission);
    }

    @Override
    public Intent getPermissionIntent(@NonNull Context context, @NonNull String permission) {
        if (PermissionUtils.equalsPermission(permission, Permission.PACKAGE_USAGE_STATS)) {
            return getPackagePermissionIntent(context);
        }
        return super.getPermissionIntent(context, permission);
    }
}
