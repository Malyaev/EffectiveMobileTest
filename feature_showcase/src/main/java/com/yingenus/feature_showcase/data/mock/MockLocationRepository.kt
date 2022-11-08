package com.yingenus.feature_showcase.data.mock

import com.yingenus.feature_showcase.domain.LocationRepository
import com.yingenus.feature_showcase.domain.dto.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MockLocationRepository @Inject constructor(): LocationRepository {
    override fun getSelectedLocation(): Flow<Location> {
        return flow { emit(Location("Zihuatanejo, Gro")) }
    }

    override fun getAllLocations(): Flow<List<Location>> {
        return flow { emit(listOf(Location("Zihuatanejo, Gro"))) }
    }
}