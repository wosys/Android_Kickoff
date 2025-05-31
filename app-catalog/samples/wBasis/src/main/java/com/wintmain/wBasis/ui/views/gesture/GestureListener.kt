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

import android.os.Build.VERSION
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import lib.wintmain.wBasis.logger.Log

class GestureListener : SimpleOnGestureListener() {
    companion object {
        const val TAG: String = "GestureListener"

        /**
         * Returns a human-readable string describing the type of touch that triggered a MotionEvent.
         */
        private fun getTouchType(e: MotionEvent?): String {
            var touchTypeDescription = ""
            val touchType = e!!.getToolType(0)

            when (touchType) {
                MotionEvent.TOOL_TYPE_FINGER -> touchTypeDescription += "(finger)"
                MotionEvent.TOOL_TYPE_STYLUS -> {
                    touchTypeDescription += "(stylus, "
                    //Get some additional information about the stylus touch
                    val stylusPressure = e.pressure
                    touchTypeDescription += "pressure: $stylusPressure"

                    if (VERSION.SDK_INT >= 21) {
                        touchTypeDescription += ", buttons pressed: " + getButtonsPressed(e)
                    }

                    touchTypeDescription += ")"
                }

                MotionEvent.TOOL_TYPE_ERASER -> touchTypeDescription += "(eraser)"
                MotionEvent.TOOL_TYPE_MOUSE -> touchTypeDescription += "(mouse)"
                else -> touchTypeDescription += "(unknown tool)"
            }

            return touchTypeDescription
        }

        /**
         * Returns a human-readable string listing all the stylus buttons that were pressed when the
         * input MotionEvent occurred.
         */
        private fun getButtonsPressed(e: MotionEvent?): String {
            var buttons = ""
            if (e == null) return ""

            if (e.isButtonPressed(MotionEvent.BUTTON_PRIMARY)) {
                buttons += " primary"
            }

            if (e.isButtonPressed(MotionEvent.BUTTON_SECONDARY)) {
                buttons += " secondary"
            }

            if (e.isButtonPressed(MotionEvent.BUTTON_TERTIARY)) {
                buttons += " tertiary"
            }

            if (e.isButtonPressed(MotionEvent.BUTTON_BACK)) {
                buttons += " back"
            }

            if (e.isButtonPressed(MotionEvent.BUTTON_FORWARD)) {
                buttons += " forward"
            }

            if (buttons == "") {
                buttons = "none"
            }

            return buttons
        }
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        // Up motion completing a single tap occurred.
        Log.i(TAG, "Single Tap Up" + getTouchType(e))
        return false
    }

    override fun onLongPress(e: MotionEvent) {
        // Touch has been long enough to indicate a long press.
        // Does not indicate motion is complete yet (no up event necessarily)
        Log.i(TAG, "Long Press" + getTouchType(e))
    }

    override fun onScroll(
        e1: MotionEvent?, e2: MotionEvent, distanceX: Float,
        distanceY: Float
    ): Boolean {
        // User attempted to scroll
        Log.i(TAG, "Scroll" + getTouchType(e1))
        return false
    }

    override fun onFling(
        e1: MotionEvent?, e2: MotionEvent, velocityX: Float,
        velocityY: Float
    ): Boolean {
        // Fling event occurred.  Notification of this one happens after an "up" event.
        Log.i(TAG, "Fling" + getTouchType(e1))
        return false
    }

    override fun onShowPress(e: MotionEvent) {
        // User performed a down event, and hasn't moved yet.
        Log.i(TAG, "Show Press" + getTouchType(e))
    }

    override fun onDown(e: MotionEvent): Boolean {
        // "Down" event - User touched the screen.
        Log.i(TAG, "Down" + getTouchType(e))
        return false
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        // User tapped the screen twice.
        Log.i(TAG, "Double tap" + getTouchType(e))
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        // Since double-tap is actually several events which are considered one aggregate
        // gesture, there's a separate callback for an individual event within the doubletap
        // occurring.  This occurs for down, up, and move.
        Log.i(TAG, "Event within double tap" + getTouchType(e))
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        // A confirmed single-tap event has occurred.  Only called when the detector has
        // determined that the first tap stands alone, and is not part of a double tap.
        Log.i(TAG, "Single tap confirmed" + getTouchType(e))
        return false
    }
}