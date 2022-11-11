package com.yingenus.feature_mycart.di

import com.yingenus.feature_mycart.presentation.view.MyCartFragment
import dagger.Component

@Component(modules = [FeatureMyCartModule::class], dependencies = [FeatureMyCartDependency::class])
@MyChart
internal abstract class FeatureMyCartComponent {

    companion object{
        fun init(dependency: FeatureMyCartDependency): FeatureMyCartComponent{
            return DaggerFeatureMyCartComponent.builder().featureMyCartDependency(dependency).build()
        }
    }

    abstract fun injectMyCartFragment(myCartFragment: MyCartFragment)
}