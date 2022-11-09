package com.yingenus.feature_showcase.domain.dto

import android.net.Uri

internal data class BestSellerProduct(
    val id: Int,
    val discountPrise: Int,
    val isFavorites: Boolean,
    val picture: Uri,
    val priceWithoutDiscount: Int,
    val title: String
)
