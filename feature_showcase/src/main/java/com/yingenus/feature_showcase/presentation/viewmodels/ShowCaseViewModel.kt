package com.yingenus.feature_showcase.presentation.viewmodels

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

    private val _error : MutableStateFlow<String?> = MutableStateFlow(null)
    val error : StateFlow<String?>
        get() = _error.asStateFlow()

    private var categoryList: List<Category> = emptyList()
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
        locationRepository.getAllLocations().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun updateViewModel(){
        storeRepository
            .getHomeShowcase()
            .onEach {
                when(it){
                    is com.yingenus.core.Result.Success -> {
                        _hotSalesStateFlow.emit(it.value.homeStoreProducts
                            .map { HotSales(it) })
                        _bestSellersStateFlow.emit(it.value.bestSellers
                            .map { BestSeller(it) })
                    }
                    is com.yingenus.core.Result.Error -> {
                        _error.emit(it.toString())
                    }
                    else -> Unit
                }
            }.launchIn(viewModelScope)
        locationRepository
            .getSelectedLocation()
            .onEach {
                _selectedLocation.emit(it)
            }
            .launchIn(viewModelScope)
        categoryRepository
            .getCategories()
            .onEach {
                _categoriesStateFlow.emit(
                    it.mapIndexed{ i, c ->
                        Category(c, i == 0)
                    }
                )
            }.launchIn(viewModelScope)
    }
    fun categorySelected( category: Category){
        viewModelScope.launch {
            val updatedCategories = categoryList.map {
                if (it == category) Category(it.category,isSelected = true)
                else if (it.isSelected) Category(it.category, false)
                else category
            }
            categoryList = updatedCategories
            _categoriesStateFlow.emit(categoryList)
        }
    }

    fun likeBestSeller( bestSeller: BestSeller, isLicked : Boolean){
        //TODO()
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