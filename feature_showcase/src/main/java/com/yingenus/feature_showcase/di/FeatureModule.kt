package com.yingenus.feature_showcase.di

import dagger.Module

@Module( includes = [RepositoryModule::class, ImageLoaderModule::class])
class FeatureModule {
}