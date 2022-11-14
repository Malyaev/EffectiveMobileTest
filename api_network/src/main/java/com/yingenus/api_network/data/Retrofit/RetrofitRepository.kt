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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.Retrofit
import javax.inject.Inject

internal class RetrofitRepository @Inject constructor(@MockyRetrofit val retrofit: Retrofit):
    CartRepository, ProductRepository, StoreRepository
{
    private val mockyIoService : MockyIoService by lazy {
        retrofit.create(MockyIoService::class.java) as MockyIoService
    }

    override suspend fun getBasket(): Result<Cart> = withContext(Dispatchers.IO){
        try {
            val response =  mockyIoService.getCart()
            response?.let { Result.Success(it) }?: Result.Empty()
        }catch ( e : IOException) {
            Result.Error(e)
        }
    }

    override suspend fun getProduct(id: Int): Result<Product> = withContext(Dispatchers.IO){
        try {
            val response = mockyIoService.getProduct()
            response?.let { Result.Success(it) }?: Result.Empty()
        } catch (e : IOException){
            Result.Error(e)
        }
    }

    override suspend fun getShowcase(): Result<Showcase> = withContext(Dispatchers.IO){
        try {
            val response =  mockyIoService.getShowcase()
            response?.let { Result.Success(it) }?: Result.Empty()
        }catch ( e : IOException) {
            Result.Error(e)
        }
    }
}