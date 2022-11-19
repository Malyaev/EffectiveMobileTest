package com.yingenus.effectivemobiletest.di

import android.content.Context
import com.yingenus.api_network.api.NetworkApi
import com.yingenus.api_network.di.ImageLoaderComponent
import com.yingenus.feature_mycart.di.MyCartDependencyProvider
import com.yingenus.feature_product_details.di.ProductDependenciesProvider
import com.yingenus.feature_showcase.di.ShowcaseDependenciesProvider
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class], dependencies = [ImageLoaderComponent::class, NetworkApi::class])
abstract class AppComponent :
    ShowcaseDependenciesProvider,
    ProductDependenciesProvider,
    MyCartDependencyProvider {

    @Component.Builder
    interface Builder{
        fun networkApi( networkApi: NetworkApi): Builder
        fun imageLoaderComponent( imageLoaderComponent: ImageLoaderComponent): Builder
        fun build(): AppComponent
    }

}