package com.yingenus.feature_showcase.data.mock

import com.yingenus.feature_showcase.domain.FilterOptionRepository
import com.yingenus.feature_showcase.domain.dto.FilterOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MockFilterOptionRepository @Inject constructor(): FilterOptionRepository {
    override suspend fun getBrands(): List<FilterOption.Brand> {
        return listOf(FilterOption.Brand("Samsung"), FilterOption.Brand("Apple"))
    }

    override suspend fun getPrises(): List<FilterOption.Prise> {
        return listOf(FilterOption.Prise(0,10000))
    }

    override suspend fun getSizes(): List<FilterOption.Size> {
        return listOf(FilterOption.Size(4.5f,5.5f))
    }
}