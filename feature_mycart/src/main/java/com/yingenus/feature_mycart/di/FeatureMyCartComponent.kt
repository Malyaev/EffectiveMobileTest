package com.yingenus.feature_mycart.di

import com.yingenus.feature_mycart.presentation.view.MyCartFragment
import dagger.Component

@Component(modules = [RepositoryModule::class], dependencies = [FeatureMyCartDependency::class])
internal abstract class FeatureMyCartComponent {

    companion object{
        fun init(dependency: FeatureMyCartDependency): FeatureMyCartComponent{
            TODO()
        }
    }

    abstract fun injectMyCartFragment(myCartFragment: MyCartFragment)
}