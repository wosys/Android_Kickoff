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

package lib.wintmain.xplayer.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * des 自定义DataBinding适配器
 */
object CustomBindAdapter {
    /**
     * 加载资源图片
     */
    @BindingAdapter(value = ["imgSrc"])
    @JvmStatic
    fun imgSrc(view: ImageView, id: Int) {
        view.setImageResource(id)
    }

    /**
     * 加载图片
     */
    @BindingAdapter(value = ["imageUrl"])
    @JvmStatic
    fun imageUrl(view: ImageView, url: String) {
        view.loadUrl(view.context.applicationContext, url)
    }

    /**
     * 加载本地圆形图片
     */
    @BindingAdapter(value = ["imgUriCircle"])
    @JvmStatic
    fun imgUriCircle(view: ImageView, albumId: Long) {
        if (albumId == -1L) {
            view.setImageResource(0)
            return
        }
        view.loadCircle(view.context.applicationContext, albumById(albumId))
    }

    /**
     * 加载网络圆角图片
     */
    @BindingAdapter(value = ["imgUrlRadius"])
    @JvmStatic
    fun imgUrlRadiusCircle(view: ImageView, url: String) {
        view.loadRadius(view.context.applicationContext, url)
    }

    @BindingAdapter(value = ["visible"])
    @JvmStatic
    fun visible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }
}