package com.yingenus.feature_mycart.di

import dagger.Module

@Module( includes = [RepositoryModule::class,ImageLoaderModule::class])
internal class FeatureMyCartModule {
}