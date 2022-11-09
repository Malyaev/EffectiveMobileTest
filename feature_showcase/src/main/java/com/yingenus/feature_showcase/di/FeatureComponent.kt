package com.yingenus.feature_showcase.di

import com.yingenus.feature_showcase.presentation.views.ShowcaseFragment
import dagger.Component

@Component(modules = [RepositoryModule::class], dependencies = [ShowcaseDependencies::class])
internal abstract class FeatureComponent {

    companion object{
        fun init( dependencies: ShowcaseDependencies): FeatureComponent{
            TODO()
        }
    }

    abstract fun injectShowFragment(showcaseFragment: ShowcaseFragment)
}