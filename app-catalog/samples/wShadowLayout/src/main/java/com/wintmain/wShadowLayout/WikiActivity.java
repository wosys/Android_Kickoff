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

package com.wintmain.wShadowLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.*;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.wintmain.wShadowLayout.databinding.ActivityWikiBinding;


public class WikiActivity extends AppCompatActivity {
    ActivityWikiBinding binding;
    String urlStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wiki);

        // 访问的网站
        urlStr = "https://baidu.com";
        // 声明WebSettings子类
        WebSettings webSettings = binding.webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        // 设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setDomStorageEnabled(true);

        // 缩放操作
        webSettings.setSupportZoom(true); // 支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); // 设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); // 隐藏原生的缩放控件

        // 其他细节操作
        webSettings.setCacheMode(
                WebSettings.LOAD_CACHE_ELSE_NETWORK); // 开启缓存LOAD_CACHE_ELSE_NETWORK
        // LOAD_NO_CACHE关闭缓存
        webSettings.setAllowFileAccess(true); // 设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); // 支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8"); // 设置编码格式

        //解决webView不加载图片
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        // 复写shouldOverrideUrlLoading()，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                        return false;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) {
                    return true;
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                    String description, String failingUrl) {
                // Condition 'binding.progressBar != null' is always 'true'
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(WikiActivity.this, "网页加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            //webView加载结束后的动作
            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (binding.progressBar.getVisibility() == View.GONE && newProgress != 100) {
                    //点击webView内部，进度条可见
                    binding.progressBar.setVisibility(View.VISIBLE);
                }
                if (newProgress == 100 && binding.progressBar.getVisibility() == View.VISIBLE) {
                    //bug:不走finish所以不能设置不可见
                    binding.progressBar.setVisibility(View.GONE);
                }
                binding.progressBar.setProgress(newProgress);
            }
        });

        binding.webView.loadUrl(urlStr);
        //js互调相关
        binding.webView.addJavascriptInterface(new CrosswalkInterface(this), "JSbridge");

        binding.shadowLayoutBarLeft.setOnClickListener(v -> {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack();
            } else {
                finish();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        super.onResume();
        binding.webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.webView.destroy();
    }

    public class CrosswalkInterface {
        Context context;

        public CrosswalkInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void exit() {
            finish();
        }
    }
}
