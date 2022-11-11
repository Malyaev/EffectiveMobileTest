package com.yingenus.api_network.data.image

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.Target

internal class SvgSoftwareLayerSetter : RequestListener<BitmapDrawable>{
    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<BitmapDrawable>?,
        isFirstResource: Boolean
    ): Boolean {
        val view = (target as ImageViewTarget<*>).view;
        view.setLayerType(ImageView.LAYER_TYPE_NONE, null)
        return false
    }

    override fun onResourceReady(
        resource: BitmapDrawable?,
        model: Any?,
        target: Target<BitmapDrawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        val view = (target as ImageViewTarget<*>).view
        view.setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null)
        return false
    }
}