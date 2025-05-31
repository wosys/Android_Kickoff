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

package com.wintmain.wPermission;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.*;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import lib.wintmain.wToaster.toast.ToastUtils;
import lib.wintmain.wPermission.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限申请拦截器
 */
public final class PermissionInterceptor implements IPermissionInterceptor {

    public static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * 权限申请标记
     */
    private boolean mRequestFlag;

    /**
     * 权限申请说明 Popup
     */
    private PopupWindow mPermissionPopup;

    @Override
    public void launchPermissionRequest(
            @NonNull Activity activity,
            @NonNull List<String> allPermissions,
            @Nullable OnPermissionCallback callback) {
        mRequestFlag = true;
        List<String> deniedPermissions = XPermissions.getDenied(activity, allPermissions);
        String message =
                activity.getString(
                        R.string.common_permission_message,
                        PermissionNameConvert.getPermissionString(activity, deniedPermissions));

        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int activityOrientation = activity.getResources().getConfiguration().orientation;

        boolean showPopupWindow = activityOrientation == Configuration.ORIENTATION_PORTRAIT;
        for (String permission : allPermissions) {
            if (!XPermissions.isSpecial(permission)) {
                continue;
            }
            if (XPermissions.isGranted(activity, permission)) {
                continue;
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R
                    && TextUtils.equals(Permission.MANAGE_EXTERNAL_STORAGE, permission)) {
                continue;
            }
            // 如果申请的权限带有特殊权限，并且还没有授予的话
            // 就不用 PopupWindow 对话框来显示，而是用 Dialog 来显示
            showPopupWindow = false;
            break;
        }

        if (showPopupWindow) {
            PermissionFragment.launch(activity, new ArrayList<>(allPermissions), this, callback);
            // 延迟 300 毫秒是为了避免出现 PopupWindow 显示然后立马消失的情况
            // 因为框架没有办法在还没有申请权限的情况下，去判断权限是否永久拒绝了，必须要在发起权限申请之后
            // 所以只能通过延迟显示 PopupWindow 来做这件事，如果 300 毫秒内权限申请没有结束，证明本次申请的权限没有永久拒绝
            HANDLER.postDelayed(
                    () -> {
                        if (!mRequestFlag) {
                            return;
                        }
                        if (activity.isFinishing() || activity.isDestroyed()) {
                            return;
                        }
                        showPopupWindow(activity, decorView, message);
                    },
                    300);
        } else {
            // 注意：这里的 Dialog 只是示例，没有用 DialogFragment 来处理 Dialog 生命周期
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.common_permission_description)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(
                            R.string.common_permission_granted,
                            (dialog, which) -> {
                                dialog.dismiss();
                                PermissionFragment.launch(
                                        activity,
                                        new ArrayList<>(allPermissions),
                                        PermissionInterceptor.this,
                                        callback);
                            })
                    .setNegativeButton(
                            R.string.common_permission_denied,
                            (dialog, which) -> {
                                dialog.dismiss();
                                if (callback == null) {
                                    return;
                                }
                                callback.onDenied(deniedPermissions, false);
                            })
                    .show();
        }
    }

    @Override
    public void deniedPermissionRequest(
            @NonNull Activity activity,
            @NonNull List<String> allPermissions,
            @NonNull List<String> deniedPermissions,
            boolean doNotAskAgain,
            @Nullable OnPermissionCallback callback) {
        if (callback != null) {
            callback.onDenied(deniedPermissions, doNotAskAgain);
        }

        if (doNotAskAgain) {
            if (deniedPermissions.size() == 1
                    && Permission.ACCESS_MEDIA_LOCATION.equals(deniedPermissions.get(0))) {
                ToastUtils.show(R.string.common_permission_media_location_hint_fail);
                return;
            }

            showPermissionSettingDialog(activity, allPermissions, deniedPermissions, callback);
            return;
        }

        if (deniedPermissions.size() == 1) {

            String deniedPermission = deniedPermissions.get(0);

            String backgroundPermissionOptionLabel = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                backgroundPermissionOptionLabel =
                        String.valueOf(
                                activity.getPackageManager().getBackgroundPermissionOptionLabel());
            }
            if (TextUtils.isEmpty(backgroundPermissionOptionLabel)) {
                backgroundPermissionOptionLabel =
                        activity.getString(
                                R.string.common_permission_background_default_option_label);
            }

            if (Permission.ACCESS_BACKGROUND_LOCATION.equals(deniedPermission)) {
                ToastUtils.show(
                        activity.getString(
                                R.string.common_permission_background_location_fail_hint,
                                backgroundPermissionOptionLabel));
                return;
            }

            if (Permission.BODY_SENSORS_BACKGROUND.equals(deniedPermission)) {
                ToastUtils.show(
                        activity.getString(
                                R.string.common_permission_background_sensors_fail_hint,
                                backgroundPermissionOptionLabel));
                return;
            }
        }

        final String message;
        List<String> permissionNames =
                PermissionNameConvert.permissionsToNames(activity, deniedPermissions);
        if (!permissionNames.isEmpty()) {
            message =
                    activity.getString(
                            R.string.common_permission_fail_assign_hint,
                            PermissionNameConvert.listToString(activity, permissionNames));
        } else {
            message = activity.getString(R.string.common_permission_fail_hint);
        }
        ToastUtils.show(message);
    }

    @Override
    public void finishPermissionRequest(
            @NonNull Activity activity,
            @NonNull List<String> allPermissions,
            boolean skipRequest,
            @Nullable OnPermissionCallback callback) {
        mRequestFlag = false;
        dismissPopupWindow();
    }

    private void showPopupWindow(Activity activity, ViewGroup decorView, String message) {
        if (mPermissionPopup == null) {
            View contentView =
                    LayoutInflater.from(activity)
                            .inflate(R.layout.permission_description_popup, decorView, false);
            mPermissionPopup = new PopupWindow(activity);
            mPermissionPopup.setContentView(contentView);
            mPermissionPopup.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            mPermissionPopup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            mPermissionPopup.setAnimationStyle(android.R.style.Animation_Dialog);
            mPermissionPopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPermissionPopup.setTouchable(true);
            mPermissionPopup.setOutsideTouchable(true);
        }
        TextView messageView =
                mPermissionPopup
                        .getContentView()
                        .findViewById(R.id.tv_permission_description_message);
        messageView.setText(message);
        // 注意：这里的 PopupWindow 只是示例，没有监听 Activity onDestroy 来处理 PopupWindow 生命周期
        mPermissionPopup.showAtLocation(decorView, Gravity.TOP, 0, 0);
    }

    private void dismissPopupWindow() {
        if (mPermissionPopup == null) {
            return;
        }
        if (!mPermissionPopup.isShowing()) {
            return;
        }
        mPermissionPopup.dismiss();
    }

    private void showPermissionSettingDialog(
            Activity activity,
            List<String> allPermissions,
            List<String> deniedPermissions,
            OnPermissionCallback callback) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }

        final String message;

        List<String> permissionNames =
                PermissionNameConvert.permissionsToNames(activity, deniedPermissions);
        if (!permissionNames.isEmpty()) {
            message =
                    activity.getString(
                            R.string.common_permission_manual_assign_fail_hint,
                            PermissionNameConvert.listToString(activity, permissionNames));
        } else {
            message = activity.getString(R.string.common_permission_manual_fail_hint);
        }

        // 这里的 Dialog 只是示例，没有用 DialogFragment 来处理 Dialog 生命周期
        new AlertDialog.Builder(activity)
                .setTitle(R.string.common_permission_alert)
                .setMessage(message)
                .setPositiveButton(
                        R.string.common_permission_goto_setting_page,
                        (dialog, which) -> {
                            dialog.dismiss();
                            XPermissions.startPermissionActivity(
                                    activity,
                                    deniedPermissions,
                                    new OnPermissionPageCallback() {

                                        @Override
                                        public void onGranted() {
                                            if (callback == null) {
                                                return;
                                            }
                                            callback.onGranted(allPermissions, true);
                                        }

                                        @Override
                                        public void onDenied() {
                                            showPermissionSettingDialog(
                                                    activity,
                                                    allPermissions,
                                                    XPermissions.getDenied(
                                                            activity, allPermissions),
                                                    callback);
                                        }
                                    });
                        }).show();
    }
}
