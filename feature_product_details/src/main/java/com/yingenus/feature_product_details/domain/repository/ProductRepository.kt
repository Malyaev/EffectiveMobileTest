package com.yingenus.feature_product_details.domain.repository

import com.yingenus.feature_product_details.domain.dto.ProductDetailed
import kotlinx.coroutines.flow.Flow
import com.yingenus.core.Result

internal interface ProductRepository {
    suspend fun getProductDetailed(productId : Int): Result<ProductDetailed>
}