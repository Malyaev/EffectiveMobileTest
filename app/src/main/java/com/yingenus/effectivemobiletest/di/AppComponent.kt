package com.yingenus.effectivemobiletest.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(modules = [InitializerModule::class])
abstract class AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

    companion object{
        fun initAndGet(context: Context): AppComponent{
            return DaggerAppComponent.builder().context(context).build()
        }
    }

    abstract fun getFeatureModulesInitializer(): FeatureModulesInitializer

}