package com.yingenus.feature_showcase.di

import com.yingenus.feature_showcase.data.StoreRepositoryImpl
import com.yingenus.feature_showcase.data.mock.MockFilterOptionRepository
import com.yingenus.feature_showcase.data.mock.MockLocationRepository
import com.yingenus.feature_showcase.domain.FilterOptionRepository
import com.yingenus.feature_showcase.domain.LocationRepository
import com.yingenus.feature_showcase.domain.StoreRepository
import dagger.Binds
import dagger.Module

@Module
internal abstract class RepositoryModule {

    @Binds abstract fun provideStoreRepository( storeRepositoryImpl: StoreRepositoryImpl): StoreRepository

    @Binds abstract fun provideLocationRepository( mockLocationRepository: MockLocationRepository): LocationRepository

    @Binds abstract fun provideFilterOptionRepository( mockFilterOptionRepository: MockFilterOptionRepository): FilterOptionRepository

}