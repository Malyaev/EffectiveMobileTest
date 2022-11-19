package com.yingenus.feature_showcase.domain.usecase

import com.yingenus.feature_showcase.domain.dto.Category

internal interface GetCategoryUseCase {
    suspend operator fun invoke(): List<Category>
}