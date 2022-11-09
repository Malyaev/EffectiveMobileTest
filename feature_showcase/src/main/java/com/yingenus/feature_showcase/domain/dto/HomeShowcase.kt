package com.yingenus.feature_showcase.domain.dto

internal data class HomeShowcase(
    val bestSellers: List<BestSellerProduct>,
    val homeStoreProducts: List<HotSalesProduct>
) {
}