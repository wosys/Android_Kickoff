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

package com.wintmain.deviceinfo;

import android.content.Context;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;

import com.android.settingslib.DeviceInfoUtils;
import com.android.settings.network.SubscriptionUtil;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.List;

public class Utils {
    private static final String TAG = "DeviceInfo-Utils";

    /**
     * Get phone number.
     *
     * @Param simSlot sim slot
     * @return Returns simSlot's phone number.
     */
    public static String getPhoneNumber(Context context, SubscriptionManager mSubscriptionManager,
            int simSlot) {
        final List<SubscriptionInfo> subscriptionInfoList =
                mSubscriptionManager.getActiveSubscriptionInfoList();
        if (subscriptionInfoList != null) {
            for (SubscriptionInfo info : subscriptionInfoList) {
                if (info.getSimSlotIndex() == simSlot) {
                    // Flag, use Settings-core.
                    String phoneNumber = SubscriptionUtil.getBidiFormattedPhoneNumber(context,
                            info);
                    return phoneNumber;
                }
            }
        }
        return "";
    }

    /**
     * Get device model.
     *
     * @return Returns device model.
     */
    public static String getDeviceModel() {
        // Flag, Use SettingLib.
        FutureTask<String> msvSuffixTask = new FutureTask<>(() -> DeviceInfoUtils.getMsvSuffix());
        msvSuffixTask.run();

        try {
            // Wait for msv suffix value.
            final String msvSuffix = msvSuffixTask.get();
            return Build.MODEL + msvSuffix;
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "Execution or Interruption error, so we only show model name");
            // return ""; --> unreachable statement for return Build.MODEL;
        }
        // If we can't get an msv suffix value successfully,
        // it's better to return model name.
        return Build.MODEL;
    }
}
