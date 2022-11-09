package com.yingenus.feature_mycart.di

import com.yingenus.feature_mycart.data.CartRepositoryImpl
import com.yingenus.feature_mycart.domain.repository.CartRepository
import dagger.Binds
import dagger.Module

@Module
internal abstract class RepositoryModule {
    @Binds abstract fun getCartRepository( cartRepositoryImpl: CartRepositoryImpl): CartRepository
}