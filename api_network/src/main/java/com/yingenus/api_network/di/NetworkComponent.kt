package com.yingenus.api_network.di

import com.yingenus.api_network.api.NetworkApi
import dagger.Component

@Component(modules = [RetrofitModule::class])
internal abstract class NetworkComponent : NetworkApi{

    @Component.Builder
    interface Builder{
        fun dependency(dependency: NetworkDependency)
        fun build(): NetworkComponent
    }

}