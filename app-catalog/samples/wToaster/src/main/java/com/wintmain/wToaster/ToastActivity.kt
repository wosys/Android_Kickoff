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

package com.wintmain.wToaster

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.catalog.framework.annotations.Sample
import lib.wintmain.wToaster.style.ToastBlackStyle
import lib.wintmain.wToaster.style.ToastWhiteStyle
import lib.wintmain.wToaster.toast.ToastUtils

/**
 * @Description
 * @Author wintmain
 * @mailto wosintmain@gmail.com
 * @Date 2022-11-09 23:46:07
 */
@Sample(
    name = "Toast demo",
    description = "吐司例子",
    documentation = "",
//    owners = ["wintmain"],
    tags = ["A-Self_demos"]
)
class ToastActivity : AppCompatActivity(R.layout.activity_toast) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ToastUtils.init(this.application)

        val button_7 = findViewById<View>(R.id.show7) as Button
        button_7.setOnClickListener {
            ToastUtils.show(
                ToastUtils.isNotificationEnabled(
                    applicationContext
                )
            )
        }
    }

    // https://www.sunzhongwei.com/android-kotlin-compile-time-prompt-warning-parameter-view-is-never-informs
    fun show1(@Suppress("UNUSED_PARAMETER") view: View) {
        ToastUtils.show("我是Toast")
    }

    fun show2(@Suppress("UNUSED_PARAMETER") view: View) {
        Thread { ToastUtils.show("我是从子线程中弹出的Toast") }.start()
    }

    fun show3(@Suppress("UNUSED_PARAMETER") view: View) {
        ToastUtils.initStyle(ToastWhiteStyle())
        ToastUtils.show("动态切换Toast白色样式成功")
    }

    fun show4(@Suppress("UNUSED_PARAMETER") view: View) {
        ToastUtils.initStyle(ToastBlackStyle())
        ToastUtils.show("动态切换Toast黑色样式成功")
    }

    fun show5(@Suppress("UNUSED_PARAMETER") view: View) {
        ToastUtils.show(this)
    }

    fun show6(@Suppress("UNUSED_PARAMETER") view: View) {
        ToastUtils.setView(this, R.layout.toast_custom_view)
        ToastUtils.show("我是自定义Toast")
    }
    //    @Override
    //    protected void onRestart() {
    //        super.onRestart();
    //        //如果通知栏的权限被手动关闭了
    //        if(!ToastUtils.isNotificationEnabled(this) && "XToast".equals(ToastUtils.getToast().getClass().getSimpleName())){
    //            Log.v("TAG","onRestart");
    //            ToastUtils.init(getApplication());
    //            recreate();
    //            ToastUtils.show("检查到你手动关闭了。需要重启应用");
    //        }
    //    }
}