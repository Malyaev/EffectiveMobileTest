package com.yingenus.feature_product_details.di

import com.yingenus.api_network.api.NetworkApi
import dagger.Module
import dagger.Provides

@Module
class ImageLoaderModule {

    @Provides
    fun provideImageLoader(networkApi: NetworkApi) = networkApi.getImageLoader()
}