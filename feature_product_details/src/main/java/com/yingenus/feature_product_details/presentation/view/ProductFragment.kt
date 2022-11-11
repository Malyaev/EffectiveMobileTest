package com.yingenus.feature_product_details.presentation.view

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabLayout
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.yingenus.api_network.api.ImageLoader
import com.yingenus.core.sizeutils.dp2px
import com.yingenus.feature_product_details.R
import com.yingenus.feature_product_details.presentation.adapterelegat.getImageAdapterDelegate
import com.yingenus.feature_product_details.presentation.adapteritem.Image
import com.yingenus.feature_product_details.presentation.adapteritem.ImageItem
import com.yingenus.feature_product_details.presentation.viewmodel.ComponentViewModel
import com.yingenus.feature_product_details.presentation.viewmodel.ProductViewModel
import com.yingenus.feature_product_details.presentation.viewmodel.ProductViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import java.lang.Integer.max
import java.lang.Math.abs
import javax.inject.Inject
import javax.inject.Provider
import kotlin.math.min

internal class ProductFragment : Fragment(R.layout.product_fragment) {

    @Inject
    lateinit var imageLoader: ImageLoader

    private val componentViewModel : ComponentViewModel by navGraphViewModels(R.id.main_product)
    @Inject
    lateinit var productViewModelFactory : Provider<ProductViewModelFactory.Factory>
    private val productViewModel: ProductViewModel by viewModels{
        val id = requireArguments().getInt("id")
        productViewModelFactory.get().crate(id)
    }

    private var imageViewPager : ViewPager2? = null
    private var detailsViewPager : ViewPager2? = null

    private var likeButton : ImageButton? = null
    private var title : TextView? = null
    private var stars : ChipGroup? = null
    private var tabbar: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        componentViewModel.getFeatureComponent().injectProductFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view =  super.onCreateView(inflater, container, savedInstanceState)!!

        imageViewPager = view.findViewById(R.id.image_view_pager)
        detailsViewPager = view.findViewById<ViewPager2?>(R.id.details_pager).also {
            it.isUserInputEnabled = false
        }

        view.findViewById<Button>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<Button>(R.id.cart).setOnClickListener {
            showCart()
        }

        likeButton = view.findViewById<ImageButton?>(R.id.like).also {
            it.setOnClickListener {
                it.isSelected = !it.isSelected
                productViewModel.likeProduct(it.isSelected)
            }
        }

        title = view.findViewById(R.id.product_name)
        stars = view.findViewById(R.id.stars)

        tabbar = view.findViewById(R.id.tab_layout)
        tabbar!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.id){
                    R.id.shop -> detailsViewPager!!.setCurrentItem(0,true)
                    R.id.details -> tabbar!!.getTabAt(0)!!.select()
                    R.id.feature -> tabbar!!.getTabAt(0)!!.select()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }
        })

        initImageViewPager()

        subscribeToViewModel()

        return view
    }

    override fun onStart() {
        super.onStart()
        productViewModel.update()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imageViewPager = null
        detailsViewPager = null
        likeButton = null
        title = null
        stars = null
        tabbar = null
    }

    private fun subscribeToViewModel(){
        lifecycleScope.launchWhenStarted {
            productViewModel.productPhotos.onEach { items ->
                (imageViewPager!!.adapter as ListDelegationAdapter<List<ImageItem>>).let {
                    it.items = items.map { Image(it) }
                    it.notifyDataSetChanged()
                }
            }.collect()
        }
        lifecycleScope.launchWhenStarted {
            productViewModel.title.onEach {
                title!!.text = it
            }
        }
        lifecycleScope.launchWhenStarted {
            productViewModel.rating.onEach {
                setRating(it)
            }.collect()
        }
        lifecycleScope.launchWhenStarted {
            productViewModel.isFavorites.onEach {
                likeButton!!.isSelected = it
            }.collect()
        }
    }


    private fun showCart(){
        TODO()
    }

    private fun setRating(rating : Int){
        stars!!.removeAllViews()
        val _rating = max(0,min(5, rating))
        if (_rating > 0){
            val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            repeat(_rating){
                val view = inflater.inflate(R.layout.star_chip, stars, false)
                stars!!.addView(view)
            }
        }
    }

    private fun initImageViewPager(){

        imageViewPager!!.adapter = ListDelegationAdapter<List<ImageItem>>(
            getImageAdapterDelegate(imageLoader)
        )
        imageViewPager!!.offscreenPageLimit = 1

        val outItemVisible = (30).dp2px(requireContext())
        val currentItemHorizontalMargin = (50).dp2px(requireContext())
        val pageTranslationX = outItemVisible + currentItemHorizontalMargin
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.alpha = 0.25f + (1 - abs(position))
        }
        imageViewPager!!.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration( (50).dp2px(requireContext()))
        imageViewPager!!.addItemDecoration(itemDecoration)

    }

    private class HorizontalMarginItemDecoration(private val horizontalMargin: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.right = horizontalMargin
            outRect.left = horizontalMargin
        }

    }


}