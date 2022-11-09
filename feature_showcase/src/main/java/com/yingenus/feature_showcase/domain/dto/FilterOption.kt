package com.yingenus.feature_showcase.domain.dto

internal sealed class FilterOption{
    data class Brand(val name: String): FilterOption()
    data class Prise(val from: Int, val to : Int): FilterOption()
    data class Size(val from : Float, val to : Float): FilterOption()
}
