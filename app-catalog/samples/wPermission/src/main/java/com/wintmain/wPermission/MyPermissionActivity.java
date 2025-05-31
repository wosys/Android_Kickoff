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

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.catalog.framework.annotations.Sample;
import lib.wintmain.wToaster.toast.ToastUtils;
import lib.wintmain.wPermission.OnPermissionCallback;
import lib.wintmain.wPermission.Permission;
import lib.wintmain.wPermission.XPermissions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @Description 权限列表
 * @Author wintmain
 * @mailto wosintmain@gmail.com
 * @Date 2023-10-30 22:37:07
 */
@Sample(
        name = "xpermissions",
        description = "权限例子",
        documentation = "",
        //    owners = ["wintmain"],
        tags = "A-Self_demos")
public class MyPermissionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MyPermissionActivity";
    private final SensorEventListener mSensorEventListener =
            new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    Log.w(TAG, "onSensorChanged event = " + event);
                    switch (event.sensor.getType()) {
                        case Sensor.TYPE_STEP_COUNTER -> Log.w(TAG,
                                "开机以来当天总步数：" + event.values[0]);
                        case Sensor.TYPE_STEP_DETECTOR -> {
                            if (event.values[0] == 1) {
                                Log.w(TAG, "当前走了一步");
                            }
                        }
                        default -> {
                        }
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    Log.w(TAG, "onAccuracyChanged" + accuracy);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_main);
        ToastUtils.init(this.getApplication());

        findViewById(R.id.btn_main_request_single_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_group_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_location_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_sensors_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_activity_recognition_permission)
                .setOnClickListener(this);
        findViewById(R.id.btn_main_request_bluetooth_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_wifi_devices_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_read_media_location_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_read_media_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_manage_storage_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_install_packages_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_system_alert_window_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_write_settings_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_notification_service_permission)
                .setOnClickListener(this);
        findViewById(R.id.btn_main_request_post_notification).setOnClickListener(this);
        findViewById(R.id.btn_main_request_bind_notification_listener_permission)
                .setOnClickListener(this);
        findViewById(R.id.btn_main_request_usage_stats_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_schedule_exact_alarm_permission)
                .setOnClickListener(this);
        findViewById(R.id.btn_main_request_access_notification_policy_permission)
                .setOnClickListener(this);
        findViewById(R.id.btn_main_request_ignore_battery_optimizations_permission)
                .setOnClickListener(this);
        findViewById(R.id.btn_main_request_picture_in_picture_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_bind_vpn_service_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_request_get_installed_apps_permission).setOnClickListener(this);
        findViewById(R.id.btn_main_start_permission_activity).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_main_request_single_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.CAMERA)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            new OnPermissionCallback() {

                                @Override
                                public void onGranted(
                                        @NonNull List<String> permissions, boolean allGranted) {
                                    if (!allGranted) {
                                        return;
                                    }
                                    toast(
                                            String.format(
                                                    getString(
                                                            R.string
                                                                    .demo_obtain_permission_success_hint),
                                                    PermissionNameConvert.getPermissionString(
                                                            MyPermissionActivity.this,
                                                            permissions)));
                                }
                            });

        } else if (viewId == R.id.btn_main_request_group_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.RECORD_AUDIO)
                    .permission(Permission.Group.CALENDAR)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                            });

        } else if (viewId == R.id.btn_main_request_location_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.ACCESS_COARSE_LOCATION)
                    .permission(Permission.ACCESS_FINE_LOCATION)
                    // 如果不需要在后台使用定位功能，请不要申请此权限
                    .permission(Permission.ACCESS_BACKGROUND_LOCATION)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                            });

        } else if (viewId == R.id.btn_main_request_sensors_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.BODY_SENSORS)
                    .permission(Permission.BODY_SENSORS_BACKGROUND)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                            });

        } else if (viewId == R.id.btn_main_request_activity_recognition_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.ACTIVITY_RECOGNITION)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                                addCountStepListener();
                            });

        } else if (viewId == R.id.btn_main_request_bluetooth_permission) {

            long delayMillis = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                delayMillis = 2000;
                toast(getString(R.string.demo_android_12_bluetooth_permission_hint));
            }

            view.postDelayed(
                    new Runnable() {

                        @Override
                        public void run() {
                            XPermissions.with(MyPermissionActivity.this)
                                    .permission(Permission.BLUETOOTH_SCAN)
                                    .permission(Permission.BLUETOOTH_CONNECT)
                                    .permission(Permission.BLUETOOTH_ADVERTISE)
                                    .interceptor(new PermissionInterceptor())
                                    .request(
                                            (permissions, allGranted) -> {
                                                if (!allGranted) {
                                                    return;
                                                }
                                                toast(
                                                        String.format(
                                                                getString(
                                                                        R.string
                                                                                .demo_obtain_permission_success_hint),
                                                                PermissionNameConvert
                                                                        .getPermissionString(
                                                                                MyPermissionActivity
                                                                                        .this,
                                                                                permissions)));
                                            });
                        }
                    },
                    delayMillis);

        } else if (viewId == R.id.btn_main_request_wifi_devices_permission) {

            long delayMillis = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                delayMillis = 2000;
                toast(getString(R.string.demo_android_13_wifi_permission_hint));
            }

            view.postDelayed(
                    () ->
                            XPermissions.with(MyPermissionActivity.this)
                                    .permission(Permission.NEARBY_WIFI_DEVICES)
                                    .interceptor(new PermissionInterceptor())
                                    .request(
                                            new OnPermissionCallback() {

                                                @Override
                                                public void onGranted(
                                                        @NonNull List<String> permissions,
                                                        boolean allGranted) {
                                                    if (!allGranted) {
                                                        return;
                                                    }
                                                    toast(
                                                            String.format(
                                                                    getString(
                                                                            R.string
                                                                                    .demo_obtain_permission_success_hint),
                                                                    PermissionNameConvert
                                                                            .getPermissionString(
                                                                                    MyPermissionActivity
                                                                                            .this,
                                                                                    permissions)));
                                                }
                                            }),
                    delayMillis);

        } else if (viewId == R.id.btn_main_request_read_media_location_permission) {

            long delayMillis = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                delayMillis = 2000;
                toast(getString(R.string.demo_android_10_read_media_location_permission_hint));
            }

            view.postDelayed(
                    new Runnable() {

                        @Override
                        public void run() {
                            XPermissions.with(MyPermissionActivity.this)
                                    // Permission.READ_EXTERNAL_STORAGE 和
                                    // Permission.MANAGE_EXTERNAL_STORAGE 二选一
                                    // 如果 targetSdk >= 33，则添加 Permission.READ_MEDIA_IMAGES 和
                                    // Permission.MANAGE_EXTERNAL_STORAGE 二选一
                                    // 如果 targetSdk < 33，则添加 Permission.READ_EXTERNAL_STORAGE 和
                                    // Permission.MANAGE_EXTERNAL_STORAGE 二选一
                                    .permission(Permission.READ_MEDIA_IMAGES)
                                    .permission(Permission.ACCESS_MEDIA_LOCATION)
                                    .interceptor(new PermissionInterceptor())
                                    .request(
                                            (permissions, allGranted) -> {
                                                if (!allGranted) {
                                                    return;
                                                }
                                                toast(
                                                        String.format(
                                                                getString(
                                                                        R.string
                                                                                .demo_obtain_permission_success_hint),
                                                                PermissionNameConvert
                                                                        .getPermissionString(
                                                                                MyPermissionActivity
                                                                                        .this,
                                                                                permissions)));
                                                new Thread(
                                                        new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                getAllImagesFromGallery();
                                                            }
                                                        })
                                                        .start();
                                            });
                        }
                    },
                    delayMillis);

        } else if (viewId == R.id.btn_main_request_read_media_permission) {

            long delayMillis = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                delayMillis = 2000;
                toast(getString(R.string.demo_android_13_read_media_permission_hint));
            }

            view.postDelayed(
                    new Runnable() {

                        @Override
                        public void run() {
                            XPermissions.with(MyPermissionActivity.this)
                                    // 不适配分区存储应该这样写
                                    // .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                                    // 适配分区存储应该这样写
                                    .permission(Permission.READ_MEDIA_IMAGES)
                                    .permission(Permission.READ_MEDIA_VIDEO)
                                    .permission(Permission.READ_MEDIA_AUDIO)
                                    .interceptor(new PermissionInterceptor())
                                    .request(
                                            (permissions, allGranted) -> {
                                                if (!allGranted) {
                                                    return;
                                                }
                                                toast(
                                                        String.format(
                                                                getString(
                                                                        R.string
                                                                                .demo_obtain_permission_success_hint),
                                                                PermissionNameConvert
                                                                        .getPermissionString(
                                                                                MyPermissionActivity
                                                                                        .this,
                                                                                permissions)));
                                            });
                        }
                    },
                    delayMillis);

        } else if (viewId == R.id.btn_main_request_manage_storage_permission) {

            long delayMillis = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                delayMillis = 2000;
                toast(getString(R.string.demo_android_11_manage_storage_permission_hint));
            }

            view.postDelayed(
                    () ->
                            XPermissions.with(MyPermissionActivity.this)
                                    // 适配分区存储应该这样写
                                    // .permission(Permission.Group.STORAGE)
                                    // 不适配分区存储应该这样写
                                    .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                                    .interceptor(new PermissionInterceptor())
                                    .request(
                                            (permissions, allGranted) -> {
                                                if (!allGranted) {
                                                    return;
                                                }
                                                toast(
                                                        String.format(
                                                                getString(
                                                                        R.string
                                                                                .demo_obtain_permission_success_hint),
                                                                PermissionNameConvert
                                                                        .getPermissionString(
                                                                                MyPermissionActivity
                                                                                        .this,
                                                                                permissions)));
                                            }),
                    delayMillis);

        } else if (viewId == R.id.btn_main_request_install_packages_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.REQUEST_INSTALL_PACKAGES)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                            });

        } else if (viewId == R.id.btn_main_request_system_alert_window_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.SYSTEM_ALERT_WINDOW)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                            });

        } else if (viewId == R.id.btn_main_request_write_settings_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.WRITE_SETTINGS)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                            });

        } else if (viewId == R.id.btn_main_request_notification_service_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.NOTIFICATION_SERVICE)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                            });

        } else if (viewId == R.id.btn_main_request_post_notification) {

            long delayMillis = 0;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                delayMillis = 2000;
                toast(getString(R.string.demo_android_13_post_notification_permission_hint));
            }

            view.postDelayed(
                    () ->
                            XPermissions.with(MyPermissionActivity.this)
                                    .permission(Permission.POST_NOTIFICATIONS)
                                    .interceptor(new PermissionInterceptor())
                                    .request(
                                            (permissions, allGranted) -> {
                                                if (!allGranted) {
                                                    return;
                                                }
                                                toast(
                                                        String.format(
                                                                getString(
                                                                        R.string
                                                                                .demo_obtain_permission_success_hint),
                                                                PermissionNameConvert
                                                                        .getPermissionString(
                                                                                MyPermissionActivity
                                                                                        .this,
                                                                                permissions)));
                                            }),
                    delayMillis);

        } else if (viewId == R.id.btn_main_request_bind_notification_listener_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.BIND_NOTIFICATION_LISTENER_SERVICE)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                                toggleNotificationListenerService();
                            });

        } else if (viewId == R.id.btn_main_request_usage_stats_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.PACKAGE_USAGE_STATS)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                            });

        } else if (viewId == R.id.btn_main_request_schedule_exact_alarm_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.SCHEDULE_EXACT_ALARM)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                            });

        } else if (viewId == R.id.btn_main_request_access_notification_policy_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.ACCESS_NOTIFICATION_POLICY)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                            });

        } else if (viewId == R.id.btn_main_request_ignore_battery_optimizations_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                            });

        } else if (viewId == R.id.btn_main_request_picture_in_picture_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.PICTURE_IN_PICTURE)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                            });

        } else if (viewId == R.id.btn_main_request_bind_vpn_service_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.BIND_VPN_SERVICE)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                            });

        } else if (viewId == R.id.btn_main_request_get_installed_apps_permission) {

            XPermissions.with(MyPermissionActivity.this)
                    .permission(Permission.GET_INSTALLED_APPS)
                    .interceptor(new PermissionInterceptor())
                    .request(
                            (permissions, allGranted) -> {
                                if (!allGranted) {
                                    return;
                                }
                                toast(
                                        String.format(
                                                getString(
                                                        R.string
                                                                .demo_obtain_permission_success_hint),
                                                PermissionNameConvert.getPermissionString(
                                                        MyPermissionActivity.this, permissions)));
                                getAppList();
                            });

        } else if (viewId == R.id.btn_main_start_permission_activity) {

            XPermissions.startPermissionActivity(MyPermissionActivity.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != XPermissions.REQUEST_CODE) {
            return;
        }
        toast(getString(R.string.demo_return_activity_result_hint));
    }

    public void toast(CharSequence text) {
        ToastUtils.show(text);
    }

    private void toggleNotificationListenerService() {
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(
                new ComponentName(this, NotificationMonitorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        packageManager.setComponentEnabledSetting(
                new ComponentName(this, NotificationMonitorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 获取所有图片
     */
    private void getAllImagesFromGallery() {
        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.MediaColumns.TITLE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.ImageColumns.LATITUDE,
                MediaStore.Images.ImageColumns.LONGITUDE
        };

        final String orderBy = MediaStore.Video.Media.DATE_TAKEN;
        Cursor cursor =
                getApplicationContext()
                        .getContentResolver()
                        .query(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                projection,
                                null,
                                null,
                                orderBy + " DESC");

        int idIndex = ((Cursor) Objects.requireNonNull(cursor)).getColumnIndexOrThrow(
                MediaStore.MediaColumns._ID);
        int pathIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {

            String filePath = cursor.getString(pathIndex);

            float[] latLong = new float[2];

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // 谷歌官方文档：https://developer.android.google.cn/training/data-storage/shared/media?hl=zh-cn#location-media-captured
                Uri photoUri =
                        Uri.withAppendedPath(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                cursor.getString(idIndex));
                photoUri = MediaStore.setRequireOriginal(photoUri);
                try {
                    InputStream inputStream =
                            getApplicationContext().getContentResolver().openInputStream(photoUri);
                    if (inputStream == null) {
                        continue;
                    }
                    ExifInterface exifInterface = new ExifInterface(inputStream);
                    // 获取图片的经纬度
                    exifInterface.getLatLong(latLong);
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (UnsupportedOperationException e) {
                    // java.lang.UnsupportedOperationException:
                    // Caller must hold ACCESS_MEDIA_LOCATION permission to access original
                    // 经过测试，在部分手机上面申请获取媒体位置权限，如果用户选择的是 "仅在使用中允许"
                    // 那么就会导致权限是授予状态，但是调用 openInputStream 时会抛出此异常
                    e.printStackTrace();
                }
            } else {
                int latitudeIndex =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LATITUDE);
                int longitudeIndex =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.LONGITUDE);
                latLong =
                        new float[]{
                                cursor.getFloat(latitudeIndex), cursor.getFloat(longitudeIndex)
                        };
            }

            if (latLong[0] != 0 && latLong[1] != 0) {
                Log.i(TAG, "获取到图片的经纬度：" + filePath + "，" + Arrays.toString(latLong));
                Log.i(TAG,
                        "图片经纬度所在的地址：" + latLongToAddressString(latLong[0], latLong[1]));
            } else {
                Log.i(TAG, "该图片获取不到经纬度：" + filePath);
            }
        }
        cursor.close();
    }

    /**
     * 将经纬度转换成地址
     */
    private String latLongToAddressString(float latitude, float longitude) {
        String addressString = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                addressString = strReturnedAddress.toString();
            } else {
                Log.w(TAG, "没有返回地址");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "无法获取到地址");
        }
        return addressString;
    }

    /**
     * 添加步数监听
     */
    private void addCountStepListener() {
        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor stepSensor = manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor = manager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if (stepSensor != null) {
            manager.registerListener(
                    mSensorEventListener, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (detectorSensor != null) {
            manager.registerListener(
                    mSensorEventListener, detectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void getAppList() {
        try {
            PackageManager packageManager = getPackageManager();
            int flags = PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES;
            List<PackageInfo> packageInfoList;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageInfoList =
                        packageManager.getInstalledPackages(
                                PackageManager.PackageInfoFlags.of(flags));
            } else {
                packageInfoList = packageManager.getInstalledPackages(flags);
            }

            for (PackageInfo info : packageInfoList) {
                Log.i(TAG, "应用包名：" + info.packageName);
            }
        } catch (Throwable t) {
            t.printStackTrace();
            ;
        }
    }
}
