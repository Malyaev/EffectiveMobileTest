package com.yingenus.feature_showcase.domain.usecase

import com.yingenus.core.Result
import com.yingenus.feature_showcase.domain.dto.Category
import com.yingenus.feature_showcase.domain.dto.HomeShowcase

internal interface GetHomeShowcaseUseCase {
    suspend operator fun invoke(category: Category): Result<HomeShowcase>
}