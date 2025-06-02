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

package com.wintmain.wNet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.drake.brv.utils.BRV
import com.drake.statelayout.StateConfig
import com.drake.tooltip.dialog.BubbleDialog
import com.google.android.catalog.framework.annotations.Sample
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wintmain.wNet.converter.SerializationConverter
import com.wintmain.wNet.interceptor.GlobalHeaderInterceptor
import com.wintmain.wNet.mock.MockDispatcher
import lib.wintmain.libwNet.NetConfig
import lib.wintmain.libwNet.cookie.PersistentCookieJar
import lib.wintmain.libwNet.interceptor.LogRecordInterceptor
import lib.wintmain.libwNet.okhttp.setConverter
import lib.wintmain.libwNet.okhttp.setDebug
import lib.wintmain.libwNet.okhttp.setDialogFactory
import lib.wintmain.libwNet.okhttp.setRequestInterceptor
import okhttp3.Cache
import java.util.concurrent.TimeUnit

@Sample(
    name = "wNet",
    description = "网络库例子",
    documentation = "",
    tags = ["A-Self_demos", "wNet"]
)
class WNetMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NetConfig.initialize(com.wintmain.wNet.constants.Api.HOST, this) {

            // 超时设置
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)

            // 本框架支持Http缓存协议和强制缓存模式
            cache(Cache(cacheDir, 1024 * 1024 * 128)) // 缓存设置, 当超过maxSize最大值会根据最近最少使用算法清除缓存来限制缓存大小

            // LogCat是否输出异常日志, 异常日志可以快速定位网络请求错误
            setDebug(true)

            // AndroidStudio OkHttp Profiler 插件输出网络日志
            addInterceptor(LogRecordInterceptor(true))

            // 添加持久化Cookie管理
            cookieJar(PersistentCookieJar(applicationContext))

            // 仅开发模式启用通知栏监听网络日志, 该框架存在下载大文件时内存溢出崩溃请等待官方修复 https://github.com/ChuckerTeam/chucker/issues/1068
            addInterceptor(
                ChuckerInterceptor.Builder(applicationContext)
                    .collector(ChuckerCollector(applicationContext))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )

            // 添加请求拦截器, 可配置全局/动态参数
            setRequestInterceptor(GlobalHeaderInterceptor())

            // 数据转换器
            setConverter(SerializationConverter())

            // 自定义全局加载对话框
            setDialogFactory {
                BubbleDialog(it, "加载中....")
            }
        }

        MockDispatcher.initialize()

        initializeView()
    }

    /** 初始化第三方依赖库库 */
    private fun initializeView() {

        // 全局缺省页配置 [https://github.com/liangjingkanji/StateLayout]
        StateConfig.apply {
            emptyLayout = R.layout.layout_empty
            loadingLayout = R.layout.layout_loading
            errorLayout = R.layout.layout_error
            setRetryIds(R.id.iv)
        }

        // 初始化SmartRefreshLayout, 这是自动下拉刷新和上拉加载采用的第三方库  [https://github.com/scwang90/SmartRefreshLayout/tree/master] V2版本
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            MaterialHeader(context)
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(context)
        }

        BRV.modelId = BR.m

        val intent = Intent().apply {
            setClassName("com.wintmain.catalog.app", "com.wintmain.wNet.ui.activity.MainActivity")
        }
        startActivity(intent)
    }
}