package com.yingenus.feature_showcase.data.mock

import com.yingenus.feature_showcase.domain.FilterOptionRepository
import com.yingenus.feature_showcase.domain.dto.FilterOption
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MockFilterOptionRepository @Inject constructor(): FilterOptionRepository {
    override fun getBrands(): Flow<List<FilterOption.Brand>> {
        return flow {
            emit(listOf(FilterOption.Brand("Samsung"), FilterOption.Brand("Apple")))
        }
    }

    override fun getPrises(): Flow<List<FilterOption.Prise>> {
        return flow {
            emit(listOf(FilterOption.Prise(0,10000)))
        }
    }

    override fun getSizes(): Flow<List<FilterOption.Size>> {
        return flow {
            emit(listOf(FilterOption.Size(4.5f,5.5f)))
        }
    }
}