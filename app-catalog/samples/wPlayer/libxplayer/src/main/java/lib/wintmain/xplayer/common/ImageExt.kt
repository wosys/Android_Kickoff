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

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import lib.wintmain.xplayer.R
import lib.wintmain.xplayer.view.GlideRoundTransform

/**
 * des 图片加载扩展方法
 */
/**
 * 通过url加载
 */
fun ImageView.loadUrl(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .transition(withCrossFade())
        .into(this)
}

/**
 * 通过uri加载
 */
fun ImageView.loadUri(context: Context, uri: Uri) {
    Glide.with(context)
        .load(uri)
        .transition(withCrossFade())
        .into(this)
}

/**
 * 高斯模糊加渐入渐出
 */
fun ImageView.loadBlurTrans(context: Context, uri: Uri, radius: Int) {
    Glide.with(context)
        .load(uri)
        .thumbnail(0.1f).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .apply(RequestOptions.bitmapTransform(CenterBlurTransformation(radius, 8, context)))
        .transition(withCrossFade(400))
        .into(this)
}

/**
 * 圆形图片
 */
fun ImageView.loadCircle(context: Context, uri: Uri) {
    Glide.with(context)
        .load(uri)
        .apply(RequestOptions.bitmapTransform(CircleCrop()))
        .into(this)
}

/**
 * 圆形图片
 */
fun ImageView.loadRadius(context: Context, url: String, radius: Int = 20) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .error(R.drawable.ic_launcher)
        .transition(withCrossFade())
        .transform(GlideRoundTransform(context, radius))
        .into(this)
}


