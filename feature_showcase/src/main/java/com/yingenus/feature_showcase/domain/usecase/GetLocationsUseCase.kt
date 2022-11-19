package com.yingenus.feature_showcase.domain.usecase

import com.yingenus.feature_showcase.domain.dto.Location

internal interface GetLocationsUseCase {
    suspend operator fun invoke(): List<Location>
}