package com.yingenus.feature_showcase.di

import com.yingenus.feature_showcase.domain.usecase.*
import com.yingenus.feature_showcase.domain.usecase.GetCategoryUseCase
import com.yingenus.feature_showcase.domain.usecase.GetCategoryUseCaseImpl
import com.yingenus.feature_showcase.domain.usecase.GetFilterOptionsUseCase
import com.yingenus.feature_showcase.domain.usecase.GetFilterOptionsUseCaseImpl
import dagger.Binds
import dagger.Module

@Module(includes = [RepositoryModule::class])
internal abstract class UseCaseModule {
    @Binds abstract fun provideGetCategoryUseCase(
        getCategoryUseCaseImpl: GetCategoryUseCaseImpl
    ): GetCategoryUseCase

    @Binds abstract fun provideGetFilterOptionsUseCase(
        getFilterOptionsUseCaseImpl: GetFilterOptionsUseCaseImpl
    ): GetFilterOptionsUseCase

    @Binds abstract fun provideGetLocationsUseCase(
        getLocationsUseCaseImpl: GetLocationsUseCaseImpl
    ): GetLocationsUseCase

    @Binds abstract fun provideGetSelectedLocationUseCase(
        getSelectedLocationUseCaseImpl: GetSelectedLocationUseCaseImpl
    ): GetSelectedLocationUseCase

    @Binds abstract fun provideGetHomeShowcaseUseCase(
        getHomeShowcaseUseCaseImpl: GetHomeShowcaseUseCaseImpl
    ): GetHomeShowcaseUseCase

    @Binds abstract fun provideLikeBestsellerUseCase(
        likeBestsellerUseCaseImpl: LikeBestsellerUseCaseImpl
    ): LikeBestsellerUseCase
}