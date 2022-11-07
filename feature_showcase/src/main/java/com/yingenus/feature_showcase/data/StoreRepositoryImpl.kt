package com.yingenus.feature_showcase.data

import android.net.Uri
import com.yingenus.api_network.api.NetworkApi
import com.yingenus.api_network.api.dto.BestSeller
import com.yingenus.api_network.api.dto.HomeStore
import com.yingenus.api_network.api.dto.Showcase
import com.yingenus.feature_showcase.domain.StoreRepository
import com.yingenus.feature_showcase.domain.dto.BestSellerProduct
import com.yingenus.feature_showcase.domain.dto.HomeShowcase
import com.yingenus.feature_showcase.domain.dto.HomeStoreProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.yingenus.api_network.api.StoreRepository as ApiStoreRepository
import com.yingenus.core.Result
import javax.inject.Inject

internal class StoreRepositoryImpl @Inject constructor(val networkApi: NetworkApi): StoreRepository {

    companion object{

        private fun Showcase.toHomeShowcase() =
            HomeShowcase(
                best_seller.map { it.toBestSellerProduct() },
                home_store.map { it.toHomeStoreProduct() }
            )

        private fun BestSeller.toBestSellerProduct() =
            BestSellerProduct(
                id = id,
                discountPrise = discount_price,
                isFavorites = is_favorites,
                picture = Uri.parse(picture),
                priceWithoutDiscount = price_without_discount,
                title = title
            )

        private fun HomeStore.toHomeStoreProduct() =
            HomeStoreProduct(
                id = id,
                isBuy = is_buy,
                isNew = is_new,
                picture = Uri.parse(picture),
                subtitle = subtitle,
                title = title
            )

    }

    private val apiStoreRepository : ApiStoreRepository by lazy {
        networkApi.getStoreRepository()
    }

    override fun getHomeShowcase(): Flow<Result<HomeShowcase>> {
        return apiStoreRepository.getShowcase().map {
            if (it is Result.Success) Result.Success(it.value.toHomeShowcase())
            else it as Result<HomeShowcase>
        }
    }
}