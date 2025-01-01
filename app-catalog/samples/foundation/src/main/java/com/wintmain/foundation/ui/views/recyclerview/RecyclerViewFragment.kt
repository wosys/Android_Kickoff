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
package com.wintmain.foundation.ui.views.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.wintmain.foundation.R
import com.wintmain.foundation.ui.views.recyclerview.RecyclerViewFragment.LayoutManagerType.GRID_LAYOUT_MANAGER
import com.wintmain.foundation.ui.views.recyclerview.RecyclerViewFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER
import java.util.Objects

/**
 * Demonstrates the use of [RecyclerView] with a [LinearLayoutManager] and a
 * [GridLayoutManager].
 */
class RecyclerViewFragment : Fragment() {
    companion object {
        private const val TAG = "RecyclerViewFragment"
        private const val KEY_LAYOUT_MANAGER = "layoutManager"
        private const val SPAN_COUNT = 2
        private const val DATASET_COUNT = 100
    }

    private lateinit var mCurrentLayoutManagerType: LayoutManagerType
    private lateinit var mLinearLayoutRadioButton: RadioButton
    private lateinit var mGridLayoutRadioButton: RadioButton
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: CustomAdapter
    private lateinit var mLayoutManager: LayoutManager
    private lateinit var mDataset: Array<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        // Generates Strings for RecyclerView's adapter.
        mDataset = arrayOfNulls(DATASET_COUNT)
        for (i in 0 until DATASET_COUNT) {
            mDataset[i] = "Element #$i"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.recycler_view_frag, container, false)
        rootView.tag = TAG

        mRecyclerView = rootView.findViewById(R.id.recyclerView)

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = LinearLayoutManager(activity)

        mCurrentLayoutManagerType = LINEAR_LAYOUT_MANAGER

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (savedInstanceState.getSerializable(
                KEY_LAYOUT_MANAGER
            ) as LayoutManagerType?)!!
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType)

        mAdapter = CustomAdapter(mDataset)
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter)

        mLinearLayoutRadioButton = rootView.findViewById(R.id.linear_layout_rb)
        mLinearLayoutRadioButton.setOnClickListener {
            setRecyclerViewLayoutManager(
                LINEAR_LAYOUT_MANAGER
            )
        }

        mGridLayoutRadioButton = rootView.findViewById(R.id.grid_layout_rb)
        mGridLayoutRadioButton.setOnClickListener {
            setRecyclerViewLayoutManager(
                GRID_LAYOUT_MANAGER
            )
        }

        return rootView
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    private fun setRecyclerViewLayoutManager(layoutManagerType: LayoutManagerType?) {
        var scrollPosition = 0

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.layoutManager != null) {
            // java.lang.ClassCastException:
            // androidx.recyclerview.widget.StaggeredGridLayoutManager cannot be cast to
            // androidx.recyclerview.widget.LinearLayoutManager
            scrollPosition = (mRecyclerView.layoutManager as LinearLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        }

        if (Objects.requireNonNull(layoutManagerType) == GRID_LAYOUT_MANAGER) {
            mLayoutManager = GridLayoutManager(activity, SPAN_COUNT)
            mCurrentLayoutManagerType = GRID_LAYOUT_MANAGER
        } else {
            mLayoutManager = LinearLayoutManager(activity)
            mCurrentLayoutManagerType = LINEAR_LAYOUT_MANAGER
        }

        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.scrollToPosition(scrollPosition)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType)
        super.onSaveInstanceState(savedInstanceState)
    }

    enum class LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
}
