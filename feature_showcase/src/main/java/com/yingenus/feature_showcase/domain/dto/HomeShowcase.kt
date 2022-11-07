package com.yingenus.feature_showcase.domain.dto

data class HomeShowcase(
    val bestSellers: List<BestSellerProduct>,
    val homeStoreProducts: List<HomeStoreProduct>
) {
}