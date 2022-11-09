package com.yingenus.feature_product_details.domain

import com.yingenus.feature_product_details.domain.dto.ProductDetailed
import kotlinx.coroutines.flow.Flow
import com.yingenus.core.Result

internal interface ProductRepository {
    fun getProductDetailed(productId : Int): Flow<Result<ProductDetailed>>
}