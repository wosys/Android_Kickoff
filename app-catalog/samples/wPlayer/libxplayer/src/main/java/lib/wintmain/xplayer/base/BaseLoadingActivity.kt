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

import android.os.Bundle
import android.widget.FrameLayout
import lib.wintmain.xplayer.common.dip2px
import lib.wintmain.xplayer.utils.StatusUtils
import lib.wintmain.xplayer.view.LoadingTip

/**
 * des 带loading的Activity
 */
abstract class BaseLoadingActivity : BaseVmActivity() {
    private var decorView: FrameLayout? = null
    var loadingTip: LoadingTip? = null

    override fun init(savedInstanceState: Bundle?) {
        decorView = window.decorView as FrameLayout
        val loadMarginTop = StatusUtils.getStatusBarHeight(this) + dip2px(this, 50f)
        val loadMarginBottom = StatusUtils.getNavigationBarHeight(this)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        params.topMargin = loadMarginTop
        params.bottomMargin = loadMarginBottom
        loadingTip = LoadingTip(this)
        decorView?.addView(loadingTip, params)
        init2(savedInstanceState)
    }

    abstract fun init2(savedInstanceState: Bundle?)
}