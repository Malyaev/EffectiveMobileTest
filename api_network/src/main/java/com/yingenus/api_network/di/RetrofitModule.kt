package com.yingenus.api_network.di

import android.content.Context
import com.yingenus.api_network.api.ProductRepository
import com.yingenus.api_network.api.StoreRepository
import com.yingenus.api_network.data.Retrofit.RetrofitRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
internal abstract class RetrofitModule {

    @Provides
    @MockyRetrofit
    fun provideMockyRetrofit( context : Context): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .build()
    }

    @Binds
    abstract fun provideCartRepository(): RetrofitRepository
    @Binds
    abstract fun provideProductRepository(): ProductRepository
    @Binds
    abstract fun provideStoreRepository(): StoreRepository

}