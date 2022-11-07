package com.yingenus.feature_showcase.domain.dto

import android.net.Uri

internal data class HomeStoreProduct(
    val id: Int,
    val isBuy: Boolean,
    val isNew: Boolean,
    val picture: Uri,
    val subtitle: String,
    val title: String
)
