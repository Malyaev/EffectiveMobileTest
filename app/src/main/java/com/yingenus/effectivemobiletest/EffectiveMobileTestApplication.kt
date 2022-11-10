package com.yingenus.effectivemobiletest

import android.app.Application
import com.yingenus.effectivemobiletest.di.AppComponent
import com.yingenus.effectivemobiletest.di.FeatureModulesInitializer

class EffectiveMobileTestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val appComponent : AppComponent = AppComponent.initAndGet()
        val initializer : FeatureModulesInitializer = appComponent.getFeatureModulesInitializer()
        initializer.initialize()
    }
}