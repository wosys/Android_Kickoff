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
package com.wintmain.foundation.ui.views.recyclerview

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ViewAnimator
import androidx.appcompat.app.AppCompatActivity
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.foundation.R
import lib.wintmain.foundation.logger.Log
import lib.wintmain.foundation.logger.LogFragment
import lib.wintmain.foundation.logger.LogWrapper
import lib.wintmain.foundation.logger.MessageOnlyLogFilter

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * [androidx.fragment.app.Fragment] which can display a view.
 */
@Sample(
    name = "RecyclerView",
    description = "使用 RecyclerView 创建动态列表",
    tags = ["android-samples", "user-interface"]
)
class RecyclerViewActivity : AppCompatActivity() {
    companion object {
        const val TAG: String = "RecyclerViewActivity"
    }

    private var mLogShown = false // Whether the Log Fragment is currently shown

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_activity_main)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = RecyclerViewFragment()
            transaction.replace(R.id.sample_content_fragment, fragment).commit()
        }
    }

    override fun onStart() {
        super.onStart()
        initializeLogging()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.recycler_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.menu_toggle_log).apply {
            isVisible = true
            title = if (mLogShown) "sample_hide_log" else "sample_show_log"
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_toggle_log) {
            mLogShown = !mLogShown
            val output = findViewById<ViewAnimator>(R.id.sample_output)
            if (mLogShown) {
                output.displayedChild = 1
            } else {
                output.displayedChild = 0
            }
            supportInvalidateOptionsMenu()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Create a chain of targets that will receive log data
     */
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
            .findFragmentById(R.id.log_fragment) as LogFragment
        msgFilter.next = logFragment.logView

        Log.i(TAG, "Ready...RecyclerViewActivity")
    }
}