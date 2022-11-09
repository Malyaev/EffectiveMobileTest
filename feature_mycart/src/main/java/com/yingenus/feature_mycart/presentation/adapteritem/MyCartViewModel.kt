package com.yingenus.feature_mycart.presentation.adapteritem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yingenus.feature_mycart.domain.CartRepository
import com.yingenus.feature_mycart.domain.dto.BasketItem
import com.yingenus.feature_mycart.domain.dto.Cart
import com.yingenus.feature_mycart.domain.dto.Delivery
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

internal class MyCartViewModel @Inject constructor(
    private val cartRepository: CartRepository
    ) : ViewModel() {

    class MyCartViewModelProvider @Inject constructor(
        private val myCartViewModel: Provider<MyCartViewModel>
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when(modelClass){
                MyCartViewModel::class.java -> myCartViewModel.get()
                else -> null
            } as T
        }
    }

    private val _basketItems : MutableStateFlow<List<BasketItem>> = MutableStateFlow(emptyList())
    val basketItem : StateFlow<List<BasketItem>>
        get() = _basketItems.asStateFlow()

    private val _delivery : MutableStateFlow<Delivery> = MutableStateFlow(Delivery.Prise(0))
    val delivery : StateFlow<Delivery>
        get() = _delivery.asStateFlow()

    private val _total : MutableStateFlow<Int> = MutableStateFlow(0)
    val total : StateFlow<Int>
        get() = _total.asStateFlow()

    private val _error : MutableStateFlow<String?> = MutableStateFlow(null)
    val error : StateFlow<String?>
        get() = _error.asStateFlow()

    fun update(){
        viewModelScope.launch {
            cartRepository.getMyCart().onEach {
                when(it){
                    is com.yingenus.core.Result.Success -> updateCart(it.value)
                    is com.yingenus.core.Result.Error -> _error.emit(it.error.toString())
                    else ->{}
                }
            }.collect()
        }
    }

    private suspend fun updateCart(cart : Cart){
        _total.emit(cart.total)
        _delivery.emit(cart.delivery)
        _basketItems.emit(cart.basket)
    }

    fun deleteBasketItem(basketItem: BasketItem){

    }

    fun addItemFromBasketItem(basketItem: BasketItem, number: Int){

    }

    fun deleteItemFromBasketItem(basketItem: BasketItem, number: Int){

    }

    fun checkOut(){

    }

}