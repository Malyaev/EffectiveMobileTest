package com.yingenus.feature_showcase.domain.usecase

import com.yingenus.core.Result
import com.yingenus.feature_showcase.domain.dto.Category
import com.yingenus.feature_showcase.domain.dto.HomeShowcase
import com.yingenus.feature_showcase.domain.repository.StoreRepository
import javax.inject.Inject

internal class GetHomeShowcaseUseCaseImpl @Inject constructor(
    private var storeRepository: StoreRepository
): GetHomeShowcaseUseCase {
    override suspend fun invoke(category: Category): Result<HomeShowcase> {
        return storeRepository.getHomeShowcase()
    }
}