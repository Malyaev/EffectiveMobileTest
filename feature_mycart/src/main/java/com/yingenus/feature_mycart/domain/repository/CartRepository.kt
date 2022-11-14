package com.yingenus.feature_mycart.domain.repository

import com.yingenus.core.Result
import com.yingenus.feature_mycart.domain.dto.Cart
import kotlinx.coroutines.flow.Flow

internal interface CartRepository {
    suspend fun getMyCart(): Result<Cart>
}