package com.yingenus.feature_product_details.di

import com.yingenus.api_network.api.ImageLoader
import com.yingenus.api_network.api.NetworkApi

interface ProductDependenciesProvider {
    fun getNetworkApi(): NetworkApi
    fun getImageLoader(): ImageLoader
}