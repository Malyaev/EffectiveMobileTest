package com.yingenus.api_network.di

import android.content.Context
import com.yingenus.api_network.api.CartRepository
import com.yingenus.api_network.api.ProductRepository
import com.yingenus.api_network.api.StoreRepository
import com.yingenus.api_network.data.Retrofit.RetrofitRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [RetrofitModule::class])
internal abstract class RepositoryModule {

    @Binds
    abstract fun provideCartRepository( retrofitRepository: RetrofitRepository): CartRepository
    @Binds
    abstract fun provideProductRepository( retrofitRepository: RetrofitRepository): ProductRepository
    @Binds
    abstract fun provideStoreRepository( retrofitRepository: RetrofitRepository): StoreRepository

}