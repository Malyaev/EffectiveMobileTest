package com.yingenus.feature_product_details.data

import android.net.Uri
import com.yingenus.api_network.api.NetworkApi
import com.yingenus.api_network.api.dto.Product
import com.yingenus.core.Result
import com.yingenus.feature_product_details.domain.ProductRepository
import com.yingenus.feature_product_details.domain.dto.ProductDetailed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ProductRepositoryImpl @Inject constructor(
    private val networkApi: NetworkApi
): ProductRepository {

    companion object{

        fun Product.toProductDetailed() =
            ProductDetailed(
                cpu = CPU,
                camera = camera,
                capacity = capacity,
                color = color,
                id = id,
                images = images.map { Uri.parse(it) },
                isFavorites = isFavorites,
                price = price,
                rating = rating,
                sd = sd,
                ssd = ssd,
                title = title
            )
    }

    override suspend fun getProductDetailed( id : Int): Result<ProductDetailed> {
        val result = networkApi.getProductRepository().getProduct(id)
        return if (result is Result.Success) Result.Success(result.value.toProductDetailed())
        else result as Result<ProductDetailed>
    }
}