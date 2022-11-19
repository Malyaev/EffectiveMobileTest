package com.yingenus.feature_mycart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yingenus.feature_mycart.domain.repository.CartRepository
import com.yingenus.feature_mycart.domain.dto.BasketProduct
import com.yingenus.feature_mycart.domain.dto.Cart
import com.yingenus.feature_mycart.domain.dto.Delivery
import com.yingenus.feature_mycart.domain.usecase.GetMyCartUsecase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

internal class MyCartViewModel @Inject constructor(
    private val getMyCartUsecase: GetMyCartUsecase
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

    private val _basketItems : MutableStateFlow<List<BasketProduct>> = MutableStateFlow(emptyList())
    val basketItem : StateFlow<List<BasketProduct>>
        get() = _basketItems.asStateFlow()

    private val _delivery : MutableStateFlow<Delivery> = MutableStateFlow(Delivery.Prise(0))
    val delivery : StateFlow<Delivery>
        get() = _delivery.asStateFlow()

    private val _total : MutableStateFlow<Int> = MutableStateFlow(0)
    val total : StateFlow<Int>
        get() = _total.asStateFlow()

    private val _error : Channel<String?> = Channel()
    val error : Flow<String?>
        get() = _error.receiveAsFlow()

    fun update(){
        viewModelScope.launch {
            val result = getMyCartUsecase.invoke()
            when(result){
                is com.yingenus.core.Result.Success -> updateCart(result.value)
                is com.yingenus.core.Result.Error -> _error.send(result.error.toString())
                else ->{}
            }
        }
    }

    private suspend fun updateCart(cart : Cart){
        _total.emit(cart.total)
        _delivery.emit(cart.delivery)
        _basketItems.emit(cart.basket)
    }

    fun deleteBasketItem(basketItem: BasketProduct){

    }

    fun addItemFromBasketItem(basketItem: BasketProduct, number: Int){

    }

    fun deleteItemFromBasketItem(basketItem: BasketProduct, number: Int){

    }

    fun checkOut(){

    }

}