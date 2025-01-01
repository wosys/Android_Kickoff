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

package com.wintmain.foundation.ui.views.layout

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.foundation.R
import com.wintmain.foundation.databinding.ConstraintSetMainBinding

@Sample(
    name = "1. Centering Views",
    description = "水平或垂直居中子视图。",
    documentation = "",
    tags = ["android-samples", "layout"],
)
class CenteringViewsFragment : Fragment(R.layout.constraintlayout_centering_views)

@Sample(
    name = "2. Basic arrangement",
    description = "排列子视图相对于其他视图的位置。",
    documentation = "",
    tags = ["android-samples", "layout"],
)
class BasicArrangementFragment : Fragment(R.layout.constraintlayout_basic_arrangement)

@Sample(
    name = "3. Advanced arrangement",
    description = "更多排列选项。",
    documentation = "",
    tags = ["android-samples", "layout"],
)
class AdvancedArrangementFragment : Fragment(R.layout.advanced_arrangement)

@Sample(
    name = "4. Aspect ratio",
    description = "指定子视图尺寸的纵横比（ratio）",
    documentation = "",
    tags = ["android-samples", "layout"],
)
class AspectRatioFragment : Fragment(R.layout.aspect_ratio)

@Sample(
    name = "5. Basic chains",
    description = "使用 chains 可以水平或垂直排列多个子视图。",
    documentation = "",
    tags = ["android-samples", "layout"],
)
class BasicChainFragment : Fragment(R.layout.basic_chains)

@Sample(
    name = "6. Advanced chains",
    description = "使用 chains 可以水平或垂直排列多个子视图。",
    documentation = "",
    tags = ["android-samples", "layout"],
)
class AdvancedChainsFragment : Fragment(R.layout.advanced_chains)

@Sample(
    name = "7. ConstraintSet",
    description = "使用“约束集”可以为所有子视图指定多个约束。",
    documentation = "",
    tags = ["android-samples", "layout"],
)
class ConstraintSetFragment : Fragment(R.layout.constraint_set_main) {

    private lateinit var binding: ConstraintSetMainBinding

    private var showBigImage = false

    // 视图切换准备的“约束集”
    private val constraintSetNormal = ConstraintSet()
    private val constraintSetBig = ConstraintSet()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ConstraintSetMainBinding.bind(view)
        // Note that this can also be achieved by calling
        // `constraintSetNormal.load(requireContext(), R.layout.constraint_set_main);`
        // Since we already have an inflated ConstraintLayout in the layout file, clone() is
        // faster and considered the best practice.
        constraintSetNormal.clone(binding.root)
        // Load the constraints from the layout where ImageView is enlarged.
        constraintSetBig.load(requireContext(), R.layout.constraint_set_big)
        binding.image.setOnClickListener {
            clickToChange()
        }
        binding.body.setOnClickListener {
            clickToChange()
        }
    }

    private fun clickToChange() {
        showBigImage = !showBigImage
        if (showBigImage) {
            constraintSetBig.applyTo(binding.root)
        } else {
            constraintSetNormal.applyTo(binding.root)
        }
    }
}

@Sample(
    name = "8. Guidelines",
    description = "使用水平或垂直基准线可以对子视图进行约束",
    documentation = "",
    tags = ["android-samples", "layout"],
)
class GuidelinesFragment : Fragment(R.layout.guidelines)

@Sample(
    name = "CardView",
    description = "创建卡片式布局",
    documentation = "",
    tags = ["android-samples", "widget"],
)
class CardViewFragment : Fragment(R.layout.card_view)

@Sample(
    name = "LinearLayout",
    description = "线性布局",
    documentation = "",
    tags = ["android-samples", "layout"],
)
class LinearViewsFragment : Fragment(R.layout.linearlayout_view)
