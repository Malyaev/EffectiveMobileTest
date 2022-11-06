package com.yingenus.api_network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
internal class RetrofitModule {

    @Provides
    @Singleton
    @MockyRetrofit
    fun provideMockyRetrofit( context : Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .build()
    }

}