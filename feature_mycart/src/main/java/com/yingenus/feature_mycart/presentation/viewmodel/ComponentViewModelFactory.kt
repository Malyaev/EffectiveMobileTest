package com.yingenus.feature_mycart.presentation.viewmodel

import com.yingenus.core.viewmodels.ComponentViewModel
import com.yingenus.feature_mycart.di.FeatureMyCartComponent
import com.yingenus.feature_mycart.di.MyCartDependencyProvider

internal class MyCartComponentViewModelFactory :
    ComponentViewModel.ComponentViewModelFactory<FeatureMyCartComponent>({ application ->
        require(application is MyCartDependencyProvider){
            "application class must implenemt MyCartDependencyProvider"
        }
        FeatureMyCartComponent.init(application)
    })