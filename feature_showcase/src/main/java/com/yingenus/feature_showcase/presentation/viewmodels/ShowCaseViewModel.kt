package com.yingenus.feature_showcase.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yingenus.core.Result
import com.yingenus.feature_showcase.domain.repository.CategoryRepository
import com.yingenus.feature_showcase.domain.repository.LocationRepository
import com.yingenus.feature_showcase.domain.repository.StoreRepository
import com.yingenus.feature_showcase.domain.dto.FilterOption
import com.yingenus.feature_showcase.domain.dto.HomeShowcase
import com.yingenus.feature_showcase.domain.dto.Location
import com.yingenus.feature_showcase.domain.usecase.*
import com.yingenus.feature_showcase.domain.usecase.GetCategoryUseCase
import com.yingenus.feature_showcase.domain.usecase.GetHomeShowcaseUseCase
import com.yingenus.feature_showcase.domain.usecase.GetLocationsUseCase
import com.yingenus.feature_showcase.domain.usecase.GetSelectedLocationUseCase
import com.yingenus.feature_showcase.presentation.adapterItem.BestSeller
import com.yingenus.feature_showcase.presentation.adapterItem.Category
import com.yingenus.feature_showcase.presentation.adapterItem.HotSales
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

internal class ShowCaseViewModel  @Inject constructor(
    private val getHomeShowcaseUseCase: GetHomeShowcaseUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getSelectedLocationUseCase: GetSelectedLocationUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val likeBestsellerUseCase: LikeBestsellerUseCase
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

    private val _showFilterDialog: Channel<com.yingenus.feature_showcase.domain.dto.Category> = Channel()
    val showFilterDialog : Flow<com.yingenus.feature_showcase.domain.dto.Category>
        get() = _showFilterDialog.receiveAsFlow()

    private val _categoriesStateFlow: MutableStateFlow<List<Category>>
        = MutableStateFlow( emptyList() )
    val categories : StateFlow<List<Category>>
        get() = _categoriesStateFlow.asStateFlow()
    private val selectedCategory : StateFlow<Category?> =
        categories.map {
            it.find { it.isSelected }
        }.stateIn(viewModelScope, SharingStarted.Lazily, null)

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
        flow { emit(getLocationsUseCase()) }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun updateViewModel(){
        viewModelScope.launch {
            launch {
                val category = getCategoryUseCase()
                _categoriesStateFlow.emit(
                    category.mapIndexed{ i, c ->
                        Category(c, i == 0)
                    }
                )
            }
            launch {
                val locations = getSelectedLocationUseCase()
                _selectedLocation.emit(locations)
            }
        }
        selectedCategory.map {
                it?.let {  getHomeShowcaseUseCase(it.category)}?:
                com.yingenus.core.Result.Empty<HomeShowcase>()
            }
            .onEach {
                when(it){
                    is com.yingenus.core.Result.Success -> {
                        _hotSalesStateFlow.emit(it.value.homeStoreProducts
                            .map { HotSales(it) })
                        _bestSellersStateFlow.emit(it.value.bestSellers
                            .map { BestSeller(it) })
                    }
                    is com.yingenus.core.Result.Error -> {
                        _error.send(it.toString())
                    }
                    else -> Unit
                }
            }
            .launchIn(viewModelScope)
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

    fun showFilterDialog(){
        viewModelScope.launch {
            selectedCategory.value?.let { _showFilterDialog.send(it.category) }
        }
    }

    fun likeBestSeller( bestSeller: BestSeller, isLicked : Boolean){
        viewModelScope.launch {
            val result = likeBestsellerUseCase(bestSeller.bestSellerProduct,isLicked)

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