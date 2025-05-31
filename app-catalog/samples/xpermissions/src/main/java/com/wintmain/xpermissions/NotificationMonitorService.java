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

package com.wintmain.xpermissions;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import lib.wintmain.wToaster.toast.ToastUtils;

/**
 * 通知消息监控服务
 */
public final class NotificationMonitorService extends NotificationListenerService {

    /**
     * 当系统收到新的通知后出发回调
     */
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        Bundle extras = sbn.getNotification().extras;
        if (extras == null) {
            return;
        }

        // 获取通知消息标题
        String title = extras.getString(Notification.EXTRA_TITLE);
        // 获取通知消息内容
        Object msgText = extras.getCharSequence(Notification.EXTRA_TEXT);
        ToastUtils.show(
                String.format(
                        getString(R.string.demo_notification_listener_toast), title, msgText));
    }

    /**
     * 当系统通知被删掉后出发回调
     */
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }
}
