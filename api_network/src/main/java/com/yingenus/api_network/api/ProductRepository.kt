package com.yingenus.api_network.api

import com.yingenus.api_network.api.dto.Product
import com.yingenus.core.Result
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProduct(id : Int): Result<Product>
}