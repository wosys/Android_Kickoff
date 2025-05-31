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

package lib.wintmain.xplayer.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.wang.avi.AVLoadingIndicatorView
import lib.wintmain.xplayer.R

/**
 * des 辅助站位图
 */
class LoadingTip : RelativeLayout {

    private var llEmpty: LinearLayout? = null
    private var indicatorView: AVLoadingIndicatorView? = null
    private var llInternetError: LinearLayout? = null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    private fun initView(context: Context) {
        val view = View.inflate(context, R.layout.loading_tip, this)
        llEmpty = view.findViewById(R.id.llEmpty)
        indicatorView = view.findViewById(R.id.indicatorView)
        llInternetError = view.findViewById(R.id.llInternetError)
        visibility = View.GONE
    }

    /**
     * 设置网络重连点击事件
     */
    fun setReloadListener(reload: (View) -> Unit) {
        llInternetError?.setOnClickListener {
            reload.invoke(it)
        }
    }

    /**
     * 显示空白页
     */
    fun showEmpty() {
        visibility = View.VISIBLE
        llEmpty?.visibility = View.VISIBLE
        indicatorView?.visibility = View.GONE
        indicatorView?.hide()
        llInternetError?.visibility = View.GONE
    }

    /**
     * 显示网络错误
     */
    fun showInternetError() {
        visibility = View.VISIBLE
        llInternetError?.visibility = View.VISIBLE
        llEmpty?.visibility = View.GONE
        indicatorView?.visibility = View.GONE
        indicatorView?.hide()
    }

    /**
     * 加载
     */
    fun loading() {
        visibility = View.VISIBLE
        indicatorView?.visibility = View.VISIBLE
        indicatorView?.show()
        llInternetError?.visibility = View.GONE
        llEmpty?.visibility = View.GONE
    }

    /**
     * 隐藏loadingTip
     */
    fun dismiss() {
        indicatorView?.hide()
        visibility = View.GONE
    }
}