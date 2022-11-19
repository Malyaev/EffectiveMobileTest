package com.yingenus.feature_product_details.domain.usecase

import com.yingenus.core.Result
import com.yingenus.feature_product_details.domain.dto.ProductDetailed

internal interface GetProductDetailedUseCase {
    suspend operator fun invoke(): Result<ProductDetailed>
}