package com.yingenus.feature_mycart.di

import com.yingenus.api_network.api.ImageLoader
import com.yingenus.api_network.api.NetworkApi


interface MyCartDependencyProvider {
    fun getNetworkApi(): NetworkApi
    fun getImageLoader(): ImageLoader
}