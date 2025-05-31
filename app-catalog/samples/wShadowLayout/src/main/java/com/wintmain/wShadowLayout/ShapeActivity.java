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

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.wintmain.wShadowLayout.databinding.ActivityShapeBinding;

public class ShapeActivity extends AppCompatActivity {
    ActivityShapeBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shape);

        binding.ShadowLayoutImage.setOnClickListener(v -> {
            binding.ShadowLayoutImage.setSelected(!binding.ShadowLayoutImage.isSelected());
        });
        binding.shadowLayoutBarLeft.setOnClickListener(v -> {
            finish();
        });
        binding.shadowLayoutSelect.setOnClickListener(v -> {
            binding.shadowLayoutSelect.setSelected(!binding.shadowLayoutSelect.isSelected());
        });
        binding.shadowLayoutBindView.setOnClickListener(v -> {
            binding.shadowLayoutBindView.setSelected(!binding.shadowLayoutBindView.isSelected());
        });
    }
}
