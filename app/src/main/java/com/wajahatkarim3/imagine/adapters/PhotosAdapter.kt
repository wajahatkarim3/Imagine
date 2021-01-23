package com.wajahatkarim3.imagine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.wajahatkarim3.imagine.R
import com.wajahatkarim3.imagine.databinding.PhotoItemLayoutBinding
import com.wajahatkarim3.imagine.model.PhotoModel

class PhotosAdapter(val onPhotoSelected: (photo: PhotoModel, position: Int) -> Unit): RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    private val photoItems: ArrayList<PhotoModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        var binding = PhotoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhotoViewHolder(binding)
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

    inner class PhotoViewHolder(val itemBinding: PhotoItemLayoutBinding): RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(photoModel: PhotoModel, position: Int) {
            itemBinding.apply {
                imgPhoto.load(photoModel.urls.thumb) {
                    placeholder(R.color.color_box_background)
                    crossfade(true)
                }

                cardPhoto.setOnClickListener {
                    onPhotoSelected(photoModel, position)
                }

            }
        }
    }
}