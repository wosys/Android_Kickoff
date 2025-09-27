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

package com.wintmain.wBasis.gesture

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.wBasis.R
import lib.wintmain.wBasis.logger.Log
import lib.wintmain.wBasis.logger.LogFragment
import lib.wintmain.wBasis.logger.LogWrapper
import lib.wintmain.wBasis.logger.MessageOnlyLogFilter

@Sample(
    name = "Gesture Detect",
    description = "监听用户手势",
    tags = ["android-samples", "user-interface"]
)
class GestureDetectActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "GestureDetectActivity"
        private const val FRAG = "GestureDetectFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gesture_detect_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(GestureDetectFragment(), FRAG).commit()
    }

    override fun onStart() {
        super.onStart()
        initializeLogging()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.gesture_main, menu)
        return true
    }

    private fun initializeLogging() {
        // Wraps Android's native log framework.
        val logWrapper = LogWrapper()

        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper)

        // Filter strips out everything except the message text.
        val msgFilter = MessageOnlyLogFilter()
        logWrapper.next = msgFilter

        // On screen logging via a fragment with a TextView.
        val logFragment = supportFragmentManager
            .findFragmentById(R.id.log_fragment_gesture) as LogFragment?
        msgFilter.next = logFragment!!.logView
        logFragment.logView.setTextAppearance(this, R.style.Log)
        logFragment.logView.setBackgroundColor(Color.WHITE)
        Log.i(TAG, "Ready")
    }
}