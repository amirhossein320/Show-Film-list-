package com.amir.testapp

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imgFromUrl")
fun setImgFromUrl(img: ImageView, url: String?) {
    Glide.with(img.context).load(url).into(img)
}

