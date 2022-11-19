package com.yingenus.feature_mycart.di

import com.yingenus.feature_mycart.presentation.view.MyCartFragment
import dagger.Component

@Component(modules = [FeatureMyCartModule::class], dependencies = [MyCartDependencyProvider::class])
@MyChart
internal abstract class FeatureMyCartComponent {

    companion object{
        fun init(dependency: MyCartDependencyProvider): FeatureMyCartComponent{
            return DaggerFeatureMyCartComponent.builder()
                .myCartDependencyProvider(dependency)
                .build()
        }
    }

    abstract fun injectMyCartFragment(myCartFragment: MyCartFragment)
}