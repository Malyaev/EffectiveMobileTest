package com.yingenus.api_network.data.image

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.yingenus.api_network.api.ImageLoader
import javax.inject.Inject

internal class GlideImageLoader @Inject constructor(private val context: Context): ImageLoader{

    init {
        Glide.get(context).setMemoryCategory(MemoryCategory.LOW)
    }

    override fun loadImage(url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

    override fun loadImage(uri: Uri, imageView: ImageView) {
        Glide.with(context)
            .load(uri)
            .into(imageView)
    }

    override fun clear(imageView: ImageView) {
        Glide.with(context).clear(imageView)
    }
}