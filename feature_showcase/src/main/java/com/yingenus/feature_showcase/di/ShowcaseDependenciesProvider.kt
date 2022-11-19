package com.yingenus.feature_showcase.di

import com.yingenus.api_network.api.ImageLoader
import com.yingenus.api_network.api.NetworkApi

interface ShowcaseDependenciesProvider {
    fun getNetworkApi(): NetworkApi
    fun getImageLoader(): ImageLoader
}