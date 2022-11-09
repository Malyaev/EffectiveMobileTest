package com.yingenus.feature_mycart.di

import com.yingenus.api_network.api.NetworkApi

interface FeatureMyCartDependency {
    fun getNetworkApi(): NetworkApi
}