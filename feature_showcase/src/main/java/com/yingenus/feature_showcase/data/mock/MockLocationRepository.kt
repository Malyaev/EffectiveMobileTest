package com.yingenus.feature_showcase.data.mock

import com.yingenus.feature_showcase.domain.repository.LocationRepository
import com.yingenus.feature_showcase.domain.dto.Location
import javax.inject.Inject

internal class MockLocationRepository @Inject constructor(): LocationRepository {
    override suspend fun getSelectedLocation(): Location {
        return Location("Zihuatanejo, Gro")
    }

    override suspend fun getAllLocations(): List<Location> {
        return listOf(Location("Zihuatanejo, Gro"))
    }
}