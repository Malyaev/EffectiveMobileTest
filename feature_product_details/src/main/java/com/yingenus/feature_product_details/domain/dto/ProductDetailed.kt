package com.yingenus.feature_product_details.domain.dto

import android.net.Uri

internal data class ProductDetailed(
    val cpu: String,
    val camera: String,
    val capacity: List<String>,
    val color: List<String>,
    val id: String,
    val images: List<Uri>,
    val isFavorites: Boolean,
    val price: Int,
    val rating: Double,
    val sd: String,
    val ssd: String,
    val title: String
) {
}