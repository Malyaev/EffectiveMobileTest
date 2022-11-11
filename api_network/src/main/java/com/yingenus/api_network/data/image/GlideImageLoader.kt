package com.yingenus.api_network.data.image

import android.R
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.yingenus.api_network.api.ImageLoader
import javax.inject.Inject


internal class GlideImageLoader @Inject constructor(private val context: Context): ImageLoader{

    init {
        Glide.get(context).setMemoryCategory(MemoryCategory.LOW)
    }

    private val requestSVG by lazy {
        Glide.with(context)
            .`as`(BitmapDrawable::class.java)
            .transition(withCrossFade())
            .listener(SvgSoftwareLayerSetter())
    }

    override fun loadImage(url: String, imageView: ImageView, width: Int, height: Int) {
        clear(imageView)
        if (url.contains(".svg"))
            requestSVG.load(url).override(width, height).into(imageView)
        else
            Glide.with(context).load(url).override(width, height).into(imageView)
    }

    override fun loadImage(uri: Uri, imageView: ImageView, width: Int, height: Int) {
        clear(imageView)
         if (uri.toString().contains(".svg"))
            requestSVG.load(uri).override(width, height).into(imageView)
        else
            Glide.with(context).load(uri).override(width, height).into(imageView)
    }

    override fun loadImage(url: String, imageView: ImageView) {
        clear(imageView)
        if (url.contains(".svg"))
            requestSVG.load(url).circleCrop().into(imageView)
        else
            Glide.with(context).load(url).into(imageView)
    }

    override fun loadImage(uri: Uri, imageView: ImageView) {
        clear(imageView)
        if (uri.toString().contains(".svg"))
            requestSVG.load(uri).circleCrop().into(imageView)
        else
            Glide.with(context).load(uri).into(imageView)
    }

    override fun clear(imageView: ImageView) {
        Glide.with(context).clear(imageView)
        imageView.setImageDrawable(null)
    }
}