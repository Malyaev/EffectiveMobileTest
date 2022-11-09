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

    override fun getProductDetailed( id : Int): Flow<Result<ProductDetailed>> {
        return  networkApi.getProductRepository().getProduct(id).map {
            if (it is Result.Success) Result.Success(it.value.toProductDetailed())
            else it as Result<ProductDetailed>
        }
    }
}