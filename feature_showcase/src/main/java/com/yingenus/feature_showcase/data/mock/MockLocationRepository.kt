package com.yingenus.feature_showcase.data.mock

import com.yingenus.feature_showcase.domain.LocationRepository
import com.yingenus.feature_showcase.domain.dto.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MockLocationRepository @Inject constructor(): LocationRepository {
    override suspend fun getSelectedLocation(): Location {
        return Location("Zihuatanejo, Gro")
    }

    override suspend fun getAllLocations(): List<Location> {
        return listOf(Location("Zihuatanejo, Gro"))
    }
}