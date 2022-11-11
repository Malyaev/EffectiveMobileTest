package com.yingenus.feature_showcase.di

import android.content.Context
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

    internal fun getFeatureComponent(context: Context): FeatureComponent{
        require(dependencies != null){
            "FeatureShowcaseComponentHolder is not initialized"
        }
        return FeatureComponent.init(dependencies!!,context)
    }

    private fun dependenciesInitialized(): Nothing{
        throw IllegalStateException("FeatureShowcaseComponentHolder is already initialized")
    }

}