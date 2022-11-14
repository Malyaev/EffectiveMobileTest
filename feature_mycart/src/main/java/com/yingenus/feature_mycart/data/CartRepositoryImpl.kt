package com.yingenus.feature_mycart.data

import android.net.Uri
import com.yingenus.api_network.api.NetworkApi
import com.yingenus.core.Result
import com.yingenus.feature_mycart.domain.repository.CartRepository
import com.yingenus.feature_mycart.domain.dto.BasketProduct
import com.yingenus.feature_mycart.domain.dto.Cart
import com.yingenus.feature_mycart.domain.dto.Delivery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.yingenus.api_network.api.dto.Cart as ApiCart
import com.yingenus.api_network.api.dto.Basket as ApiBasket

internal class CartRepositoryImpl @Inject constructor(
    private val networkApi: NetworkApi
    ): CartRepository {

    companion object{
        private fun ApiCart.toCart() =
            Cart(
                basket = basket.map { it.toBasketItem() },
                delivery = delivery.getDelivery(),
                id = id,
                total = total
            )

        private fun ApiBasket.toBasketItem() =
            BasketProduct(
                id = id,
                images = Uri.parse(images),
                price = price,
                title = title,
                number = 1
            )

        private fun String.getDelivery(): Delivery{
            return if (this.equals("free",true)) Delivery.Free
            else Delivery.Prise(this.toInt())
        }

    }


    override suspend fun getMyCart(): Result<Cart>{
        val result = networkApi.getCartRepository().getBasket()
        return if (result is Result.Success) Result.Success(result.value.toCart())
        else result as Result<Cart>
    }
}