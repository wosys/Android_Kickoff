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

package com.wintmain.xplayer.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import lib.wintmain.wToaster.style.ToastBlackStyle;
import lib.wintmain.wToaster.toast.ToastUtils;

/**
 * @Description
 * @Author wintmain
 * @mailto wosintmain@gmail.com
 * @Date 2022-06-01 10:57:18
 */
public class InternetActivity extends AppCompatActivity {

    private WebView webView;
    private long exitTime = System.currentTimeMillis();

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        webView.setWebViewClient(
                new WebViewClient() {
                    // 设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
        webView.getSettings().setJavaScriptEnabled(true); // 设置WebView属性,运行执行js脚本
        webView.loadUrl("https://music.163.com/#/discover/toplist"); // 调用loadUrl方法为WebView加入链接
        setContentView(webView); // 调用Activity提供的setContentView将webView显示出来
    }

    // 我们需要重写回退按钮的时间,当用户点击回退按钮：
    // 1.webView.canGoBack()判断网页是否能后退,可以则goback()
    // 2.如果不可以连续点击两次退出App,否则弹出提示Toast
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2500) {
                ToastUtils.initStyle(new ToastBlackStyle());
                ToastUtils.show("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }
}
