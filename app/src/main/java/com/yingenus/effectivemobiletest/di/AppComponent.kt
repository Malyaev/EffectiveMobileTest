package com.yingenus.effectivemobiletest.di

import dagger.Component

@Component(modules = [InitializerModule::class])
abstract class AppComponent {

    companion object{
        fun initAndGet(): AppComponent{
            return DaggerAppComponent.builder().build()
        }
    }

    abstract fun getFeatureModulesInitializer(): FeatureModulesInitializer

}