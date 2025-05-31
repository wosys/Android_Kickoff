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

package lib.wintmain.xplayer.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.RSRuntimeException;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.internal.FastBlur;
import jp.wasabeef.glide.transformations.internal.RSBlur;

import java.security.MessageDigest;

public class CenterBlurTransformation extends BitmapTransformation {

    private static final int VERSION = 1;
    private static final String ID =
            "jp.wasabeef.glide.transformations.BlurTransformation." + VERSION;

    private static int MAX_RADIUS = 25;
    private static int DEFAULT_DOWN_SAMPLING = 1;

    private int radius;
    private int sampling;
    private Context context;

    public CenterBlurTransformation(Context context) {
        this(MAX_RADIUS, DEFAULT_DOWN_SAMPLING, context);
    }

    public CenterBlurTransformation(int radius, Context context) {
        this(radius, DEFAULT_DOWN_SAMPLING, context);
    }

    public CenterBlurTransformation(int radius, int sampling, Context context) {
        this.radius = radius;
        this.sampling = sampling;
        this.context = context;
    }

    @Override
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
            @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
        return blurCrop(pool, bitmap);
    }

    private Bitmap blurCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }
        int width = source.getWidth();
        int height = source.getHeight();
        int scaledWidth = width / sampling;
        int scaledHeight = height / sampling;
        Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 / (float) sampling, 1 / (float) sampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(source, 0, 0, paint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                bitmap = RSBlur.blur(context, bitmap, radius);
            } catch (RSRuntimeException e) {
                bitmap = FastBlur.blur(bitmap, radius, true);
            }
        } else {
            bitmap = FastBlur.blur(bitmap, radius, true);
        }
        return bitmap;
    }


    @Override
    public boolean equals(Object o) {
        return o instanceof CenterBlurTransformation &&
                ((CenterBlurTransformation) o).radius == radius &&
                ((CenterBlurTransformation) o).sampling == sampling;
    }

    @Override
    public int hashCode() {
        return ID.hashCode() + radius * 1000 + sampling * 10;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }
}
