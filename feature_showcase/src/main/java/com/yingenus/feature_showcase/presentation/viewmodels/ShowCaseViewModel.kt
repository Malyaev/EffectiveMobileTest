package com.yingenus.feature_showcase.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yingenus.feature_showcase.domain.CategoryRepository
import com.yingenus.feature_showcase.domain.LocationRepository
import com.yingenus.feature_showcase.domain.StoreRepository
import com.yingenus.feature_showcase.domain.dto.FilterOption
import com.yingenus.feature_showcase.domain.dto.Location
import com.yingenus.feature_showcase.presentation.adapterItem.BestSeller
import com.yingenus.feature_showcase.presentation.adapterItem.Category
import com.yingenus.feature_showcase.presentation.adapterItem.HotSales
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

internal class ShowCaseViewModel  @Inject constructor(
    private val storeRepository: StoreRepository,
    private val locationRepository: LocationRepository,
    private val categoryRepository: CategoryRepository
): ViewModel() {

    class ShowCaseViewModelFactory @Inject constructor(
        private val viewModelProvider: Provider<ShowCaseViewModel>
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when(modelClass){
                ShowCaseViewModel::class.java -> viewModelProvider.get()
                else -> null
            } as T
        }
    }

    private val _error : Channel<String?> = Channel()
    val error : Flow<String?>
        get() = _error.receiveAsFlow()

    private val _categoriesStateFlow: MutableStateFlow<List<Category>>
        = MutableStateFlow( emptyList() )
    val categories : StateFlow<List<Category>>
        get() = _categoriesStateFlow.asStateFlow()

    private val _hotSalesStateFlow: MutableStateFlow<List<HotSales>>
        = MutableStateFlow( emptyList() )
    val hotSales : StateFlow<List<HotSales>>
        get() = _hotSalesStateFlow.asStateFlow()

    private val _bestSellersStateFlow : MutableStateFlow<List<BestSeller>>
        = MutableStateFlow( emptyList() )
    val bestSellers : StateFlow<List<BestSeller>>
        get() = _bestSellersStateFlow.asStateFlow()

    private val _selectedLocation : MutableStateFlow<Location?> = MutableStateFlow(null)
    val selectedLocation : StateFlow<Location?>
        get() = _selectedLocation.asStateFlow()

    val locations : StateFlow<List<Location>> by lazy{
        flow { emit(locationRepository.getAllLocations()) }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun updateViewModel(){
        viewModelScope.launch {
            launch {
                val homeShowcase = storeRepository.getHomeShowcase()
                when(homeShowcase){
                    is com.yingenus.core.Result.Success -> {
                        _hotSalesStateFlow.emit(homeShowcase.value.homeStoreProducts
                            .map { HotSales(it) })
                        _bestSellersStateFlow.emit(homeShowcase.value.bestSellers
                            .map { BestSeller(it) })
                    }
                    is com.yingenus.core.Result.Error -> {
                        _error.send(homeShowcase.toString())
                    }
                    else -> Unit
                }
            }
            launch {
                val locations = locationRepository
                    .getSelectedLocation()
                _selectedLocation.emit(locations)
            }
            launch {
                val category = categoryRepository
                    .getCategories()
                _categoriesStateFlow.emit(
                    category.mapIndexed{ i, c ->
                        Category(c, i == 0)
                    }
                )
            }
        }
    }
    fun categorySelected( category: Category){
        viewModelScope.launch {
            val updatedCategories = _categoriesStateFlow.value
                .map {
                if (it == category) Category(it.category,isSelected = true)
                else if (it.isSelected) Category(it.category, false)
                else it
            }
            _categoriesStateFlow.emit(updatedCategories)
        }
    }

    fun likeBestSeller( bestSeller: BestSeller, isLicked : Boolean){
        viewModelScope.launch {
            val result = storeRepository
                .likeBestSeller(bestSeller.bestSellerProduct,isLicked)
            when(result){
                is com.yingenus.core.Result.Success ->{
                    val updated = _bestSellersStateFlow.value.map {
                        if (it.bestSellerProduct.id == result.value.id) BestSeller(result.value)
                        else it
                    }
                    _bestSellersStateFlow.emit(updated)
                }
                is com.yingenus.core.Result.Error -> {
                    _error.send(result.error.message)
                }
                else -> {}
            }
        }
    }

    fun searchQuery( query : String){
        //TODO()
    }

    fun setFilter( brand: FilterOption.Brand?, prise: FilterOption.Prise?, size: FilterOption.Size?){
        //TODO()
    }

    fun setLocation(location: Location){
        viewModelScope.launch {
            _selectedLocation.emit(location)
        }
    }

}