package com.yingenus.effectivemobiletest.di

import com.yingenus.feature_mycart.di.FeatureMyCartComponentHolder
import com.yingenus.feature_mycart.di.FeatureMyCartDependency
import com.yingenus.feature_product_details.di.FeatureProductComponentHolder
import com.yingenus.feature_product_details.di.ProductDependencies
import com.yingenus.feature_showcase.di.FeatureShowcaseComponentHolder
import com.yingenus.feature_showcase.di.ShowcaseDependencies
import javax.inject.Inject

class FeatureModulesInitializerImpl @Inject constructor(
    private val showcaseDependencies : ShowcaseDependencies,
    private val productDependencies : ProductDependencies,
    private val featureMyCartDependency : FeatureMyCartDependency
    ): FeatureModulesInitializer {

    override fun initialize() {
        FeatureShowcaseComponentHolder.init(showcaseDependencies)
        FeatureProductComponentHolder.init(productDependencies)
        FeatureMyCartComponentHolder.init(featureMyCartDependency)
    }

}