package com.yingenus.feature_mycart.domain

import com.yingenus.core.Result
import com.yingenus.feature_mycart.domain.dto.Cart
import kotlinx.coroutines.flow.Flow

internal interface CartRepository {
    fun getMyCart(): Flow<Result<Cart>>
}