package com.yingenus.feature_showcase.domain.usecase

import com.yingenus.feature_showcase.domain.dto.Location
import com.yingenus.feature_showcase.domain.repository.LocationRepository
import javax.inject.Inject

internal class GetLocationsUseCaseImpl @Inject constructor(
    private val locationRepository: LocationRepository
): GetLocationsUseCase {
    override suspend fun invoke(): List<Location> {
        return locationRepository.getAllLocations()
    }
}