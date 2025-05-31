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

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.catalog.framework.annotations.Sample
import com.google.android.catalog.framework.ui.theme.CatalogTheme
import lib.wintmain.permissionbase.PermissionsBox

@Sample(
    name = "Call Notification",
    description = "如何发出来电通知和来电通知的示例。",
    documentation = "",
    tags = ["android-samples", "telephony"],
)
@RequiresApi(Build.VERSION_CODES.P)
class CallNotificationSample : AppCompatActivity() {

    private lateinit var notificationSource: NotificationSource<NotificationReceiver>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationSource = NotificationSource(this, NotificationReceiver::class.java)

        setContent {
            CatalogTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    color = Color.Transparent
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        // 多个权限申请，ANSWER_PHONE_CALLS在这里没有效果
                        val permissions = mutableListOf(Manifest.permission.ANSWER_PHONE_CALLS)
                        permissions.add(Manifest.permission.POST_NOTIFICATIONS)
                        PermissionsBox(permissions = permissions) {
                            EntryPoint(notificationSource)
                        }
                    } else {
                        EntryPoint(notificationSource)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NotificationSource.cancelNotification(this)
    }

    class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            intent?.let {
                val notificationStateValue = it.getIntExtra(
                    NotificationSource.NOTIFICATION_ACTION,
                    NotificationSource.Companion.NotificationState.CANCEL.ordinal
                )
                val message = when (notificationStateValue) {
                    NotificationSource.Companion.NotificationState.ANSWER.ordinal -> "Answered"
                    NotificationSource.Companion.NotificationState.REJECT.ordinal -> "Rejected"
                    else -> "Cancelled"
                }

                NotificationSource.cancelNotification(context)
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun EntryPoint(notificationSource: NotificationSource<CallNotificationSample.NotificationReceiver>) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(64.dp)
    ) {
        Button(onClick = { notificationSource.postIncomingCall() }) {
            Text(text = "模拟来电时的通知")
        }
        Button(onClick = { notificationSource.postOnGoingCall() }) {
            Text(text = "模拟通话中的通知")
        }
    }
}
