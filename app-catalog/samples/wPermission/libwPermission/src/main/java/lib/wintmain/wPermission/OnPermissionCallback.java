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

import androidx.annotation.NonNull;

import java.util.List;

/**
 * desc : 权限请求结果回调接口
 */
public interface OnPermissionCallback {

    /**
     * 有权限被同意授予时回调
     *
     * @param permissions 请求成功的权限组
     * @param allGranted  是否全部授予了
     */
    void onGranted(@NonNull List<String> permissions, boolean allGranted);

    /**
     * 有权限被拒绝授予时回调
     *
     * @param permissions   请求失败的权限组
     * @param doNotAskAgain 是否勾选了不再询问选项
     */
    default void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
    }
}
