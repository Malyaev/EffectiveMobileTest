package com.yingenus.feature_mycart.di

object FeatureMyCartComponentHolder {
    @Volatile
    private var dependencies: FeatureMyCartDependency? = null

    fun init( showcaseDependencies: FeatureMyCartDependency){
        if (dependencies == null){
            synchronized(FeatureMyCartComponentHolder::class){
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

    internal fun getFeatureComponent(): FeatureMyCartComponent{
        require(dependencies != null){
            "FeatureShowcaseComponentHolder is not initialized"
        }
        return FeatureMyCartComponent.init(dependencies!!)
    }

    private fun dependenciesInitialized(): Nothing{
        throw IllegalStateException("FeatureShowcaseComponentHolder is already initialized")
    }
}