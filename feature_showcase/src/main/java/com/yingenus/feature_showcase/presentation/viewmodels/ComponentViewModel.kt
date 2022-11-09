package com.yingenus.feature_showcase.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yingenus.feature_showcase.di.FeatureComponent
import com.yingenus.feature_showcase.di.FeatureShowcaseComponentHolder

internal class ComponentViewModel : ViewModel() {

    fun getFeatureComponent(): FeatureComponent{
        return FeatureShowcaseComponentHolder.getFeatureComponent()
    }

}