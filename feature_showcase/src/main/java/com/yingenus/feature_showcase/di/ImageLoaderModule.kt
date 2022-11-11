package com.yingenus.feature_showcase.di

import com.yingenus.api_network.api.ImageLoader
import com.yingenus.api_network.api.NetworkApi
import dagger.Module
import dagger.Provides

@Module
internal class ImageLoaderModule {

    @Provides
    fun provideImageLoader(networkApi: NetworkApi): ImageLoader = networkApi.getImageLoader()
}