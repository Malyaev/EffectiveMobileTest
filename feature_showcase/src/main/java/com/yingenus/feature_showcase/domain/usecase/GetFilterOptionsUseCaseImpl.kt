package com.yingenus.feature_showcase.domain.usecase

import com.yingenus.feature_showcase.data.mock.MockFilterOptionRepository
import com.yingenus.feature_showcase.domain.dto.Category
import com.yingenus.feature_showcase.domain.dto.FilterOptions
import com.yingenus.feature_showcase.domain.repository.FilterOptionRepository
import javax.inject.Inject

internal class GetFilterOptionsUseCaseImpl @Inject constructor(
    private val filterOptionRepository: FilterOptionRepository):GetFilterOptionsUseCase {
    override suspend fun invoke(category: Category): FilterOptions {
        return filterOptionRepository.getFilterOptions(category)
    }
}