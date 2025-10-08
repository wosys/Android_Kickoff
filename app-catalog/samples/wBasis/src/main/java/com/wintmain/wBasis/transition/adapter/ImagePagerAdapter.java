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

package com.wintmain.wBasis.transition.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.wintmain.wBasis.transition.fragment.ImageFragment;

import static com.wintmain.wBasis.transition.util.ImageData.IMAGE_DRAWABLES;

public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    //    public ImagePagerAdapter(@NonNull FragmentManager fm) {
//        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//    }
    public ImagePagerAdapter(Fragment fragment) {
        // Note: Initialize with the child fragment manager.
        super(fragment.getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(IMAGE_DRAWABLES[position]);
    }

    @Override
    public int getCount() {
        return IMAGE_DRAWABLES.length;
    }
}
