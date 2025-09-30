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

package com.wintmain.wBasis.ui.layout.slidingpanelayout

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wintmain.wBasis.databinding.SportsListItemBinding
import com.wintmain.wBasis.ui.layout.slidingpanelayout.SportsAdapter.SportsViewHolder

class SportsAdapter(private val onItemClicked: (Sport) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Sport, SportsViewHolder>(DiffCallback) {

    private lateinit var context: Context

    class SportsViewHolder(private var binding: SportsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sport: Sport, context: Context) {
            binding.title.text = context.getString(sport.titleResourceId)
            binding.subTitle.text = context.getString(sport.subTitleResourceId)
            // Load the images into the ImageView using the Coil library.
            binding.sportsImage.load(sport.imageResourceId)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SportsViewHolder {
        context = parent.context
        return SportsViewHolder(
            SportsListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SportsViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current, context)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Sport>() {
            override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean {
                return (oldItem.id == newItem.id ||
                    oldItem.titleResourceId == newItem.titleResourceId ||
                    oldItem.subTitleResourceId == newItem.subTitleResourceId
                    )
            }

            override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean {
                return oldItem == newItem
            }
        }
    }
}
