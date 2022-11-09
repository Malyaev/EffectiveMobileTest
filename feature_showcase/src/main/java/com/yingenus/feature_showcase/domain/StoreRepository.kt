package com.yingenus.feature_showcase.domain

import com.yingenus.feature_showcase.domain.dto.HomeShowcase
import kotlinx.coroutines.flow.Flow
import com.yingenus.core.Result

internal interface StoreRepository {
    fun getHomeShowcase(): Flow<Result<HomeShowcase>>
}