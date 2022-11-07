package com.yingenus.api_network.data.Retrofit

import com.yingenus.api_network.api.CartRepository
import com.yingenus.api_network.api.ProductRepository
import com.yingenus.api_network.api.StoreRepository
import com.yingenus.api_network.api.dto.Cart
import com.yingenus.api_network.api.dto.HomeStore
import com.yingenus.api_network.api.dto.Product
import com.yingenus.api_network.api.dto.Showcase
import com.yingenus.api_network.di.MockyRetrofit
import com.yingenus.core.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import javax.inject.Inject

internal class RetrofitRepository @Inject constructor(@MockyRetrofit val retrofit: Retrofit):
    CartRepository, ProductRepository, StoreRepository
{
    private val mockyIoService : MockyIoService by lazy {
        retrofit.create(MockyIoService::class.java) as MockyIoService
    }

    override fun getBasket(): Flow<Result<Cart>> {
        return mockyIoService
            .getCart()
            .map { it?.let { Result.Success(it) }?: Result.Empty() }
            .catch { emit(Result.Error(it)) }
    }

    override fun getProduct(id: Int): Flow<Result<Product>> {
        return mockyIoService
            .getProduct()
            .map { it?.let { Result.Success(it) }?: Result.Empty() }
            .catch { emit(Result.Error(it)) }
    }

    override fun getShowcase(): Flow<Result<Showcase>> {
        return mockyIoService
            .getShowcase()
            .map { it?.let { Result.Success(it) }?: Result.Empty() }
            .catch { emit(Result.Error(it)) }
    }
}