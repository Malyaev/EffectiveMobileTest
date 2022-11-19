package com.yingenus.api_network.di

import android.content.Context
import com.yingenus.api_network.api.ImageLoader
import com.yingenus.api_network.data.image.GlideImageLoader
import dagger.Module
import dagger.Provides

@Module
class ImageLoaderModule {
    @Provides fun provideImageLoader( context: Context) : ImageLoader = GlideImageLoader(context)
}