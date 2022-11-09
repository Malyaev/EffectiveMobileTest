package com.yingenus.api_network.di

import com.yingenus.api_network.api.NetworkApi

object NetworkApiHolder {

    @Volatile
    private var networkApi : NetworkApi? = null;

    fun init(){
        if (networkApi == null){
            synchronized(NetworkApiHolder::class){
                if (networkApi == null){
                    networkApi = NetworkComponent.initAndGet()
                }
            }
        }
    }

    fun getNetworkApi(): NetworkApi{
        networkApi?: throw IllegalStateException("NetworkApiHolder is not initialized")
        return networkApi!!
    }
}