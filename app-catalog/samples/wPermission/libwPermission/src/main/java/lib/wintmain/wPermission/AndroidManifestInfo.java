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

import android.content.pm.PackageInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * desc : 清单文件解析 Bean 类
 */
final class AndroidManifestInfo {

    /**
     * 权限节点信息
     */
    @NonNull
    final List<PermissionInfo> permissionInfoList = new ArrayList<>();
    /**
     * Activity 节点信息
     */
    @NonNull
    final List<ActivityInfo> activityInfoList = new ArrayList<>();
    /**
     * Service 节点信息
     */
    @NonNull
    final List<ServiceInfo> serviceInfoList = new ArrayList<>();
    /**
     * 应用包名
     */
    String packageName;
    /**
     * 使用 sdk 信息
     */
    @Nullable
    UsesSdkInfo usesSdkInfo;
    /**
     * Application 节点信息
     */
    ApplicationInfo applicationInfo;

    static final class UsesSdkInfo {

        public int minSdkVersion;
    }

    static final class PermissionInfo {

        /**
         * {@link PackageInfo#REQUESTED_PERMISSION_NEVER_FOR_LOCATION}
         */
        private static final int REQUESTED_PERMISSION_NEVER_FOR_LOCATION = 0x00010000;

        public String name;
        public int maxSdkVersion;
        public int usesPermissionFlags;

        public boolean neverForLocation() {
            return (usesPermissionFlags & REQUESTED_PERMISSION_NEVER_FOR_LOCATION) != 0;
        }
    }

    static final class ApplicationInfo {
        public String name;
        public boolean requestLegacyExternalStorage;
    }

    static final class ActivityInfo {
        public String name;
        public boolean supportsPictureInPicture;
    }

    static final class ServiceInfo {
        public String name;
        public String permission;
    }
}
