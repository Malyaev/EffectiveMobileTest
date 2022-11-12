package com.yingenus.feature_product_details.presentation.viewmodel

import android.graphics.Color
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yingenus.feature_product_details.domain.ProductRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Provider


internal class ProductViewModelFactory  @AssistedInject constructor(
    @Assisted private val productId : Int,
    private val  productViewModelFactory : Provider<ProductViewModel.Factory>
    ): ViewModelProvider.Factory{

    @AssistedFactory
    interface Factory{
        fun crate(productId: Int): ProductViewModelFactory
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass){
            ProductViewModel::class.java -> productViewModelFactory.get().crate(productId)
            else -> null
        } as T
    }
}

internal class ProductViewModel @AssistedInject constructor(
    @Assisted private val productId : Int,
    private val productRepository: ProductRepository
): ViewModel() {

    @AssistedFactory
    interface Factory{
        fun crate(productId: Int): ProductViewModel
    }

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

    private val _error : MutableStateFlow<String?> = MutableStateFlow(null)
    val error : StateFlow<String?>
        get() = _error.asStateFlow()

    fun update(){
        productRepository
            .getProductDetailed(productId)
            .onEach {
                when(it){
                    is com.yingenus.core.Result.Success ->{
                        _cpu.emit(it.value.cpu)
                        _colors.emit(it.value.color.map { Color.parseColor(it) })
                        _camera.emit(it.value.camera)
                        _capacity.emit(it.value.capacity)
                        _isFavorites.emit(it.value.isFavorites)
                        _prise.emit(it.value.price)
                        _productPhotos.emit(it.value.images)
                        _rating.emit(it.value.rating.toInt())
                        _sd.emit(it.value.sd)
                        _ssd.emit(it.value.ssd)
                        _title.emit(it.value.title)
                    }
                    is com.yingenus.core.Result.Error ->{
                        _error.emit(it.error.message)
                    }
                    else ->{}
                }
            }
            .launchIn(viewModelScope)
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

