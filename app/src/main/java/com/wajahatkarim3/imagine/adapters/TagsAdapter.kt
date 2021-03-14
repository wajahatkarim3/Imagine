/*
* Copyright 2021 Wajahat Karim (https://wajahatkarim.com)
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
package com.wajahatkarim3.imagine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wajahatkarim3.imagine.R
import com.wajahatkarim3.imagine.databinding.TagItemLayoutBinding
import com.wajahatkarim3.imagine.model.TagModel

class TagsAdapter(val onTagSelected: (tag: TagModel, position: Int) -> Unit) : RecyclerView.Adapter<TagsAdapter.TagViewHolder>() {

    private val tagItems: ArrayList<TagModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        var binding = TagItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tagItems[position], position)
    }

    override fun getItemCount() = tagItems.size

    fun updateItems(tagsList: List<TagModel>) {
        tagItems.clear()
        tagItems.addAll(tagsList)
        notifyDataSetChanged()
    }

    inner class TagViewHolder(val itemBinding: TagItemLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(tagModel: TagModel, position: Int) {
            itemBinding.apply {
                txtTagName.text = tagModel.tagName
                imgTag.load(tagModel.imageUrl) {
                    placeholder(R.color.color_box_background)
                    crossfade(true)
                }

                cardTag.setOnClickListener {
                    onTagSelected(tagModel, position)
                }
            }
        }
    }
}
