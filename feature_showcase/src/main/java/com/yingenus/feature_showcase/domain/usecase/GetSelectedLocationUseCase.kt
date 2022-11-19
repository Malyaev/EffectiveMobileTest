package com.yingenus.feature_showcase.domain.usecase

import com.yingenus.feature_showcase.domain.dto.Location

internal interface GetSelectedLocationUseCase {
    suspend operator fun invoke(): Location
}