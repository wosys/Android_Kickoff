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

package com.wintmain.basic.constraintlayout

import androidx.fragment.app.Fragment
import com.google.android.catalog.framework.annotations.Sample
import com.wintmain.basic.R

@Sample(
    name = "1. Centering Views",
    description = "水平或垂直居中子视图。",
    documentation = "",
    tags = ["user-interface", "constraint-layout"],
)
class CenteringViewsFragment : Fragment(R.layout.constraintlayout_centering_views)

@Sample(
    name = "2. Basic arrangement",
    description = "排列子视图相对于其他视图的位置。",
    documentation = "",
    tags = ["user-interface", "constraint-layout"],
)
class BasicArrangementFragment : Fragment(R.layout.constraintlayout_basic_arrangement)

@Sample(
    name = "3. Advanced arrangement",
    description = "更多排列选项。",
    documentation = "",
    tags = ["user-interface", "constraint-layout"],
)
class AdvancedArrangementFragment : Fragment(R.layout.advanced_arrangement)

@Sample(
    name = "4. Aspect ratio",
    description = "指定子视图尺寸的纵横比（ratio）。",
    documentation = "",
    tags = ["user-interface", "constraint-layout"],
)
class AspectRatioFragment : Fragment(R.layout.aspect_ratio)
