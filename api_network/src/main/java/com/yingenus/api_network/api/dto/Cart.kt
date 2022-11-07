package com.yingenus.api_network.api.dto

data class Cart(
    val basket: List<Basket>,
    val delivery: String,
    val id: String,
    val total: Int
)