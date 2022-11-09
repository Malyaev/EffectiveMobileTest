package com.yingenus.effectivemobiletest.di

import com.yingenus.api_network.api.NetworkApi
import com.yingenus.feature_mycart.di.FeatureMyCartDependency
import com.yingenus.feature_product_details.di.ProductDependencies
import com.yingenus.feature_showcase.di.ShowcaseDependencies
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module( includes = [NetworkApiModule::class])
class FeatureModulesDependencyModule {


    @Provides
    fun provideShowcaseDependencies(networkApi: Provider<NetworkApi>): ShowcaseDependencies{
        return object : ShowcaseDependencies{
            override fun getNetworkApi(): NetworkApi {
                return networkApi.get()!!
            }
        }
    }

    @Provides
    fun provideProductDependencies(networkApi: Provider<NetworkApi>): ProductDependencies{
        return object : ProductDependencies{
            override fun getNetworkApi(): NetworkApi {
                return networkApi.get()!!
            }
        }
    }

    @Provides
    fun provideMyCartDependency(networkApi: Provider<NetworkApi>): FeatureMyCartDependency{
        return object : FeatureMyCartDependency{
            override fun getNetworkApi(): NetworkApi {
                return networkApi.get()!!
            }
        }
    }

}