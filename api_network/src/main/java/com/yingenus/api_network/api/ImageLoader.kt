package com.yingenus.api_network.api

import android.net.Uri
import android.widget.ImageView

interface ImageLoader {
    fun loadImage(url : String, imageView: ImageView)
    fun loadImage( uri: Uri, imageView: ImageView)
    fun loadImage( url : String, imageView: ImageView, width : Int, height : Int)
    fun loadImage( uri: Uri, imageView: ImageView, width : Int, height : Int)
    fun clear(imageView: ImageView)
}