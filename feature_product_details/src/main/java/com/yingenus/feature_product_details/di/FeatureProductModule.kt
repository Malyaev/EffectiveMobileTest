package com.yingenus.feature_product_details.di

import dagger.Module

@Module(includes = [RepositoryModule::class,ImageLoaderModule::class])
class FeatureProductModule {
}