package com.yingenus.feature_showcase.presentation.viewmodels

import com.yingenus.core.viewmodels.ComponentViewModel
import com.yingenus.feature_showcase.di.FeatureComponent
import com.yingenus.feature_showcase.di.ShowcaseDependenciesProvider

internal class ShowcaseComponentViewModelFactory :
    ComponentViewModel.ComponentViewModelFactory<FeatureComponent>({ application ->
        require(application is ShowcaseDependenciesProvider){
            "application class must implenemt ProductDependenciesProvider"
        }
        FeatureComponent.init(application, application)
    })


