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

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;
import lib.wintmain.wShadowLayout.ShadowLayout;

public class StarShowActivity extends AppCompatActivity implements View.OnClickListener {
    private ShadowLayout ShadowLayout;
    private int alpha;
    private int red;
    private int green;
    private int blue;
    private ImageView tab_topShow;
    private ImageView tab_bottomShow;
    private ImageView tab_rightShow;
    private ImageView tab_leftShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starshow);
        ShadowLayout = findViewById(R.id.ShadowLayout);
        SeekBar skbar_x = findViewById(R.id.skbar_x);
        SeekBar skbar_y = findViewById(R.id.skbar_y);
        SeekBar skbar_limit = findViewById(R.id.skbar_limit);
        SeekBar skbar_corner = findViewById(R.id.skbar_corner);
        SeekBar skbar_alpha = findViewById(R.id.skbar_alpha);
        SeekBar skbar_red = findViewById(R.id.skbar_red);
        SeekBar skbar_green = findViewById(R.id.skbar_green);
        SeekBar skbar_blue = findViewById(R.id.skbar_blue);
        tab_topShow = findViewById(R.id.tab_topShow);
        tab_topShow.setOnClickListener(this);
        tab_bottomShow = findViewById(R.id.tab_bottomShow);
        tab_bottomShow.setOnClickListener(this);
        tab_rightShow = findViewById(R.id.tab_rightShow);
        tab_rightShow.setOnClickListener(this);
        tab_leftShow = findViewById(R.id.tab_leftShow);
        tab_leftShow.setOnClickListener(this);

        skbar_corner.setMax((int) (ShadowLayout.getCornerRadius() * 3));
        skbar_corner.setProgress((int) ShadowLayout.getCornerRadius());
        skbar_corner.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ShadowLayout.setCornerRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        skbar_limit.setMax((int) (ShadowLayout.getShadowLimit() * 3));
        skbar_limit.setProgress((int) ShadowLayout.getShadowLimit());
        skbar_limit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {
                ShadowLayout.setShadowLimit(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        skbar_x.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ShadowLayout.setShadowOffsetX(progress - 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        skbar_y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ShadowLayout.setShadowOffsetY(progress - 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        skbar_alpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alpha = progress;
                ShadowLayout.setShadowColor(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        skbar_red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                red = progress;
                ShadowLayout.setShadowColor(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        skbar_green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                green = progress;
                ShadowLayout.setShadowColor(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        skbar_blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blue = progress;
                ShadowLayout.setShadowColor(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tab_topShow) {
            ShadowLayout.setShadowHiddenTop(select(tab_topShow));
        } else if (id == R.id.tab_bottomShow) {
            ShadowLayout.setShadowHiddenBottom(select(tab_bottomShow));
        } else if (id == R.id.tab_leftShow) {
            ShadowLayout.setShadowHiddenLeft(select(tab_leftShow));
        } else if (id == R.id.tab_rightShow) {
            ShadowLayout.setShadowHiddenRight(select(tab_rightShow));
        }
    }

    public boolean select(ImageView imageView) {
        if (imageView.isSelected()) {
            imageView.setSelected(false);
            return false;
        } else {
            imageView.setSelected(true);
            return true;
        }
    }
}
