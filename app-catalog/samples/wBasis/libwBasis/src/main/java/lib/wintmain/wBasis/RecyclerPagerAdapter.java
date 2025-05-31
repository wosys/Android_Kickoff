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

package lib.wintmain.wBasis;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

/** desc : PagerAdapter 封装 */
@SuppressWarnings("rawtypes")
public final class RecyclerPagerAdapter extends PagerAdapter {

    private final RecyclerView.Adapter mAdapter;

    public RecyclerPagerAdapter(RecyclerView.Adapter adapter) {
        if (adapter == null) {
            throw new IllegalArgumentException("are you ok?");
        }
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(
                new RecyclerView.AdapterDataObserver() {

                    @Override
                    public void onChanged() {
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onItemRangeChanged(int positionStart, int itemCount) {
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onItemRangeChanged(
                            int positionStart, int itemCount, @Nullable Object payload) {
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onItemRangeInserted(int positionStart, int itemCount) {
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onItemRangeRemoved(int positionStart, int itemCount) {
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                        notifyDataSetChanged();
                    }
                });
    }

    @Override
    public int getCount() {
        return mAdapter.getItemCount();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        RecyclerView.ViewHolder holder = mAdapter.createViewHolder(container, 0);
        container.addView(holder.itemView);
        mAdapter.onBindViewHolder(holder, position);
        return holder.itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
