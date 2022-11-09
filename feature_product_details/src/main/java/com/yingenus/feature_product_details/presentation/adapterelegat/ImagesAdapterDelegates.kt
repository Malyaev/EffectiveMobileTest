package com.yingenus.feature_product_details.presentation.adapterelegat

import android.widget.ImageView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.yingenus.feature_product_details.R
import com.yingenus.feature_product_details.presentation.adapteritem.Image
import com.yingenus.feature_product_details.presentation.adapteritem.ImageItem


internal fun getImageAdapterDelegate() = adapterDelegate<Image,ImageItem>(R.layout.devise_photo){
    val image : ImageView = findViewById(R.id.photo)

    bind {
        image.setImageURI(item.uri)
    }
}