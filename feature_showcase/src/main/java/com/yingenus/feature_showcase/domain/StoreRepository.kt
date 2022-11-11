package com.yingenus.feature_showcase.domain

import com.yingenus.feature_showcase.domain.dto.HomeShowcase
import kotlinx.coroutines.flow.Flow
import com.yingenus.core.Result
import com.yingenus.feature_showcase.domain.dto.BestSellerProduct

internal interface StoreRepository {
    fun getHomeShowcase(): Flow<Result<HomeShowcase>>
    fun likeBestSeller( bestSeller: BestSellerProduct, isFavorites : Boolean): Flow<Result<BestSellerProduct>>
}