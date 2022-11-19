package com.yingenus.feature_product_details.di

import com.yingenus.feature_product_details.data.ProductRepositoryImpl
import com.yingenus.feature_product_details.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module

@Module
internal abstract class RepositoryModule {

    @Binds abstract fun provideProductRepository( productRepository: ProductRepositoryImpl): ProductRepository

}