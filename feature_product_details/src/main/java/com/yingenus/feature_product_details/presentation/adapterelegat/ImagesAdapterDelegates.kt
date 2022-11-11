package com.yingenus.feature_product_details.presentation.adapterelegat

import android.widget.ImageView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.yingenus.api_network.api.ImageLoader
import com.yingenus.feature_product_details.R
import com.yingenus.feature_product_details.presentation.adapteritem.Image
import com.yingenus.feature_product_details.presentation.adapteritem.ImageItem


internal fun getImageAdapterDelegate(imageLoader: ImageLoader) = adapterDelegate<Image,ImageItem>(R.layout.devise_photo){
    val image : ImageView = findViewById(R.id.photo)

    bind {
        imageLoader.loadImage(item.uri,image)
    }
}