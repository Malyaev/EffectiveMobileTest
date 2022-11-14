package com.yingenus.feature_showcase.domain

import com.yingenus.feature_showcase.domain.dto.Location
import kotlinx.coroutines.flow.Flow

internal interface LocationRepository {
    suspend fun getSelectedLocation(): Location
    suspend fun getAllLocations(): List<Location>
}