package com.yingenus.feature_product_details.domain.usecase

import com.yingenus.core.Result
import com.yingenus.feature_product_details.di.ProductID
import com.yingenus.feature_product_details.domain.dto.ProductDetailed
import com.yingenus.feature_product_details.domain.repository.ProductRepository
import javax.inject.Inject

internal class GetProductDetailedUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository,
    @ProductID private val productId: Int
): GetProductDetailedUseCase{
    override suspend fun invoke(): Result<ProductDetailed> {
        return productRepository.getProductDetailed(productId)
    }
}