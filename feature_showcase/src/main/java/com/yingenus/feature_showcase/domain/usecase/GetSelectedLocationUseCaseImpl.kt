package com.yingenus.feature_showcase.domain.usecase

import com.yingenus.feature_showcase.domain.dto.Location
import com.yingenus.feature_showcase.domain.repository.LocationRepository
import javax.inject.Inject

internal class GetSelectedLocationUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository
): GetSelectedLocationUseCase {
    override suspend fun invoke(): Location {
        return locationRepository.getSelectedLocation()
    }
}