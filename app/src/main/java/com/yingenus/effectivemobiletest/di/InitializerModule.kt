package com.yingenus.effectivemobiletest.di

import dagger.Binds
import dagger.Module

@Module(includes = [FeatureModulesDependencyModule::class])
abstract class InitializerModule {
    @Binds abstract fun provideFeatureModulesInitializer(initializer : FeatureModulesInitializerImpl): FeatureModulesInitializer
}