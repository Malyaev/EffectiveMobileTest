package com.yingenus.effectivemobiletest.di

import com.yingenus.api_network.api.NetworkApi
import com.yingenus.api_network.di.NetworkApiHolder
import dagger.Module
import dagger.Provides

@Module
class NetworkApiModule {
    @Provides
    fun provideNetworkApi(): NetworkApi{
        NetworkApiHolder.init()
        return NetworkApiHolder.getNetworkApi()
    }
}