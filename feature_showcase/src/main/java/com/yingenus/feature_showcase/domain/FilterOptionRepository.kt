package com.yingenus.feature_showcase.domain

import com.yingenus.feature_showcase.domain.dto.FilterOption
import kotlinx.coroutines.flow.Flow

internal interface FilterOptionRepository {
    suspend fun getBrands(): List<FilterOption.Brand>
    suspend fun getPrises(): List<FilterOption.Prise>
    suspend fun getSizes(): List<FilterOption.Size>
}