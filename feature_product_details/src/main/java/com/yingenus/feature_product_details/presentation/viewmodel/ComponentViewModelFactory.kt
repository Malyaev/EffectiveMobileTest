package com.yingenus.feature_product_details.presentation.viewmodel

import com.yingenus.core.viewmodels.ComponentViewModel
import com.yingenus.feature_product_details.di.FeatureProductComponent
import com.yingenus.feature_product_details.di.ProductDependenciesProvider

internal class ProductComponentViewModelFactory :
    ComponentViewModel.ComponentViewModelFactory<FeatureProductComponent>({ application ->
        require(application is ProductDependenciesProvider){
            "application class must implenemt ProductDependenciesProvider"
        }
        FeatureProductComponent.init(application)
    })