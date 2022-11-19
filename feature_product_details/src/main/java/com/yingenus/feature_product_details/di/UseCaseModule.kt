package com.yingenus.feature_product_details.di

import com.yingenus.feature_product_details.domain.usecase.GetProductDetailedUseCase
import com.yingenus.feature_product_details.domain.usecase.GetProductDetailedUseCaseImpl
import dagger.Binds
import dagger.Module

@Module(includes = [RepositoryModule::class])
internal abstract class UseCaseModule {
    @Binds abstract fun provideGetProductDetailedUseCase(
        getProductDetailedUseCaseImpl: GetProductDetailedUseCaseImpl
    ): GetProductDetailedUseCase
}