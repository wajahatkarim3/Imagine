package com.wajahatkarim3.imagine.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.wajahatkarim3.imagine.R

@BindingAdapter("loadUrl")
fun loadImage(view: ImageView, url: String?) {
    url?.let {
        view.load(it){
            placeholder(R.color.color_box_background)
            crossfade(true)
        }
    }

}