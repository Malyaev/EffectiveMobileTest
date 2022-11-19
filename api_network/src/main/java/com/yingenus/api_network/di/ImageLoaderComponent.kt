package com.yingenus.api_network.di

import android.content.Context
import com.yingenus.api_network.api.ImageLoader
import dagger.BindsInstance
import dagger.Component

@Component( modules = [ImageLoaderModule::class])
interface ImageLoaderComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): ImageLoaderComponent
    }

    fun getImageLoader(): ImageLoader
}