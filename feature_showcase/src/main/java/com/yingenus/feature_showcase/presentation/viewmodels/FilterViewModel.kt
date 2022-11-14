package com.yingenus.feature_showcase.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yingenus.feature_showcase.domain.FilterOptionRepository
import com.yingenus.feature_showcase.domain.LocationRepository
import com.yingenus.feature_showcase.domain.dto.FilterOption
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Provider

internal class FilterViewModel @Inject constructor(
    private val filterOptionRepository: FilterOptionRepository
): ViewModel() {

    class FilterViewModelFactory @Inject constructor(
        private val filterViewModelProvider: Provider<FilterViewModel>
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when(modelClass){
                FilterViewModel::class.java -> filterViewModelProvider.get()
                else -> null
            } as T
        }
    }

    val brands : StateFlow<List<FilterOption.Brand>> by lazy {
        flow<List<FilterOption.Brand>> {
            emit(filterOptionRepository.getBrands())
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }
    val prises : StateFlow<List<FilterOption.Prise>> by lazy {
        flow { emit(filterOptionRepository.getPrises()) }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }
    val sizes : StateFlow<List<FilterOption.Size>> by lazy{
        flow{emit(filterOptionRepository.getSizes())}
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

}