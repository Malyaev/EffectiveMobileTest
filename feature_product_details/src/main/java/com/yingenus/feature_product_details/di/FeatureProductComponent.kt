package com.yingenus.feature_product_details.di

import com.yingenus.feature_product_details.presentation.view.ProductFragment
import dagger.Component

@Component(modules = [FeatureProductModule::class], dependencies = [ProductDependenciesProvider::class])
@ProductDetails
internal abstract class FeatureProductComponent {

    companion object{
        fun init(dependencies : ProductDependenciesProvider): FeatureProductComponent{
            return DaggerFeatureProductComponent
                .builder()
                .productDependenciesProvider(dependencies)
                .build()
        }
    }

    abstract fun injectProductFragment(productFragment: ProductFragment)
}