package com.yingenus.feature_showcase.presentation.views

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.yingenus.feature_showcase.R
import com.yingenus.feature_showcase.domain.dto.Location
import com.yingenus.feature_showcase.presentation.adapterItem.*
import com.yingenus.feature_showcase.presentation.adapterItem.BestSeller
import com.yingenus.feature_showcase.presentation.adapterItem.HotSales
import com.yingenus.feature_showcase.presentation.adapterItem.HotSalesItem
import com.yingenus.feature_showcase.presentation.adapterItem.ShopItem
import com.yingenus.feature_showcase.presentation.adapterdelegate.*
import com.yingenus.feature_showcase.presentation.adapterdelegate.getBestSalterAdapterDelegate
import com.yingenus.feature_showcase.presentation.adapterdelegate.getCategoryAdapterDelegate
import com.yingenus.feature_showcase.presentation.adapterdelegate.getHeaderAdapterDelegate
import com.yingenus.feature_showcase.presentation.adapterdelegate.getHotSalesAdapterDelegate
import com.yingenus.feature_showcase.presentation.adapterdelegate.getHotSalesContainerAdapterDelegate
import com.yingenus.feature_showcase.presentation.viewmodels.ComponentViewModel
import com.yingenus.feature_showcase.presentation.viewmodels.ShowCaseViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class ShowcaseFragment : Fragment(R.layout.shop_layout) {

    private var locationView : AutoCompleteTextView? = null
    private var categoryRecycler : RecyclerView? = null
    private var storeRecycler : RecyclerView? = null

    private var locationHelper: AutoCompleteTextViewHelper<Location>? = null

    private val hotSalesHeader = Header(getString(R.string.hot_sales))
    private val bestSellerHeader = Header(getString(R.string.best_seller))

    @Inject
    lateinit var showCaseViewModelFactory: ShowCaseViewModel.ShowCaseViewModelFactory

    private val componentViewModel : ComponentViewModel by navGraphViewModels(R.id.main_showcase)
    private val showCaseViewModel : ShowCaseViewModel by navGraphViewModels<ShowCaseViewModel>(R.id.main_showcase) {
        componentViewModel.getFeatureComponent().injectShowFragment(this)
        showCaseViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  super.onCreateView(inflater, container, savedInstanceState)!!

        locationView = view.findViewById(R.id.location)
        categoryRecycler = view.findViewById(R.id.category_recycler)
        storeRecycler = view.findViewById(R.id.recycler)

        view.findViewById<Button>(R.id.qrButton).setOnClickListener { scanQr() }
        view.findViewById<EditText>(R.id.search_view).addTextChangedListener {
            showCaseViewModel.searchQuery(it.toString())
        }
        view.findViewById<Toolbar>(R.id.top_toolbar).setOnMenuItemClickListener {
            if (it.itemId == R.id.filter){
                openFilters()
                true
            } else false
        }

        locationHelper = AutoCompleteTextViewHelper<Location>(locationView!!){
                it.name
            }
            .also {
                it.init(requireContext())
                it.onItemSelected {
                    showCaseViewModel.setLocation(it)
                }
            }

        val hotSalesAdapter = ListDelegationAdapter<List<HotSalesItem>>(
            getHotSalesAdapterDelegate {
                openHotSale(it)
            }
        )

        val storeAdapter = ListDelegationAdapter<List<ShopItem>>(
            getHeaderAdapterDelegate {
                when(it){
                    hotSalesHeader -> viewAllHotSale()
                    bestSellerHeader -> viewAllBestSeller()
                }
            },
            getBestSalterAdapterDelegate({
                openBestSeller(it)
            },{ bestseller, licked ->
                showCaseViewModel.likeBestSeller(bestseller, licked)
            }),
            getHotSalesContainerAdapterDelegate(hotSalesAdapter)
        )

        storeRecycler!!.adapter = StoreAdapter({
            when(it){
                hotSalesHeader -> viewAllHotSale()
                bestSellerHeader -> viewAllBestSeller()
            }
        },{
            openBestSeller(it)
        },{ bestseller, licked ->
            showCaseViewModel.likeBestSeller(bestseller, licked)
        },hotSalesAdapter)
        storeRecycler!!.layoutManager = GridLayoutManager(requireContext(),2).apply {
            spanSizeLookup = StoreAdapter.HotSalesSizeLookup(storeRecycler!!)
        }

        val categoryAdapter = ListDelegationAdapter<List<CategoryItem>>(
            getCategoryAdapterDelegate {
                showCaseViewModel.categorySelected(it)
            }
        )

        categoryRecycler!!.adapter = categoryAdapter

        return view
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        locationHelper = null
        locationView = null
        categoryRecycler = null
        storeRecycler = null
    }

    private fun subscribeToViewModel(){
        lifecycleScope.launchWhenStarted {
            showCaseViewModel.bestSellers.combine(showCaseViewModel.hotSales){ best, hot ->
                val shopItemList = mutableListOf<ShopItem>()
                if (best.isNotEmpty()){
                    shopItemList.add(hotSalesHeader)
                    shopItemList.add( HotSalesContainer(hot))
                }
                if (best.isNotEmpty()){
                    shopItemList.add(bestSellerHeader)
                    shopItemList.addAll(best)
                }
                shopItemList.toList()
            }
                .onEach { items ->
                    updateShopItems(items)
                }.collect()
        }
        lifecycleScope.launchWhenStarted {
            showCaseViewModel
                .categories
                .onEach {
                    updateCategoryItems(it)
                }.collect()
                }
        lifecycleScope.launchWhenStarted {
            showCaseViewModel.locations
                .onEach {
                    locationHelper!!.setItems(it)
                }.collect()
        }
        lifecycleScope.launchWhenStarted {
            showCaseViewModel.selectedLocation
                .onEach {
                    it?.let {  locationHelper!!.selectItem(it)}
                }.collect()
        }
        lifecycleScope.launchWhenStarted {
            showCaseViewModel.error
                .onEach {
                    //TODO(show error dialog)
                }.collect()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateShopItems(items : List<ShopItem>){
        (storeRecycler!!.adapter as  ListDelegationAdapter<List<ShopItem>>)
            .let {
                it.items = items
                it.notifyDataSetChanged()
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateCategoryItems(categorys: List<Category>){
        (categoryRecycler!!.adapter as ListDelegationAdapter<List<CategoryItem>>)
            .let {
                it.items = categorys
                it.notifyDataSetChanged()
            }
    }

    private fun openFilters(){
        findNavController().navigate(R.id.showcase_open_filters)
    }

    private fun openBestSeller(bestSeller: BestSeller){

    }

    private fun viewAllBestSeller(){
        //TODO
    }

    private fun openHotSale(hotSales: HotSales){

    }

    private fun viewAllHotSale(){
        //TODO
    }

    private fun scanQr(){

    }

}