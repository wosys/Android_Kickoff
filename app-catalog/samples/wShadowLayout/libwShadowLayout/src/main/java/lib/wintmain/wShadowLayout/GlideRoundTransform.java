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

package lib.wintmain.wShadowLayout;

import android.content.Context;
import android.graphics.*;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import java.security.MessageDigest;

/**
 * 这个是glide只加载2个圆角
 */
class GlideRoundTransform implements Transformation<Bitmap> {
    private final BitmapPool mBitmapPool;

    private float leftTop_radius;
    private float leftBottom_radius;
    private float rightTop_radius;
    private float rightBottom_radius;


    /**
     * @param context 上下文
     */
    public GlideRoundTransform(Context context, float leftTop_radius, float leftBottom_radius,
            float rightTop_radius, float rightBottom_radius) {
        this.mBitmapPool = Glide.get(context).getBitmapPool();
        this.leftTop_radius = leftTop_radius;
        if (leftTop_radius != 0) {
            boolean isLeftTop = true;
        }
        this.leftBottom_radius = leftBottom_radius;
        if (leftBottom_radius != 0) {
            boolean isLeftBottom = true;
        }
        this.rightTop_radius = rightTop_radius;
        if (rightTop_radius != 0) {
            boolean isRightTop = true;
        }
        this.rightBottom_radius = rightBottom_radius;
        if (rightBottom_radius != 0) {
            boolean isRightBottom = true;
        }
    }

    @NonNull
    @Override
    public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource,
            int outWidth, int outHeight) {
        Bitmap source = resource.get();
        int finalWidth, finalHeight;
        // 输出目标的宽高或高宽比例
        float scale;
        if (outWidth > outHeight) {
            // 如果 输出宽度 > 输出高度 求高宽比
            scale = (float) outHeight / (float) outWidth;
            finalWidth = source.getWidth();
            // 固定原图宽度,求最终高度
            finalHeight = (int) ((float) source.getWidth() * scale);
            if (finalHeight > source.getHeight()) {
                // 如果 求出的最终高度 > 原图高度 求宽高比
                scale = (float) outWidth / (float) outHeight;
                finalHeight = source.getHeight();
                // 固定原图高度,求最终宽度
                finalWidth = (int) ((float) source.getHeight() * scale);
            }
        } else if (outWidth < outHeight) {
            // 如果 输出宽度 < 输出高度 求宽高比
            scale = (float) outWidth / (float) outHeight;
            finalHeight = source.getHeight();
            // 固定原图高度,求最终宽度
            finalWidth = (int) ((float) source.getHeight() * scale);
            if (finalWidth > source.getWidth()) {
                // 如果 求出的最终宽度 > 原图宽度 求高宽比
                scale = (float) outHeight / (float) outWidth;
                finalWidth = source.getWidth();
                finalHeight = (int) ((float) source.getWidth() * scale);
            }
        } else {
            // 如果 输出宽度=输出高度
            finalHeight = source.getHeight();
            finalWidth = finalHeight;
        }

        // 修正圆角
        this.leftTop_radius *= (float) finalHeight / (float) outHeight;
        this.leftBottom_radius *= (float) finalHeight / (float) outHeight;
        this.rightTop_radius *= (float) finalHeight / (float) outHeight;
        this.rightBottom_radius *= (float) finalHeight / (float) outHeight;
        Bitmap outBitmap = this.mBitmapPool.get(finalWidth, finalHeight, Bitmap.Config.ARGB_8888);
        if (outBitmap == null) {
            outBitmap = Bitmap.createBitmap(finalWidth, finalHeight, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        // 关联画笔绘制的原图bitmap
        BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        // 计算中心位置,进行偏移
        int width = (source.getWidth() - finalWidth) / 2;
        int height = (source.getHeight() - finalHeight) / 2;
        if (width != 0 || height != 0) {
            Matrix matrix = new Matrix();
            matrix.setTranslate((float) (-width), (float) (-height));
            shader.setLocalMatrix(matrix);
        }

        paint.setShader(shader);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0.0F, 0.0F, (float) canvas.getWidth(),
                (float) canvas.getHeight());

        float[] outerR = new float[]{leftTop_radius, leftTop_radius, rightTop_radius,
                rightTop_radius, rightBottom_radius,
                rightBottom_radius, leftBottom_radius, leftBottom_radius}; // 左上，右上，右下，左下
        Path path = new Path();
        path.addRoundRect(rectF, outerR, Path.Direction.CW);
        canvas.drawPath(path, paint);
        return BitmapResource.obtain(outBitmap, this.mBitmapPool);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }
}

