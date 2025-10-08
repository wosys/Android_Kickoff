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

package com.wintmain.wBasis.transition;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.google.android.catalog.framework.annotations.Sample;
import com.wintmain.wBasis.R;

/**
 * Grid to pager app's main activity.
 */
@Sample(name = "Grid to pager",
        description = "viewpager & glide 照片展示",
        tags = {"android-samples", "animation-samples"}
)
public class GridToPagerMainActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_POSITION =
            "com.wintmain.wBasis.gridtopager.key.currentPosition";
    /**
     * Holds the current image position to be shared between the grid and the pager fragments. This
     * position updated when a grid item is clicked, or when paging the pager.
     *
     * In this demo app, the position always points to an image index at the {@link
     * com.wintmain.wBasis.transition.util.ImageData} class.
     */
    public static int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_to_pager_main);
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0);
            // Return here to prevent adding additional GridFragments when changing orientation.
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, new GridToPagerFragment(),
                        GridToPagerFragment.class.getSimpleName())
                .commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_POSITION, currentPosition);
    }

}
