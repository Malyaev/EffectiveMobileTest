package com.yingenus.api_network.api

import com.yingenus.api_network.api.dto.HomeStore
import com.yingenus.api_network.api.dto.Showcase
import kotlinx.coroutines.flow.Flow
import com.yingenus.core.Result

interface StoreRepository {
    suspend fun getShowcase(): Result<Showcase>
}