package com.yingenus.feature_showcase.di

import com.yingenus.feature_showcase.data.StoreRepositoryImpl
import com.yingenus.feature_showcase.domain.StoreRepository
import dagger.Binds
import dagger.Module

@Module
internal abstract class RepositoryModule {

    @Binds abstract fun provideStoreRepository( storeRepositoryImpl: StoreRepositoryImpl): StoreRepository

}