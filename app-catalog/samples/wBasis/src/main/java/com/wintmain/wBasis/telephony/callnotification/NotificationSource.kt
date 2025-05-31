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

package com.wintmain.wBasis.telephony.callnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.content.getSystemService
import com.wintmain.wBasis.R

/** Source for creating notifications and notification channels */
@RequiresApi(Build.VERSION_CODES.P)
class NotificationSource<T>(
    private val context: Context,
    private val notificationBroadcastClass: Class<T>
) {
    companion object {
        const val ChannelId = "5555"
        const val ChannelIntId = 5555

        const val ChannelOnGoingID = "6666"
        const val ChannelOnGoingIntID = 6666

        /* Notification state sent via in intent to inform receiving broadcast what action the user wants to take */
        enum class NotificationState(val value: Int) {
            ANSWER(0),
            REJECT(1),
            CANCEL(2),
            CLEARED(3);

            companion object {
                fun valueOf(value: Int) = NotificationState.values().find {
                    it.value == value
                }
            }
        }

        // Get ringtone location
        private val ringToneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        const val NOTIFICATION_ACTION = "NOTIFICATION_ACTION"

        // Channel for on going call, this has low importance so that the notification is not always shown
        private val notificationChannelOngoing = NotificationChannel(
            ChannelOnGoingID, "Call Notifications",
            NotificationManager.IMPORTANCE_LOW
        )

        // Notification channel for incoming calls.
        // Will play ringtone, will be no dismissible and will stay on screen until user interacts with notification.
        private fun notificationChannelIncoming(): NotificationChannel {
            val notification = NotificationChannel(
                ChannelId, "Call Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notification.importance = NotificationManager.IMPORTANCE_HIGH
            notification.setSound(
                ringToneUri,
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setLegacyStreamType(AudioManager.STREAM_RING)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .build()
            )

            return notification
        }

        private val fakeCaller = Person.Builder()
            .setName("wintmain")
            .setImportant(true)
            .build()

        /** Cancel notification and dismiss from sysUI */
        fun cancelNotification(context: Context) {
            context.getSystemService<NotificationManager>()?.cancel(ChannelIntId)
        }
    }

    private val notificationManager = context.getSystemService<NotificationManager>()!!

    // Intent and pending intent for full screen activity
    private val intent = Intent()
        .setAction(Intent.ACTION_MAIN)
        .setPackage(context.packageName)

    private val pendingIntent =
        PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    init {
        val managerCompat = NotificationManagerCompat.from(context)
        managerCompat.createNotificationChannels(
            listOf(
                notificationChannelIncoming(),
                notificationChannelOngoing,
            )
        )
    }

    /** Will post incoming call to sysUI */
    fun postIncomingCall() {
        notificationManager.notify(ChannelIntId, getIncomingCallNotification())
    }

    /**
     * Creates a notification for incoming calls.
     * This notification plays a ringtone and is not dismissible
     */
    private fun getIncomingCallNotification(): Notification {
        val receiveCallIntent = Intent(context, notificationBroadcastClass)
        receiveCallIntent.putExtra(NOTIFICATION_ACTION, NotificationState.ANSWER.ordinal)

        val cancelCallIntent = Intent(context, notificationBroadcastClass)
        cancelCallIntent.putExtra(NOTIFICATION_ACTION, NotificationState.REJECT.ordinal)

        val receiveCallPendingIntent = PendingIntent.getBroadcast(
            context, 1200, receiveCallIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val cancelCallPendingIntent = PendingIntent.getBroadcast(
            context, 1201, cancelCallIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        // https://developer.android.com/reference/android/app/Notification.CallStyle
        return NotificationCompat.Builder(context, ChannelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setFullScreenIntent(pendingIntent, true)
            .setSound(ringToneUri)
            .setOngoing(true)
            .setVisibility(VISIBILITY_PUBLIC)
            .setPriority(PRIORITY_MAX)
            .setStyle(
                NotificationCompat.CallStyle.forIncomingCall(
                    fakeCaller,
                    cancelCallPendingIntent,
                    receiveCallPendingIntent
                )
            ).build()
    }

    fun postOnGoingCall() {
        notificationManager.notify(ChannelIntId, getIncallNotification())
    }

    /** Creates an in call notification which is hidden but shows the user the state of the call */
    private fun getIncallNotification(): Notification {
        val cancelCallIntent = Intent(context, notificationBroadcastClass)
        cancelCallIntent.putExtra("message", "Call Rejected")
        cancelCallIntent.putExtra(NOTIFICATION_ACTION, NotificationState.CANCEL.ordinal)

        val cancelCallPendingIntent =
            PendingIntent.getBroadcast(
                context,
                1201,
                cancelCallIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

        return NotificationCompat.Builder(context, ChannelOnGoingID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setFullScreenIntent(pendingIntent, false)
            .setOngoing(true)
            .setStyle(
                NotificationCompat.CallStyle.forOngoingCall(
                    fakeCaller,
                    cancelCallPendingIntent
                )
            ).build()
    }

    /** Will cancel notification and dismiss from sysUI */
    fun cancelNotification() {
        notificationManager.cancel(ChannelIntId)
    }
}
