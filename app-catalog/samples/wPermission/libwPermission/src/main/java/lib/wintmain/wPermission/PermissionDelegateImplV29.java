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
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * desc : Android 10 权限委托实现
 */
@RequiresApi(api = AndroidVersion.ANDROID_10)
class PermissionDelegateImplV29 extends PermissionDelegateImplV28 {

    /**
     * 是否采用的是非分区存储的模式
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isUseDeprecationExternalStorage() {
        return Environment.isExternalStorageLegacy();
    }

    @Override
    public boolean isGrantedPermission(@NonNull Context context, @NonNull String permission) {
        if (PermissionUtils.equalsPermission(permission, Permission.ACCESS_MEDIA_LOCATION)) {
            return hasReadStoragePermission(context)
                    && PermissionUtils.checkSelfPermission(
                    context, Permission.ACCESS_MEDIA_LOCATION);
        }

        if (PermissionUtils.equalsPermission(permission, Permission.ACCESS_BACKGROUND_LOCATION)
                || PermissionUtils.equalsPermission(permission, Permission.ACTIVITY_RECOGNITION)) {
            return PermissionUtils.checkSelfPermission(context, permission);
        }

        // 向下兼容 Android 11 新权限
        if (!AndroidVersion.isAndroid11()) {
            if (PermissionUtils.equalsPermission(permission, Permission.MANAGE_EXTERNAL_STORAGE)) {
                // 这个是 Android 10 上面的历史遗留问题，假设申请的是 MANAGE_EXTERNAL_STORAGE 权限
                // 必须要在 AndroidManifest.xml 中注册 android:requestLegacyExternalStorage="true"
                if (!isUseDeprecationExternalStorage()) {
                    return false;
                }
            }
        }

        return super.isGrantedPermission(context, permission);
    }

    @Override
    public boolean isPermissionPermanentDenied(
            @NonNull Activity activity, @NonNull String permission) {
        if (PermissionUtils.equalsPermission(permission, Permission.ACCESS_BACKGROUND_LOCATION)) {
            if (!PermissionUtils.checkSelfPermission(activity, Permission.ACCESS_FINE_LOCATION)) {
                return !PermissionUtils.shouldShowRequestPermissionRationale(
                        activity, Permission.ACCESS_FINE_LOCATION);
            }
            return !PermissionUtils.checkSelfPermission(activity, permission)
                    && !PermissionUtils.shouldShowRequestPermissionRationale(activity, permission);
        }

        if (PermissionUtils.equalsPermission(permission, Permission.ACCESS_MEDIA_LOCATION)) {
            return hasReadStoragePermission(activity)
                    && !PermissionUtils.checkSelfPermission(activity, permission)
                    && !PermissionUtils.shouldShowRequestPermissionRationale(activity, permission);
        }

        if (PermissionUtils.equalsPermission(permission, Permission.ACTIVITY_RECOGNITION)) {
            return !PermissionUtils.checkSelfPermission(activity, permission)
                    && !PermissionUtils.shouldShowRequestPermissionRationale(activity, permission);
        }

        // 向下兼容 Android 11 新权限
        if (!AndroidVersion.isAndroid11()) {
            if (PermissionUtils.equalsPermission(permission, Permission.MANAGE_EXTERNAL_STORAGE)) {
                // 处理 Android 10 上面的历史遗留问题
                if (!isUseDeprecationExternalStorage()) {
                    return true;
                }
            }
        }

        return super.isPermissionPermanentDenied(activity, permission);
    }

    /**
     * 是否有读取文件的权限
     */
    private boolean hasReadStoragePermission(@NonNull Context context) {
        if (AndroidVersion.isAndroid13()
                && AndroidVersion.getTargetSdkVersionCode(context) >= AndroidVersion.ANDROID_13) {
            return PermissionUtils.checkSelfPermission(context, Permission.READ_MEDIA_IMAGES)
                    || isGrantedPermission(context, Permission.MANAGE_EXTERNAL_STORAGE);
        }
        if (AndroidVersion.isAndroid11()
                && AndroidVersion.getTargetSdkVersionCode(context) >= AndroidVersion.ANDROID_11) {
            return PermissionUtils.checkSelfPermission(context, Permission.READ_EXTERNAL_STORAGE)
                    || isGrantedPermission(context, Permission.MANAGE_EXTERNAL_STORAGE);
        }
        return PermissionUtils.checkSelfPermission(context, Permission.READ_EXTERNAL_STORAGE);
    }
}
