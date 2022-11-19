package com.yingenus.feature_product_details.presentation.viewmodel

import android.graphics.Color
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yingenus.feature_product_details.di.ProductID
import com.yingenus.feature_product_details.domain.repository.ProductRepository
import com.yingenus.feature_product_details.domain.usecase.GetProductDetailedUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

internal class ProductViewModelFactory  @Inject constructor(
    private val  productViewModelFactory : Provider<ProductViewModel>
    ): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass){
            ProductViewModel::class.java -> productViewModelFactory.get()
            else -> null
        } as T
    }
}

internal class ProductViewModel @Inject constructor(
    private val getProductDetailedUseCase: GetProductDetailedUseCase
): ViewModel() {

    private val _cpu : MutableStateFlow<String> = MutableStateFlow("")
    val cpu : StateFlow<String>
        get() = _cpu.asStateFlow()

    private val _camera : MutableStateFlow<String> = MutableStateFlow("")
    val camera : StateFlow<String>
        get() = _camera.asStateFlow()

    private val _sd : MutableStateFlow<String> = MutableStateFlow("")
    val sd : StateFlow<String>
        get() = _sd.asStateFlow()

    private val _ssd : MutableStateFlow<String> = MutableStateFlow("")
    val ssd : StateFlow<String>
        get() = _ssd.asStateFlow()

    private val _title : MutableStateFlow<String> = MutableStateFlow("")
    val title : StateFlow<String>
        get() = _title.asStateFlow()

    private val _rating : MutableStateFlow<Int> = MutableStateFlow(0)
    val rating : StateFlow<Int>
        get() = _rating.asStateFlow()

    private val _prise : MutableStateFlow<Int> = MutableStateFlow(0)
    val prise : StateFlow<Int>
        get() = _prise.asStateFlow()

    private val _isFavorites : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFavorites : StateFlow<Boolean>
        get() = _isFavorites.asStateFlow()

    private val _capacity : MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val capacity : StateFlow<List<String>>
        get() = _capacity.asStateFlow()

    private val _productPhotos : MutableStateFlow<List<Uri>> = MutableStateFlow(emptyList())
    val productPhotos : StateFlow<List<Uri>>
        get() = _productPhotos.asStateFlow()

    private val _colors : MutableStateFlow<List<Int>> = MutableStateFlow(emptyList())
    val colors : StateFlow<List<Int>>
        get() = _colors.asStateFlow()

    private val _error : Channel<String?> = Channel()
    val error : Flow<String?>
        get() = _error.receiveAsFlow()

    fun update(){
        viewModelScope.launch {
            val result = getProductDetailedUseCase()
            when (result) {
                is com.yingenus.core.Result.Success -> {
                    _cpu.emit(result.value.cpu)
                    _colors.emit(result.value.color.map { Color.parseColor(it) })
                    _camera.emit(result.value.camera)
                    _capacity.emit(result.value.capacity)
                    _isFavorites.emit(result.value.isFavorites)
                    _prise.emit(result.value.price)
                    _productPhotos.emit(result.value.images)
                    _rating.emit(result.value.rating.toInt())
                    _sd.emit(result.value.sd)
                    _ssd.emit(result.value.ssd)
                    _title.emit(result.value.title)
                }
                is com.yingenus.core.Result.Error -> {
                    _error.send(result.error.message)
                }
                else -> {}
            }
        }
    }

    fun setSelectedColor(int : Int){

    }

    fun setSelectedStorageSize( size : String){

    }

    fun addToChart(){

    }

    fun likeProduct( isFavorites: Boolean){

    }
}

