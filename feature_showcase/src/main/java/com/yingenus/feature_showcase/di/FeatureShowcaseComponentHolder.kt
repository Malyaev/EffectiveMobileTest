package com.yingenus.feature_showcase.di

import java.lang.ref.WeakReference

object FeatureShowcaseComponentHolder {

    @Volatile
    private var dependencies: ShowcaseDependencies? = null

    fun init( showcaseDependencies: ShowcaseDependencies){
        if (dependencies == null){
            synchronized(FeatureShowcaseComponentHolder::class){
                if (dependencies == null){
                    dependencies = showcaseDependencies
                }
                else{
                    dependenciesInitialized()
                }
            }
        }else{
            dependenciesInitialized()
        }
    }

    internal fun getFeatureComponent(): FeatureComponent{
        require(dependencies != null){
            "FeatureShowcaseComponentHolder is not initialized"
        }
        return FeatureComponent.init(dependencies!!)
    }

    private fun dependenciesInitialized(): Nothing{
        throw IllegalStateException("FeatureShowcaseComponentHolder is already initialized")
    }

}