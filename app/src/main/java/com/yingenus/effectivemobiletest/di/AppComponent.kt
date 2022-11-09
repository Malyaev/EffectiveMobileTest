package com.yingenus.effectivemobiletest.di

import dagger.Component

@Component
abstract class AppComponent {

    companion object{
        fun initAndGet(): AppComponent{
            TODO()
        }
    }

    abstract fun getFeatureModulesInitializer(): FeatureModulesInitializer

}