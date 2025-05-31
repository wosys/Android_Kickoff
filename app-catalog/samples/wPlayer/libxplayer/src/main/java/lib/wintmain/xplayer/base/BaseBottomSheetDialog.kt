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

package lib.wintmain.xplayer.base

import android.content.Context
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import lib.wintmain.xplayer.R

/**
 * des 可滑动的dialog
 */
open class BaseBottomSheetDialog : BottomSheetDialogFragment() {
    private var bottomSheet: FrameLayout? = null
    private var behavior: BottomSheetBehavior<FrameLayout>? = null

    protected var fragmentActivity: FragmentActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentActivity) {
            fragmentActivity = context
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as BottomSheetDialog?
        bottomSheet =
            dialog?.delegate?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.let {
            val layoutParams = it.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.height = getHeight()
            it.layoutParams = layoutParams
            behavior = BottomSheetBehavior.from(it)
            behavior?.peekHeight = getHeight()
            // 初始为展开状态
            behavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    /**
     * 主题
     */
    override fun getTheme(): Int {
        return R.style.sheet_dialog_style
    }

    /**
     * 高度 子类可复写
     */
    protected fun getHeight(): Int {
        return (resources.displayMetrics.heightPixels.toFloat() * 0.7).toInt()
    }
}