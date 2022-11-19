package com.yingenus.feature_showcase.domain.usecase

import com.yingenus.core.Result
import com.yingenus.feature_showcase.domain.dto.BestSellerProduct
import com.yingenus.feature_showcase.domain.repository.StoreRepository
import javax.inject.Inject

internal class LikeBestsellerUseCaseImpl @Inject constructor(
    private val storeRepository: StoreRepository
): LikeBestsellerUseCase{
    override suspend fun invoke(bestSeller: BestSellerProduct, isFavorites : Boolean): Result<BestSellerProduct> {
        return storeRepository.likeBestSeller(bestSeller,isFavorites)
    }
}