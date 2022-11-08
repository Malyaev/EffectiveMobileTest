package com.yingenus.api_network.api

interface NetworkApi {
    fun getCartRepository(): CartRepository
    fun getProductRepository(): ProductRepository
    fun getStoreRepository(): StoreRepository
}