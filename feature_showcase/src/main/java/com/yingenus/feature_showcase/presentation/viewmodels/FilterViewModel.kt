package com.yingenus.feature_showcase.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yingenus.feature_showcase.domain.dto.Category
import com.yingenus.feature_showcase.domain.repository.FilterOptionRepository
import com.yingenus.feature_showcase.domain.dto.FilterOption
import com.yingenus.feature_showcase.domain.dto.FilterOptions
import com.yingenus.feature_showcase.domain.usecase.GetFilterOptionsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

internal class FilterViewModel constructor(
    private val getFilterOptionsUseCase: GetFilterOptionsUseCase,
    private val category: Category
): ViewModel() {

    private val options : StateFlow<FilterOptions?> =
        flow {
            emit(getFilterOptionsUseCase.invoke(category))
        }.stateIn(viewModelScope, SharingStarted.Lazily,null)

    val brands : StateFlow<List<FilterOption.Brand>> by lazy {
        options.map { it?.brands?: emptyList() }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }
    val prises : StateFlow<List<FilterOption.Prise>> by lazy {
        options.map { it?.prises?: emptyList() }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }
    val sizes : StateFlow<List<FilterOption.Size>> by lazy{
        options.map { it?.sizes?: emptyList() }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

}

internal class FilterViewModelFactory @AssistedInject constructor(
    private val getFilterOptionsUseCase: GetFilterOptionsUseCase,
    @Assisted private val category: Category
): ViewModelProvider.Factory{

    @AssistedFactory
    interface Factory{
        fun create(category: Category): FilterViewModelFactory
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass){
            FilterViewModel::class.java -> FilterViewModel(getFilterOptionsUseCase, category)
            else -> null
        } as T
    }
}