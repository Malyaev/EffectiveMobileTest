package com.yingenus.feature_product_details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.yingenus.feature_product_details.di.FeatureProductComponent
import com.yingenus.feature_product_details.di.FeatureProductComponentHolder

internal class ComponentViewModel : ViewModel() {

    @Volatile
    private var featureProductComponent: FeatureProductComponent? = null

    fun getFeatureComponent(): FeatureProductComponent{
        if (featureProductComponent == null){
            synchronized(ComponentViewModel::class){
                if (featureProductComponent == null){
                    featureProductComponent = FeatureProductComponentHolder.getFeatureComponent()
                }
            }
        }
        return featureProductComponent!!
    }

}