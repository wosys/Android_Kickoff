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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import com.google.android.catalog.framework.annotations.Sample;
import com.squareup.picasso.Picasso;
import com.wintmain.wBasis.R;
import com.wintmain.wBasis.transition.util.SceneTransitionBasicItem;

/**
 * Displays a grid of items which an image and title.
 * When the user clicks on an item, {@link SceneTransitionBasicDetailActivity} is launched,
 * using the Activity Scene Transitions framework to animatedly do so.
 */
@Sample(name = "ActivitySceneTransitionBaic",
        description = "Activity Scene的变化",
        tags = {"android-samples", "animation-samples"}
)
public class SceneTransitionBasicMainActivity extends AppCompatActivity {

    private final AdapterView.OnItemClickListener mOnItemClickListener
            = new AdapterView.OnItemClickListener() {

        /**
         * Called when an item in the {@link android.widget.GridView} is clicked. Here will launch
         * the {@link SceneTransitionBasicDetailActivity}, using the Scene Transition animation
         * functionality.
         */
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            SceneTransitionBasicItem sceneTransitionBasicItem =
                    (SceneTransitionBasicItem) adapterView.getItemAtPosition(position);

            // Construct an Intent as normal
            Intent intent = new Intent(SceneTransitionBasicMainActivity.this,
                    SceneTransitionBasicDetailActivity.class);
            intent.putExtra(SceneTransitionBasicDetailActivity.EXTRA_PARAM_ID,
                    sceneTransitionBasicItem.getId());

            // BEGIN_INCLUDE(start_activity)
            /*
             * Now create an {@link android.app.ActivityOptions} instance using the
             * {@link ActivityOptionsCompat#makeSceneTransitionAnimation(Activity, Pair[])} factory
             * method.
             */
            @SuppressWarnings("unchecked")
            ActivityOptionsCompat activityOptions =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                            SceneTransitionBasicMainActivity.this,

                            // Now we provide a list of Pair items which contain the view we can
                            // transitioning from, and the name of the view it is transitioning to,
                            // in the launched activity
                            new Pair<>(view.findViewById(R.id.imageview_item),
                                    SceneTransitionBasicDetailActivity.VIEW_NAME_HEADER_IMAGE),
                            new Pair<>(view.findViewById(R.id.textview_name),
                                    SceneTransitionBasicDetailActivity.VIEW_NAME_HEADER_TITLE));

            // Now we can start the Activity, providing the activity options as a bundle
            ActivityCompat.startActivity(SceneTransitionBasicMainActivity.this, intent,
                    activityOptions.toBundle());
            // END_INCLUDE(start_activity)
        }
    };

    /**
     * {@link android.widget.BaseAdapter} which displays items.
     */
    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return SceneTransitionBasicItem.SceneTransitionBasicITEMS.length;
        }

        @Override
        public SceneTransitionBasicItem getItem(int position) {
            return SceneTransitionBasicItem.SceneTransitionBasicITEMS[position];
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.activity_scene_transition_basic_grid_item, viewGroup, false);
            }

            final SceneTransitionBasicItem item = getItem(position);

            // Load the thumbnail image
            ImageView image = (ImageView) view.findViewById(R.id.imageview_item);
            Picasso.with(image.getContext()).load(item.getThumbnailUrl()).into(image);

            // Set the TextView's contents
            TextView name = (TextView) view.findViewById(R.id.textview_name);
            name.setText(item.getName());

            return view;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_transition_basic_grid);

        GridView gridView = findViewById(R.id.grid);
        gridView.setOnItemClickListener(mOnItemClickListener);

        // 没有adapter，页面是空白的
        GridAdapter mAdapter = new GridAdapter();
        gridView.setAdapter(mAdapter);
    }
}
