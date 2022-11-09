package com.yingenus.feature_mycart.domain.dto

internal data class Cart(
    val basket: List<BasketProduct>,
    val delivery: Delivery,
    val id: String,
    val total: Int
)