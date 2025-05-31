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

/**
 * desc : 权限委托接口
 */
public interface PermissionDelegate {

    /**
     * 判断某个权限是否授予了
     */
    boolean isGrantedPermission(@NonNull Context context, @NonNull String permission);

    /**
     * 判断某个权限是否永久拒绝了
     */
    boolean isPermissionPermanentDenied(@NonNull Activity activity, @NonNull String permission);

    /**
     * 获取权限设置页的意图
     */
    Intent getPermissionIntent(@NonNull Context context, @NonNull String permission);
}
