/*
 * Copyright 2023-2025 wintmain
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

package com.wintmain.wBasis.ui.compose

import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsCompat
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.wBasis.R

@Sample(
    name = "Compose Activity",
    description = "Compose kickoff",
    documentation = "",
    tags = ["android-samples", "user-interface"],
)
class ComposeActivity : ComponentActivity() {
    @RequiresApi(VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.insetsController?.apply {
            // 设置window的一些参数可以调整状态栏和导航栏的显示
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        setContent {
            Android_KickoffTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting(
//                        message = "Happy Birthday!",
//                        from = "From wintmain",
//                        modifier = Modifier.padding(8.dp)
//                    )
                    GreetingImage(
                        message = "Happy Birthday",
                        from = "From wintmain",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun Greeting(message: String, from: String, modifier: Modifier = Modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            Text(
                text = message,
                fontSize = 60.sp,
                lineHeight = 60.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            Text(
                text = from,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(12.dp)
                    .align(alignment = Alignment.End)
            )
        }
    }

    @Composable
    fun GreetingImage(message: String, from: String, modifier: Modifier = Modifier) {
        val image = painterResource(R.drawable.androidparty)
        Box(modifier) {
            Image(
                painter = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = 0.5f
            )
            Greeting(
                message = message,
                from = from,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Android_KickoffTheme {
//            Greeting(
//                message = "Happy Birthday",
//                from = "from wintmain"
//            )
            GreetingImage(
                message = "Happy Birthday",
                from = "from wintmain"
            )
        }
    }
}