package com.yingenus.feature_showcase.domain.usecase

import com.yingenus.feature_showcase.domain.dto.Category
import com.yingenus.feature_showcase.domain.dto.FilterOptions

internal interface GetFilterOptionsUseCase {
    suspend fun invoke( category: Category): FilterOptions
}