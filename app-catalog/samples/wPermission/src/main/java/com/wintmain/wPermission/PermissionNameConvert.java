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

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import lib.wintmain.wPermission.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限名称转换器
 */
public final class PermissionNameConvert {

    /**
     * 获取权限名称
     */
    public static String getPermissionString(Context context, List<String> permissions) {
        return listToString(context, permissionsToNames(context, permissions));
    }

    /**
     * String 列表拼接成一个字符串
     */
    public static String listToString(Context context, List<String> hints) {
        if (hints == null || hints.isEmpty()) {
            return context.getString(R.string.common_permission_unknown);
        }

        StringBuilder builder = new StringBuilder();
        for (String text : hints) {
            if (builder.length() == 0) {
                builder.append(text);
            } else {
                builder.append("、").append(text);
            }
        }
        return builder.toString();
    }

    /**
     * 将权限列表转换成对应名称列表
     */
    @NonNull
    public static List<String> permissionsToNames(Context context, List<String> permissions) {
        List<String> permissionNames = new ArrayList<>();
        if (context == null) {
            return permissionNames;
        }
        if (permissions == null) {
            return permissionNames;
        }
        for (String permission : permissions) {
            switch (permission) {
                case Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE -> {
                    String hint = context.getString(R.string.common_permission_storage);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.READ_MEDIA_IMAGES, Permission.READ_MEDIA_VIDEO -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        String hint =
                                context.getString(R.string.common_permission_image_and_video);
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint);
                        }
                    }
                }
                case Permission.READ_MEDIA_AUDIO -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        String hint =
                                context.getString(R.string.common_permission_music_and_audio);
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint);
                        }
                    }
                }
                case Permission.CAMERA -> {
                    String hint = context.getString(R.string.common_permission_camera);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.RECORD_AUDIO -> {
                    String hint = context.getString(R.string.common_permission_microphone);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION,
                        Permission.ACCESS_BACKGROUND_LOCATION -> {
                    String hint;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                            && !permissions.contains(Permission.ACCESS_FINE_LOCATION)
                            && !permissions.contains(Permission.ACCESS_COARSE_LOCATION)) {
                        hint =
                                context.getString(
                                        R.string.common_permission_location_background);
                    } else {
                        hint = context.getString(R.string.common_permission_location);
                    }
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.BODY_SENSORS, Permission.BODY_SENSORS_BACKGROUND -> {
                    String hint;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                            && !permissions.contains(Permission.BODY_SENSORS)) {
                        hint =
                                context.getString(
                                        R.string.common_permission_body_sensors_background);
                    } else {
                        hint = context.getString(R.string.common_permission_body_sensors);
                    }
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.BLUETOOTH_SCAN, Permission.BLUETOOTH_CONNECT,
                        Permission.BLUETOOTH_ADVERTISE -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        String hint =
                                context.getString(R.string.common_permission_nearby_devices);
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint);
                        }
                    }
                }
                case Permission.NEARBY_WIFI_DEVICES -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        String hint =
                                context.getString(R.string.common_permission_nearby_devices);
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint);
                        }
                    }
                }
                case Permission.READ_PHONE_STATE, Permission.CALL_PHONE, Permission.ADD_VOICEMAIL
                        , Permission.USE_SIP, Permission.READ_PHONE_NUMBERS,
                        Permission.ANSWER_PHONE_CALLS -> {
                    String hint = context.getString(R.string.common_permission_phone);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.GET_ACCOUNTS, Permission.READ_CONTACTS, Permission.WRITE_CONTACTS -> {
                    String hint = context.getString(R.string.common_permission_contacts);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.READ_CALENDAR, Permission.WRITE_CALENDAR -> {
                    String hint = context.getString(R.string.common_permission_calendar);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.READ_CALL_LOG, Permission.WRITE_CALL_LOG -> {
                    String hint =
                            context.getString(
                                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                                            ? R.string.common_permission_call_logs
                                            : R.string.common_permission_phone);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.ACTIVITY_RECOGNITION -> {
                    String hint =
                            context.getString(
                                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
                                            ? R.string
                                            .common_permission_activity_recognition_api30
                                            : R.string
                                                    .common_permission_activity_recognition_api29);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.ACCESS_MEDIA_LOCATION -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        String hint =
                                context.getString(
                                        R.string.common_permission_access_media_location);
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint);
                        }
                    }
                }
                case Permission.SEND_SMS, Permission.RECEIVE_SMS, Permission.READ_SMS,
                        Permission.RECEIVE_WAP_PUSH, Permission.RECEIVE_MMS -> {
                    String hint = context.getString(R.string.common_permission_sms);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.MANAGE_EXTERNAL_STORAGE -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        String hint =
                                context.getString(R.string.common_permission_all_file_access);
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint);
                        }
                    }
                }
                case Permission.REQUEST_INSTALL_PACKAGES -> {
                    String hint =
                            context.getString(R.string.common_permission_install_unknown_apps);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.SYSTEM_ALERT_WINDOW -> {
                    String hint =
                            context.getString(
                                    R.string.common_permission_display_over_other_apps);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.WRITE_SETTINGS -> {
                    String hint =
                            context.getString(
                                    R.string.common_permission_modify_system_settings);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.NOTIFICATION_SERVICE -> {
                    String hint =
                            context.getString(R.string.common_permission_allow_notifications);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.POST_NOTIFICATIONS -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        String hint =
                                context.getString(
                                        R.string.common_permission_post_notifications);
                        if (!permissionNames.contains(hint)) {
                            permissionNames.add(hint);
                        }
                    }
                }
                case Permission.BIND_NOTIFICATION_LISTENER_SERVICE -> {
                    String hint =
                            context.getString(
                                    R.string.common_permission_allow_notifications_access);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.PACKAGE_USAGE_STATS -> {
                    String hint =
                            context.getString(
                                    R.string.common_permission_apps_with_usage_access);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.SCHEDULE_EXACT_ALARM -> {
                    String hint =
                            context.getString(R.string.common_permission_alarms_reminders);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.ACCESS_NOTIFICATION_POLICY -> {
                    String hint =
                            context.getString(R.string.common_permission_do_not_disturb_access);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS -> {
                    String hint =
                            context.getString(
                                    R.string.common_permission_ignore_battery_optimize);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.BIND_VPN_SERVICE -> {
                    String hint = context.getString(R.string.common_permission_vpn);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.PICTURE_IN_PICTURE -> {
                    String hint =
                            context.getString(R.string.common_permission_picture_in_picture);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                case Permission.GET_INSTALLED_APPS -> {
                    String hint =
                            context.getString(R.string.common_permission_get_installed_apps);
                    if (!permissionNames.contains(hint)) {
                        permissionNames.add(hint);
                    }
                }
                default -> {
                }
            }
        }

        return permissionNames;
    }
}
