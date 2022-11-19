package com.yingenus.feature_showcase.di

import android.content.Context
import com.yingenus.api_network.di.DaggerNetworkComponent
import com.yingenus.feature_showcase.presentation.views.FilterDialog
import com.yingenus.feature_showcase.presentation.views.ShowcaseFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [FeatureModule::class], dependencies = [ShowcaseDependenciesProvider::class])
@Showcase
internal abstract class FeatureComponent {

    @Component.Factory
    interface Factory{
        fun create(showcaseDependencies: ShowcaseDependenciesProvider, @BindsInstance context: Context): FeatureComponent
    }

    companion object{
        fun init( dependencies: ShowcaseDependenciesProvider, context: Context): FeatureComponent{
            return DaggerFeatureComponent.factory().create(dependencies,context)
        }
    }

    abstract fun injectShowFragment(showcaseFragment: ShowcaseFragment)
    abstract fun injectFiltersDialog(filterDialog: FilterDialog)
}