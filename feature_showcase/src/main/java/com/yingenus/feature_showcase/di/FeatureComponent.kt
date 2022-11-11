package com.yingenus.feature_showcase.di

import com.yingenus.api_network.di.DaggerNetworkComponent
import com.yingenus.feature_showcase.presentation.views.FilterDialog
import com.yingenus.feature_showcase.presentation.views.ShowcaseFragment
import dagger.Component

@Component(modules = [FeatureModule::class], dependencies = [ShowcaseDependencies::class])
@Showcase
internal abstract class FeatureComponent {

    companion object{
        fun init( dependencies: ShowcaseDependencies): FeatureComponent{
            return DaggerFeatureComponent.builder().showcaseDependencies(dependencies).build()
        }
    }

    abstract fun injectShowFragment(showcaseFragment: ShowcaseFragment)
    abstract fun injectFiltersDialog(filterDialog: FilterDialog)
}