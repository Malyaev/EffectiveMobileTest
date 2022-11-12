package com.yingenus.feature_showcase.presentation.views

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.yingenus.api_network.api.ImageLoader
import com.yingenus.core.colors.resolveColorAttr
import com.yingenus.feature_showcase.R
import com.yingenus.feature_showcase.domain.dto.Location
import com.yingenus.feature_showcase.presentation.adapterItem.*
import com.yingenus.feature_showcase.presentation.adapterItem.BestSeller
import com.yingenus.feature_showcase.presentation.adapterItem.HotSales
import com.yingenus.feature_showcase.presentation.adapterItem.HotSalesItem
import com.yingenus.feature_showcase.presentation.adapterItem.ShopItem
import com.yingenus.feature_showcase.presentation.adapterdelegate.*
import com.yingenus.feature_showcase.presentation.adapterdelegate.getCategoryAdapterDelegate
import com.yingenus.feature_showcase.presentation.adapterdelegate.getHotSalesAdapterDelegate
import com.yingenus.feature_showcase.presentation.viewmodels.ComponentViewModel
import com.yingenus.feature_showcase.presentation.viewmodels.ShowCaseViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class ShowcaseFragment : Fragment(R.layout.shop_layout) {

    @Inject
    lateinit var imageLoader: ImageLoader

    private var locationView : AutoCompleteTextView? = null
    private var categoryRecycler : RecyclerView? = null
    private var storeRecycler : RecyclerView? = null

    private var locationHelper: AutoCompleteTextViewHelper<Location>? = null

    private val hotSalesHeader by lazy { Header(getString(R.string.hot_sales)) }
    private val bestSellerHeader by lazy { Header(getString(R.string.best_seller))}

    @Inject
    lateinit var showCaseViewModelFactory: ShowCaseViewModel.ShowCaseViewModelFactory

    private val componentViewModel : ComponentViewModel by navGraphViewModels(R.id.main_showcase)
    private val showCaseViewModel : ShowCaseViewModel by navGraphViewModels<ShowCaseViewModel>(R.id.main_showcase) {
        showCaseViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        componentViewModel.getFeatureComponent(requireActivity()).injectShowFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  super.onCreateView(inflater, container, savedInstanceState)!!

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.top_toolbar)

        locationView = toolbar.findViewById(R.id.location_select)
        categoryRecycler = view.findViewById(R.id.category_recycler)
        storeRecycler = view.findViewById(R.id.recycler)

        view.findViewById<ImageButton>(R.id.qrButton).setOnClickListener { scanQr() }
        view.findViewById<EditText>(R.id.search_view).addTextChangedListener {
            showCaseViewModel.searchQuery(it.toString())
        }
        toolbar.setOnMenuItemClickListener {
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
            getHotSalesAdapterDelegate(imageLoader) {
                openHotSale(it)
            }
        )

        storeRecycler!!.adapter = StoreAdapter(imageLoader,{
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
        storeRecycler!!.addItemDecoration(StoreAdapter.HotSalesBoundDecorator())

        val categoryAdapter = ListDelegationAdapter<List<CategoryItem>>(
            getCategoryAdapterDelegate(imageLoader) {
                showCaseViewModel.categorySelected(it)
            }
        )

        categoryRecycler!!.adapter = categoryAdapter

        subscribeToViewModel()

        return view
    }

    override fun onStart() {
        super.onStart()
        requireActivity().window.navigationBarColor = requireContext().resolveColorAttr(androidx.appcompat.R.attr.background)
        showCaseViewModel.updateViewModel()
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
        (categoryRecycler!!.adapter as ListDelegationAdapter<*>)
            .let {
                it.items = categorys
                it.notifyDataSetChanged()
            }
    }

    private fun openFilters(){
        findNavController().navigate(R.id.showcase_open_filters)
    }

    private fun openBestSeller(bestSeller: BestSeller){
        val uri = Uri.parse("App://feature_product/product/${bestSeller.bestSellerProduct.id}")
        findNavController().navigate(uri)
    }

    private fun viewAllBestSeller(){
        //TODO
    }

    private fun openHotSale(hotSales: HotSales){
        val uri = Uri.parse("App://feature_product/product/${hotSales.hotSalesProduct.id}")
        findNavController().navigate(uri)
    }

    private fun viewAllHotSale(){
        //TODO
    }

    private fun scanQr(){

    }

}