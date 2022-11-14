package com.yingenus.feature_showcase.domain

import com.yingenus.feature_showcase.domain.dto.Category
import kotlinx.coroutines.flow.Flow

internal interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}