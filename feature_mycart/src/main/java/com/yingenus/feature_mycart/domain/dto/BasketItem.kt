package com.yingenus.feature_mycart.domain.dto

import android.net.Uri

internal data class BasketItem(
    val id: Int,
    val images: Uri,
    val price: Int,
    val title: String
)