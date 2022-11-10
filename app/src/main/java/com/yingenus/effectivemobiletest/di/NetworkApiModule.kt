package com.yingenus.effectivemobiletest.di

import android.content.Context
import com.yingenus.api_network.api.NetworkApi
import com.yingenus.api_network.di.NetworkApiHolder
import com.yingenus.api_network.di.NetworkDependency
import dagger.Module
import dagger.Provides

@Module
class NetworkApiModule {
    @Provides
    fun provideNetworkApi( context: Context): NetworkApi{

        NetworkApiHolder.init(object : NetworkDependency{
            override fun getContext() = context

        })
        return NetworkApiHolder.getNetworkApi()
    }
}