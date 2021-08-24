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
import com.wajahatkarim3.imagine.databinding.PhotoItemLayoutBinding
import com.wajahatkarim3.imagine.model.PhotoModel

class PhotosAdapter(val onPhotoSelected: (photo: PhotoModel, position: Int) -> Unit) : RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    private val photoItems: ArrayList<PhotoModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            PhotoItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photoItems[position], position)
    }

    override fun getItemCount() = photoItems.size

    fun updateItems(photosList: List<PhotoModel>) {
        photoItems.clear()
        photoItems.addAll(photosList)
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(private val itemBinding: PhotoItemLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(photoModel: PhotoModel, position: Int) {
            itemBinding.apply {
                item = photoModel
                cardPhoto.setOnClickListener {
                    onPhotoSelected(photoModel, position)
                }
            }
        }
    }
}
