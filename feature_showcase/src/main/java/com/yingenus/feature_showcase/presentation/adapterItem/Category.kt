package com.yingenus.feature_showcase.presentation.adapterItem

internal data class Category(
    val category: com.yingenus.feature_showcase.domain.dto.Category,
    val isSelected: Boolean
): CategoryItem
