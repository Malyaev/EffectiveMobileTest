package com.yingenus.feature_mycart.di

import com.yingenus.api_network.api.NetworkApi
import dagger.Module
import dagger.Provides

@Module
internal class ImageLoaderModule {

    @Provides
    fun provideImageLoader(networkApi: NetworkApi) = networkApi.getImageLoader()
}