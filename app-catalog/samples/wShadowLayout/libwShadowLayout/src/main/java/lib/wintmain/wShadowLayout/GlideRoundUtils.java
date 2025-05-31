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

import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

class GlideRoundUtils {
    public static void setRoundCorner(final View view, final Drawable resourceId,
            final float cornerDipValue) {
        if (cornerDipValue == 0) {
            if (view.getMeasuredWidth() == 0 && view.getMeasuredHeight() == 0) {
                view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                            int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        view.removeOnLayoutChangeListener(this);
                        Glide.with(view)
                                .asDrawable()
                                .load(resourceId)
                                .transform(new CenterCrop())
                                .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource,
                                            @Nullable Transition<? super Drawable> transition) {
                                        view.setBackground(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }
                                });
                    }
                });
            } else {
                Glide.with(view)
                        .asDrawable()
                        .load(resourceId)
                        .transform(new CenterCrop())
                        .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource,
                                    @Nullable Transition<? super Drawable> transition) {
                                view.setBackground(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });
            }

        } else {

            if (view.getMeasuredWidth() == 0 && view.getMeasuredHeight() == 0) {
                view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                            int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        view.removeOnLayoutChangeListener(this);
                        Glide.with(view)
                                .load(resourceId)
                                .transform(new CenterCrop(),
                                        new RoundedCorners((int) cornerDipValue))
                                .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource,
                                            @Nullable Transition<? super Drawable> transition) {
                                        view.setBackground(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                    }
                                });
                    }
                });
            } else {
                Glide.with(view)
                        .load(resourceId)
                        .transform(new CenterCrop(), new RoundedCorners((int) cornerDipValue))
                        .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource,
                                    @Nullable Transition<? super Drawable> transition) {
                                view.setBackground(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
            }
        }
    }


    public static void setCorners(final View view, final Drawable resourceId,
            final float leftTop_corner, final float leftBottom_corner,
            final float rightTop_corner, final float rightBottom_corner) {
        if (leftTop_corner == 0 && leftBottom_corner == 0 && rightTop_corner == 0
                && rightBottom_corner == 0) {
            if (view.getMeasuredWidth() == 0 && view.getMeasuredHeight() == 0) {
                view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                            int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        view.removeOnLayoutChangeListener(this);
                        Glide.with(view)
                                .load(resourceId)
                                .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource,
                                            @Nullable Transition<? super Drawable> transition) {
                                        view.setBackground(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }
                                });
                    }
                });
            } else {
                Glide.with(view)
                        .load(resourceId)
                        .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource,
                                    @Nullable Transition<? super Drawable> transition) {
                                view.setBackground(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
            }

        } else {
            if (view.getMeasuredWidth() == 0 && view.getMeasuredHeight() == 0) {
                view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                            int oldLeft, int oldTop, int oldRight, int oldBottom) {
                        view.removeOnLayoutChangeListener(this);
                        GlideRoundTransform transform = new GlideRoundTransform(view.getContext(),
                                leftTop_corner, leftBottom_corner, rightTop_corner,
                                rightBottom_corner);
                        Glide.with(view)
                                .load(resourceId)
                                .transform(transform)
                                .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource,
                                            @Nullable Transition<? super Drawable> transition) {
                                        view.setBackground(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                    }
                                });
                    }
                });
            } else {

                GlideRoundTransform transform = new GlideRoundTransform(view.getContext(),
                        leftTop_corner, leftBottom_corner, rightTop_corner, rightBottom_corner);
                Glide.with(view)
                        .load(resourceId)
                        .transform(transform)
                        .override(view.getMeasuredWidth(), view.getMeasuredHeight())
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource,
                                    @Nullable Transition<? super Drawable> transition) {
                                view.setBackground(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
            }
        }
    }
}
