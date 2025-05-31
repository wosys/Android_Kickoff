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
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import lib.wintmain.xplayer.utils.StatusUtils
import lib.wintmain.xplayer.view.LoadingTip

abstract class BaseLoadingFragment<BD : ViewDataBinding> : BaseVmFragment<BD>() {

    private var gloding: LoadingTip? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseLoadingActivity) {
            gloding = context.loadingTip
        }
    }

    /**
     * 设置loadingView上下变局
     */
    protected fun setLoadingMargin(topMargin: Int, bottomMargin: Int) {
        val loadMarginTop = StatusUtils.getStatusBarHeight(mActivity) + topMargin
        val loadMarginBottom = StatusUtils.getNavigationBarHeight(mActivity) + bottomMargin
        val params = gloding?.layoutParams as ViewGroup.MarginLayoutParams
        params.topMargin = loadMarginTop
        params.bottomMargin = loadMarginBottom
        gloding?.layoutParams = params
    }
}