package com.yingenus.effectivemobiletest

import android.app.Application
import android.util.Log
import com.yingenus.api_network.api.ImageLoader
import com.yingenus.api_network.api.NetworkApi
import com.yingenus.api_network.di.DaggerImageLoaderComponent
import com.yingenus.api_network.di.DaggerNetworkComponent
import com.yingenus.api_network.di.ImageLoaderComponent
import com.yingenus.effectivemobiletest.di.AppComponent
import com.yingenus.effectivemobiletest.di.DaggerAppComponent
import com.yingenus.feature_mycart.di.MyCartDependencyProvider
import com.yingenus.feature_product_details.di.ProductDependenciesProvider
import com.yingenus.feature_showcase.di.ShowcaseDependenciesProvider

class EffectiveMobileTestApplication :
    Application(),
    ShowcaseDependenciesProvider,
    ProductDependenciesProvider,
    MyCartDependencyProvider
{

    companion object{
        private var appComponent : AppComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .networkApi(
                DaggerNetworkComponent.builder().build()
            )
            .imageLoaderComponent(
                DaggerImageLoaderComponent.builder().context(this).build()
            )
            .build()
    }

    override fun getNetworkApi(): NetworkApi =
        appComponent!!.getNetworkApi()

    override fun getImageLoader(): ImageLoader =
        appComponent!!.getImageLoader()
}