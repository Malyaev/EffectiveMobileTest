package com.yingenus.feature_mycart.di

import com.yingenus.feature_mycart.domain.usecase.GetMyCartUsecase
import com.yingenus.feature_mycart.domain.usecase.GetMyCartUsecaseImpl
import dagger.Binds
import dagger.Module

@Module(includes = [RepositoryModule::class])
internal abstract class UsecaseModule {
    @Binds abstract fun provideGetMayCartUsecase( getMyCartUsecaseImpl: GetMyCartUsecaseImpl): GetMyCartUsecase
}