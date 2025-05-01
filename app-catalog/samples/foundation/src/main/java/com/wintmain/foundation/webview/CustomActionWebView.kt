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

package com.wintmain.foundation.webview

import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.AttributeSet
import android.view.ActionMode
import android.view.ActionMode.Callback
import android.webkit.JavascriptInterface
import android.webkit.WebView
import androidx.core.view.get
import androidx.core.view.size

class CustomActionWebView(
    context: Context,
    attrs: AttributeSet?,
) : WebView(context, attrs) {
    companion object {
        private const val TAG = "CustomActionWebView"
    }

    private var mActionMode: ActionMode? = null
    private var mActionList: List<String> = ArrayList()
    private var mActionSelectListener: ActionSelectListener? = null

    /**
     * 处理item，处理点击
     * @param actionMode
     */
    private fun resolveActionMode(actionMode: ActionMode?): ActionMode {
        if (actionMode != null) {
            val menu = actionMode.menu
            mActionMode = actionMode
            menu.clear()
            for (i in mActionList.indices) {
                menu.add(mActionList[i])
            }
            for (i in 0..<menu.size) {
                val menuItem = menu[i]
                menuItem.setOnMenuItemClickListener { item ->
                    (item.title as String?)?.let { getSelectedData(it) }
                    releaseAction()
                    true
                }
            }
        }
        mActionMode = actionMode!!
        return actionMode
    }

    override fun startActionMode(callback: Callback?): ActionMode {
        val actionMode = super.startActionMode(callback)
        return resolveActionMode(actionMode)
    }

    override fun startActionMode(callback: Callback?, type: Int): ActionMode {
        val actionMode = super.startActionMode(callback, type)
        return resolveActionMode(actionMode)
    }

    private fun releaseAction() {
        if (mActionMode != null) {
            mActionMode!!.finish()
            mActionMode = null
        }
    }

    /**
     * 点击的时候，获取网页中选择的文本，回掉到原生中的js接口
     * @param title 传入点击的item文本，一起通过js返回给原生接口
     * IE9以下支持：document.selection 　　
     * IE9、Firefox、Safari、Chrome和Opera支持：window.getSelection()
     */
    private fun getSelectedData(title: String) {
        val js = "(function getSelectedText() {" +
            "var txt1 =window.getSelection().getRangeAt(0).startContainer.data;" +
            "var txt2 =window.getSelection().toString();" +
            "var title = \"" + title + "\";" +
            "JSInterface.callback(txt1,txt2,title);" +
            "})()"
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            evaluateJavascript("javascript:$js", null)
        } else {
            loadUrl("javascript:$js")
        }
    }

    fun linkJSInterface() {
        addJavascriptInterface(ActionSelectInterface(), "JSInterface")
    }

    /**
     * 设置弹出action列表
     * @param actionList
     */
    fun setActionList(actionList: MutableList<String>) {
        mActionList = actionList
    }

    /**
     * 设置点击回掉
     * @param actionSelectListener
     */
    fun setActionSelectListener(actionSelectListener: ActionSelectListener?) {
        this.mActionSelectListener = actionSelectListener
    }

    /**
     * 隐藏消失Action
     */
    fun dismissAction() {
        releaseAction()
    }

    /**
     * js选中的回掉接口
     */
    private inner class ActionSelectInterface {
        @JavascriptInterface
        fun callback(parentText: String?, value: String?, title: String?) {
            this@CustomActionWebView.mActionSelectListener?.onClick(title, value, parentText)
        }
    }
}

interface ActionSelectListener {
    fun onClick(title: String?, parentText: String?, selectText: String?)
}
