package com.yingenus.feature_product_details.di

import com.yingenus.feature_product_details.presentation.view.ProductFragment
import dagger.Component

@Component(modules = [RepositoryModule::class], dependencies = [ProductDependencies::class])
internal abstract class FeatureProductComponent {

    companion object{
        fun init(dependencies : ProductDependencies): FeatureProductComponent{
            TODO()
        }
    }

    abstract fun injectProductFragment(productFragment: ProductFragment)
}