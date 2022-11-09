package com.yingenus.feature_product_details.di

object FeatureProductComponentHolder {

    @Volatile
    private var dependencies: ProductDependencies? = null

    fun init( showcaseDependencies: ProductDependencies){
        if (dependencies == null){
            synchronized(FeatureProductComponentHolder::class){
                if (dependencies == null){
                    dependencies = showcaseDependencies
                }
                else{
                    dependenciesInitialized()
                }
            }
        }else{
            dependenciesInitialized()
        }
    }

    internal fun getFeatureComponent(): FeatureProductComponent{
        require(dependencies != null){
            "FeatureShowcaseComponentHolder is not initialized"
        }
        return FeatureProductComponent.init(dependencies!!)
    }

    private fun dependenciesInitialized(): Nothing{
        throw IllegalStateException("FeatureShowcaseComponentHolder is already initialized")
    }
}