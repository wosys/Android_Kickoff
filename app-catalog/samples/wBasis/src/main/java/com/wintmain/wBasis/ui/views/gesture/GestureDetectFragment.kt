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

package com.wintmain.wBasis.ui.views.gesture

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MenuItem
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.wintmain.wBasis.R
import lib.wintmain.wBasis.logger.LogFragment

class GestureDetectFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // First create the GestureListener that will include all our callbacks.
        // Then create the GestureDetector, which takes that listener as an argument.
        val gestureListener: SimpleOnGestureListener = GestureListener()
        val gd = GestureDetector(activity, gestureListener)

        /* For the view where gestures will occur, create an onTouchListener that sends
         * all motion events to the gesture detector.  When the gesture detector
         * actually detects an event, it will use the callbacks you created in the
         * SimpleOnGestureListener to alert your application.
        */
        activity?.findViewById<TextView>(R.id.sample_output_gesture)?.apply {
            isClickable = true
            isFocusable = true
        }?.setOnTouchListener { _, motionEvent ->
            gd.onTouchEvent(motionEvent)
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sample_action_gesture) {
            clearLog()
        }
        return true
    }

    private fun clearLog() {
        val logFragment = (requireActivity().supportFragmentManager
            .findFragmentById(R.id.log_fragment_gesture) as LogFragment?)
        logFragment?.logView?.text = ""
    }
}
