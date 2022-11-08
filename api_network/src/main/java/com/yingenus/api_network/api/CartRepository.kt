package com.yingenus.api_network.api

import com.yingenus.api_network.api.dto.Cart
import kotlinx.coroutines.flow.Flow
import com.yingenus.core.Result

interface CartRepository{
    fun getBasket(): Flow<Result<Cart>>
}