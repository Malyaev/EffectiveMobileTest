package com.yingenus.feature_showcase.data.mock

import com.yingenus.feature_showcase.domain.dto.Category
import com.yingenus.feature_showcase.domain.repository.FilterOptionRepository
import com.yingenus.feature_showcase.domain.dto.FilterOption
import com.yingenus.feature_showcase.domain.dto.FilterOptions
import javax.inject.Inject

internal class MockFilterOptionRepository @Inject constructor(): FilterOptionRepository {
    override suspend fun getFilterOptions(category: Category): FilterOptions {
        return FilterOptions(getBrands(),getPrises(),getSizes())
    }

    private fun getBrands(): List<FilterOption.Brand> {
        return listOf(FilterOption.Brand("Samsung"), FilterOption.Brand("Apple"))
    }

    private fun getPrises(): List<FilterOption.Prise> {
        return listOf(FilterOption.Prise(0,10000))
    }

    private fun getSizes(): List<FilterOption.Size> {
        return listOf(FilterOption.Size(4.5f,5.5f))
    }
}