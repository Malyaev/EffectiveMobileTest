package com.yingenus.feature_mycart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.yingenus.feature_mycart.di.FeatureMyCartComponent
import com.yingenus.feature_mycart.di.FeatureMyCartComponentHolder

internal class ComponentViewModel : ViewModel() {
    @Volatile
    private var featureProductComponent: FeatureMyCartComponent? = null

    fun getFeatureComponent(): FeatureMyCartComponent{
        if (featureProductComponent == null){
            synchronized(ComponentViewModel::class){
                if (featureProductComponent == null){
                    featureProductComponent = FeatureMyCartComponentHolder.getFeatureComponent()
                }
            }
        }
        return featureProductComponent!!
    }
}