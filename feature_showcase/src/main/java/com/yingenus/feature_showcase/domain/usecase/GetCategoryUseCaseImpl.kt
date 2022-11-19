package com.yingenus.feature_showcase.domain.usecase

import com.yingenus.feature_showcase.domain.dto.Category
import com.yingenus.feature_showcase.domain.repository.CategoryRepository
import javax.inject.Inject

internal class GetCategoryUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoryRepository
): GetCategoryUseCase {
    override suspend fun invoke(): List<Category> {
        return categoryRepository.getCategories()
    }
}