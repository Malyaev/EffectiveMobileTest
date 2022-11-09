package com.yingenus.feature_showcase.domain

import com.yingenus.feature_showcase.domain.dto.FilterOption
import kotlinx.coroutines.flow.Flow

internal interface FilterOptionRepository {
    fun getBrands(): Flow<List<FilterOption.Brand>>
    fun getPrises(): Flow<List<FilterOption.Prise>>
    fun getSizes(): Flow<List<FilterOption.Size>>
}