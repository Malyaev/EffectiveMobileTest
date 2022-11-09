package com.yingenus.feature_product_details.di

import com.yingenus.api_network.api.NetworkApi

interface ProductDependencies {
    fun getNetworkApi(): NetworkApi
}