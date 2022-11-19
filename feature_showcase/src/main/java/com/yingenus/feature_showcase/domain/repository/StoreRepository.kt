package com.yingenus.feature_showcase.domain.repository

import com.yingenus.feature_showcase.domain.dto.HomeShowcase
import kotlinx.coroutines.flow.Flow
import com.yingenus.core.Result
import com.yingenus.feature_showcase.domain.dto.BestSellerProduct

internal interface StoreRepository {
    suspend fun getHomeShowcase(): Result<HomeShowcase>
    suspend fun likeBestSeller( bestSeller: BestSellerProduct, isFavorites : Boolean): Result<BestSellerProduct>
}