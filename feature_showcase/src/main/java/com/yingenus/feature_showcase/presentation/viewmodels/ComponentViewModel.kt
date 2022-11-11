package com.yingenus.feature_showcase.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yingenus.feature_showcase.di.FeatureComponent
import com.yingenus.feature_showcase.di.FeatureShowcaseComponentHolder

internal class ComponentViewModel : ViewModel() {

    @Volatile
    private var featureComponent: FeatureComponent? = null

    fun getFeatureComponent( context: Context): FeatureComponent{
        if (featureComponent == null){
            synchronized(ComponentViewModel::class){
                if (featureComponent == null){
                    featureComponent = FeatureShowcaseComponentHolder.getFeatureComponent(context)
                }
            }
        }
        return featureComponent!!
    }

}