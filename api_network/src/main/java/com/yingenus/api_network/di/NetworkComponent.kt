package com.yingenus.api_network.di

import com.yingenus.api_network.api.NetworkApi
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [RepositoryModule::class],
)
@Singleton
internal abstract class NetworkComponent : NetworkApi{
    companion object{
        @Volatile
        private var networkComponent : NetworkComponent? = null

        fun initAndGet(): NetworkComponent{
            if (networkComponent == null){
                synchronized(NetworkComponent::class){
                    if (networkComponent == null){
                        networkComponent = DaggerNetworkComponent.builder()
                            .build()
                    }
                }
            }
            return networkComponent!!;
        }
    }
}