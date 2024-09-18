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

package com.wintmain.xtele.deviceinfo;

import static android.telephony.SubscriptionManager.INVALID_SIM_SLOT_INDEX;
import static android.telephony.UiccSlotInfo.CARD_STATE_INFO_PRESENT;

import android.annotation.Nullable;
import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.UiccSlotInfo;
import android.text.BidiFormatter;
import android.text.TextDirectionHeuristics;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.telephony.MccTable;

import java.util.List;
import java.util.Locale;

public class Utils {
    private static final String TAG = "TelephonyDebugApp-Utils";

    /**
     * To get specific simSlot's SubscriptionInfo.
     *
     * @return Returns simSlot's SubscriptionInfo.
     */
    public static SubscriptionInfo getSubscriptionInfo(Context context, int simSlot) {
        final SubscriptionManager subManager = context.getSystemService(SubscriptionManager.class);
        final List<SubscriptionInfo> subscriptionInfoList =
                subManager.getActiveSubscriptionInfoList();
        if (subscriptionInfoList != null) {
            for (SubscriptionInfo info : subscriptionInfoList) {
                if (info.getSimSlotIndex() == simSlot) {
                    return info;
                }
            }
        }
        return null;
    }

    /**
     * To get the formatting text for display in a potentially opposite-directionality context
     * without garbling.
     *
     * @param subscriptionInfo {@link SubscriptionInfo} subscription information.
     * @return Returns phone number with Bidi format.
     */
    @Nullable
    public static String getBidiFormattedPhoneNumber(
            Context context, SubscriptionInfo subscriptionInfo) {
        String phoneNumber = getFormattedPhoneNumber(context, subscriptionInfo);
        return (phoneNumber == null)
                ? phoneNumber
                : BidiFormatter.getInstance().unicodeWrap(phoneNumber, TextDirectionHeuristics.LTR);
    }

    /** Returns the formatted phone number of a subscription. */
    @Nullable
    public static String getFormattedPhoneNumber(
            Context context, SubscriptionInfo subscriptionInfo) {
        if (subscriptionInfo == null) {
            Log.e(TAG, "Invalid subscription.");
            return null;
        }

        final SubscriptionManager subscriptionManager =
                context.getSystemService(SubscriptionManager.class);
        String rawPhoneNumber =
                subscriptionManager.getPhoneNumber(subscriptionInfo.getSubscriptionId());
        if (TextUtils.isEmpty(rawPhoneNumber)) {
            return null;
        }
        String countryIso =
                MccTable.countryCodeForMcc(subscriptionInfo.getMccString())
                        .toUpperCase(Locale.ROOT);
        return PhoneNumberUtils.formatNumber(rawPhoneNumber, countryIso);
    }

    public static boolean isInactiveInsertedPSim(UiccSlotInfo slotInfo) {
        if (slotInfo == null) {
            return false;
        }
        return !slotInfo.getIsEuicc()
                && !slotInfo.getPorts().stream().findFirst().get().isActive()
                && slotInfo.getCardStateInfo() == CARD_STATE_INFO_PRESENT;
    }

    public static String getDisplayName(SubscriptionInfo info) {
        final CharSequence name = info.getDisplayName();
        if (name != null) {
            return name.toString();
        }
        return "";
    }

    /**
     * Get phoneId or logical slot index for a subId if active, or INVALID_PHONE_INDEX if inactive.
     */
    public static int getPhoneId(Context context, int subId) {
        final SubscriptionManager subManager = context.getSystemService(SubscriptionManager.class);
        if (subManager == null) {
            return INVALID_SIM_SLOT_INDEX;
        }
        final SubscriptionInfo info = subManager.getActiveSubscriptionInfo(subId);
        if (info == null) {
            return INVALID_SIM_SLOT_INDEX;
        }
        return info.getSimSlotIndex();
    }

    public static int getDefaultVoiceSubscriptionId() {
        return SubscriptionManager.getDefaultVoiceSubscriptionId();
    }

    public static int getDefaultSmsSubscriptionId() {
        return SubscriptionManager.getDefaultSmsSubscriptionId();
    }

    public static int getDefaultDataSubscriptionId() {
        return SubscriptionManager.getDefaultDataSubscriptionId();
    }
}
