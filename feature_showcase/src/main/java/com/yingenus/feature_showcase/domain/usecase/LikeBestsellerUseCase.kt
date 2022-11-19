package com.yingenus.feature_showcase.domain.usecase

import com.yingenus.core.Result
import com.yingenus.feature_showcase.domain.dto.BestSellerProduct

internal interface LikeBestsellerUseCase {
    suspend operator fun invoke(bestSeller: BestSellerProduct, isFavorites : Boolean): Result<BestSellerProduct>
}