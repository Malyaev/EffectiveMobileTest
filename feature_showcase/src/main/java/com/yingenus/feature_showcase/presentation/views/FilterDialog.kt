package com.yingenus.feature_showcase.presentation.views

import android.app.Application
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
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yingenus.core.sizeutils.dp2px
import com.yingenus.core.textutils.convertPrise
import com.yingenus.core.viewmodels.ComponentViewModel
import com.yingenus.feature_showcase.R
import com.yingenus.feature_showcase.di.FeatureComponent
import com.yingenus.feature_showcase.domain.dto.Category
import com.yingenus.feature_showcase.domain.dto.FilterOption
import com.yingenus.feature_showcase.presentation.viewmodels.FilterViewModel
import com.yingenus.feature_showcase.presentation.viewmodels.FilterViewModelFactory
import com.yingenus.feature_showcase.presentation.viewmodels.ShowCaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class FilterDialog : BottomSheetDialogFragment(R.layout.bottom_dialog) {

    @Inject
    lateinit var filterViewModelFactory: FilterViewModelFactory.Factory
    @Inject
    lateinit var showCaseViewModelFactory: ShowCaseViewModel.ShowCaseViewModelFactory
    private val filterViewModel by viewModels<FilterViewModel> {
        componentViewModel.getComponent(requireContext().applicationContext as Application).injectFiltersDialog(this)
        filterViewModelFactory.create(category!!)
    }

    private var category: Category?=null

    private val componentViewModel: ComponentViewModel<FeatureComponent> by navGraphViewModels(R.id.main_showcase)
    private val showCaseViewModel : ShowCaseViewModel by navGraphViewModels(R.id.main_showcase)

    private var filterBrandHelper : AutoCompleteTextViewHelper<FilterOption.Brand>? = null
    private var filterPriseHelper : AutoCompleteTextViewHelper<FilterOption.Prise>? = null
    private var filterSizeHelper : AutoCompleteTextViewHelper<FilterOption.Size>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = requireArguments().getParcelable<Category>("category")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  super.onCreateView(inflater, container, savedInstanceState)!!

        filterBrandHelper = AutoCompleteTextViewHelper<FilterOption.Brand>(
            autoCompleteTextView = view.findViewById<AutoCompleteTextView?>(R.id.brand)
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
            String.format(getString(R.string.sizes),it.from,it.to)
        }
        filterSizeHelper!!.init(requireContext())

        view.findViewById<ImageButton>(R.id.close).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<Button>(R.id.done).setOnClickListener {
            applyFilters()
        }

        setPeekHeight(view)

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

    private fun setPeekHeight( view: View){
        val heightSpec = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED)
        view.measure(widthSpec,heightSpec)
        val height = view.measuredHeight

        val  behavior = (dialog as BottomSheetDialog).behavior
        behavior.peekHeight = height
        behavior.isDraggable = false
    }

}