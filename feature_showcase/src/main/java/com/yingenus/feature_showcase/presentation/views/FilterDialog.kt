package com.yingenus.feature_showcase.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ViewSwitcher.ViewFactory
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yingenus.core.textutils.convertPrise
import com.yingenus.feature_showcase.R
import com.yingenus.feature_showcase.domain.dto.FilterOption
import com.yingenus.feature_showcase.presentation.viewmodels.ComponentViewModel
import com.yingenus.feature_showcase.presentation.viewmodels.FilterViewModel
import com.yingenus.feature_showcase.presentation.viewmodels.ShowCaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class FilterDialog : BottomSheetDialogFragment(R.layout.bottom_dialog) {

    @Inject
    lateinit var filterViewModelFactory: FilterViewModel.FilterViewModelFactory
    @Inject
    lateinit var showCaseViewModelFactory: ShowCaseViewModel.ShowCaseViewModelFactory
    private val filterViewModel by viewModels<FilterViewModel> {
        componentViewModel.getFeatureComponent().injectFiltersDialog(this)
        filterViewModelFactory
    }
    private val componentViewModel: ComponentViewModel by navGraphViewModels(R.id.main_showcase)
    private val showCaseViewModel : ShowCaseViewModel by navGraphViewModels<ShowCaseViewModel>(R.id.main_showcase) {
        if (!::showCaseViewModelFactory.isInitialized) componentViewModel.getFeatureComponent().injectFiltersDialog(this)
        showCaseViewModelFactory
    }

    private var filterBrandHelper : AutoCompleteTextViewHelper<FilterOption.Brand>? = null
    private var filterPriseHelper : AutoCompleteTextViewHelper<FilterOption.Prise>? = null
    private var filterSizeHelper : AutoCompleteTextViewHelper<FilterOption.Size>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  super.onCreateView(inflater, container, savedInstanceState)!!

        filterBrandHelper = AutoCompleteTextViewHelper<FilterOption.Brand>(
            autoCompleteTextView = view.findViewById(R.id.brand)
        ) {
            it.name
        }
        filterBrandHelper!!.init(requireContext())
        filterPriseHelper = AutoCompleteTextViewHelper<FilterOption.Prise>(
            autoCompleteTextView = view.findViewById(R.id.prise)
        ){
            it.from.convertPrise("$")+ " - "+ it.to.convertPrise("$")
        }
        filterPriseHelper!!.init(requireContext())
        filterSizeHelper = AutoCompleteTextViewHelper<FilterOption.Size>(
            autoCompleteTextView = view.findViewById(R.id.size)
        ){
            String.format(getString(R.string.sizes),it.to,it.to)
        }
        filterSizeHelper!!.init(requireContext())

        view.findViewById<ImageButton>(R.id.close).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<Button>(R.id.done).setOnClickListener {
            applyFilters()
        }

        subscribeToViewModel()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        filterBrandHelper = null
        filterSizeHelper = null
        filterPriseHelper = null
    }

    private fun subscribeToViewModel(){
        lifecycleScope.launchWhenStarted {
            filterViewModel.brands
                .onEach {
                    filterBrandHelper!!.setItems(it)
                }.collect()
        }
        lifecycleScope.launchWhenStarted {
            filterViewModel.prises
                .onEach {
                    filterPriseHelper!!.setItems(it)
                }.collect()
        }
        lifecycleScope.launchWhenStarted {
            filterViewModel.sizes
                .onEach {
                    filterSizeHelper!!.setItems(it)
                }.collect()
        }
    }

    private fun applyFilters(){
        val brand = filterBrandHelper!!.getSelectedItem()
        val prise = filterPriseHelper!!.getSelectedItem()
        val size = filterSizeHelper!!.getSelectedItem()
        showCaseViewModel.setFilter(brand,prise,size)
        findNavController().popBackStack()
    }

}