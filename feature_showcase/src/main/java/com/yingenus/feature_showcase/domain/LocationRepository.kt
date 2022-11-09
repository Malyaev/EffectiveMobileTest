package com.yingenus.feature_showcase.domain

import com.yingenus.feature_showcase.domain.dto.Location
import kotlinx.coroutines.flow.Flow

internal interface LocationRepository {
    fun getSelectedLocation(): Flow<Location>
    fun getAllLocations(): Flow<List<Location>>
}