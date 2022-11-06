package com.yingenus.api_network.api

import com.yingenus.api_network.api.dto.HomeStore
import kotlinx.coroutines.flow.Flow
import com.yingenus.core.Result

interface StoreRepository {
    fun getHomeStore(): Flow<Result<HomeStore>>
}