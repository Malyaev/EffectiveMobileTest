package com.yingenus.effectivemobiletest.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(modules = [InitializerModule::class])
abstract class AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(context: Context)
        fun build(): AppComponent
    }

    companion object{
        fun initAndGet(): AppComponent{
            //return DaggerAppComponent.builder().build()
            TODO()
        }
    }

    abstract fun getFeatureModulesInitializer(): FeatureModulesInitializer

}