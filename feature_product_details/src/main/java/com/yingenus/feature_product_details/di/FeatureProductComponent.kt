package com.yingenus.feature_product_details.di

import com.yingenus.feature_product_details.presentation.view.ProductFragment
import dagger.BindsInstance
import dagger.Component

@Component(modules = [FeatureProductModule::class], dependencies = [ProductDependenciesProvider::class])
@ProductDetails
internal abstract class FeatureProductComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun productId(@ProductID productId: Int): Builder
        fun productDependenciesProvider(dependencies: ProductDependenciesProvider ): Builder
        fun build(): FeatureProductComponent
    }

    companion object{
        fun init(dependencies : ProductDependenciesProvider, productId: Int): FeatureProductComponent{
            return DaggerFeatureProductComponent
                .builder()
                .productId(productId)
                .productDependenciesProvider(dependencies)
                .build()
        }
    }

    abstract fun injectProductFragment(productFragment: ProductFragment)
}