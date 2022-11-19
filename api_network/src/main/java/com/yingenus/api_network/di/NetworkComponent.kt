package com.yingenus.api_network.di

import com.yingenus.api_network.api.NetworkApi
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [NetworkModule::class]
)
@Singleton
interface NetworkComponent : NetworkApi{
    companion object{
        fun create(): NetworkComponent{
            TODO()
        }
    }
}