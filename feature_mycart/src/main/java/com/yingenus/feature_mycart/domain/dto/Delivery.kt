package com.yingenus.feature_mycart.domain.dto

sealed class Delivery{
    object Free: Delivery()
    class Prise( val prise : Int): Delivery()
}
