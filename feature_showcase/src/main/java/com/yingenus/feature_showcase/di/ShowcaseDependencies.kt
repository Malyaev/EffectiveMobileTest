package com.yingenus.feature_showcase.di

import com.yingenus.api_network.api.NetworkApi

interface ShowcaseDependencies {
    fun getNetworkApi(): NetworkApi
}