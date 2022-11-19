package com.yingenus.core.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ComponentViewModel<T>(private val provider : (Application) -> T) : ViewModel() {

    open class ComponentViewModelFactory<T>(private val provider : (Application) -> T) : ViewModelProvider.Factory{
        override fun <S : ViewModel> create(modelClass: Class<S>): S {
            return when(modelClass){
                ComponentViewModel::class.java -> ComponentViewModel<T>(provider)
                else -> null
            } as S
        }
    }

    @Volatile
    private var component: T? = null

    fun getComponent( application: Application): T{
        if (component == null){
            synchronized(ComponentViewModel::class){
                if (component == null){
                    component = provider(application)
                }
            }
        }
        return component!!
    }

}