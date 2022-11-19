package com.yingenus.feature_showcase.domain.repository

import com.yingenus.feature_showcase.domain.dto.Category
import com.yingenus.feature_showcase.domain.dto.FilterOption
import com.yingenus.feature_showcase.domain.dto.FilterOptions
import kotlinx.coroutines.flow.Flow

internal interface FilterOptionRepository {
    suspend fun getFilterOptions(category: Category): FilterOptions
}