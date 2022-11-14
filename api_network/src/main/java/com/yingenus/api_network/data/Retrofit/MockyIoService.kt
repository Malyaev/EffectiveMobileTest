package com.yingenus.api_network.data.Retrofit

import com.yingenus.api_network.api.dto.Cart
import com.yingenus.api_network.api.dto.HomeStore
import com.yingenus.api_network.api.dto.Product
import com.yingenus.api_network.api.dto.Showcase
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET

internal interface MockyIoService {
    @GET("53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    suspend fun getCart(): Cart?
    @GET("6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    suspend fun getProduct(): Product?
    @GET("654bd15e-b121-49ba-a588-960956b15175")
    suspend fun getShowcase(): Showcase?
}