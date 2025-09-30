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

package com.wintmain.wBasis.webview

import android.os.Bundle
import android.webkit.WebSettings.LayoutAlgorithm.SINGLE_COLUMN
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.wBasis.R

@Sample(name = "CustomActionWebViewActivity", description = "", tags = ["webkit"])
class CustomActionWebViewActivity : AppCompatActivity() {
    private lateinit var mWebView: CustomActionWebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_action_webview)
        mWebView = findViewById(R.id.webview)

        val list: MutableList<String> = ArrayList()
        list.add("复制")
        list.add("分享")
        list.add("批注")
        //设置item
        mWebView.setActionList(list)

        //链接js注入接口，使能选中返回数据
        mWebView.linkJSInterface()

        //增加点击回调
        mWebView.setActionSelectListener(object : ActionSelectListener {
            override fun onClick(title: String?, parentText: String?, selectText: String?) {
                val info = "内容: $selectText。\n段落: $parent"
                Toast.makeText(applicationContext, info, Toast.LENGTH_LONG).show()
            }
        })

        // 开启 javascript 渲染
        val webSettings = mWebView.getSettings()

        webSettings.javaScriptCanOpenWindowsAutomatically = true

        //关键点
        webSettings.useWideViewPort = true

        webSettings.layoutAlgorithm = SINGLE_COLUMN

        webSettings.displayZoomControls = false

        // 设置支持javascript脚本
        webSettings.javaScriptEnabled = true

        // 允许访问文件
        webSettings.allowFileAccess = true

        // 设置显示缩放按钮
        webSettings.builtInZoomControls = true

        // 支持缩放
        webSettings.setSupportZoom(true)

        webSettings.loadWithOverviewMode = true

        mWebView.setWebViewClient(WebViewClient())

        // 载入内容
        // mWebView.loadUrl("file:///android_asset/111.html")
        mWebView.loadData("qweweeeeeeeeeeqewq", "text/html", "utf-8")
    }
}