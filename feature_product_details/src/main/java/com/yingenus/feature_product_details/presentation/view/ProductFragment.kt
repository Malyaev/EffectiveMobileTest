package com.yingenus.feature_product_details.presentation.view

import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabLayout
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.yingenus.ImageUtils.isChecked
import com.yingenus.api_network.api.ImageLoader
import com.yingenus.core.sizeutils.dp2px
import com.yingenus.feature_product_details.R
import com.yingenus.feature_product_details.presentation.adapterelegat.getImageAdapterDelegate
import com.yingenus.feature_product_details.presentation.adapteritem.Image
import com.yingenus.feature_product_details.presentation.adapteritem.ImageItem
import com.yingenus.feature_product_details.presentation.adapters.ProductInfoAdapter
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
            it.adapter = ProductInfoAdapter(this)
        }

        view.findViewById<ImageButton>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<ImageButton>(R.id.cart).setOnClickListener {
            showCart()
        }

        likeButton = view.findViewById<ImageButton?>(R.id.like).also {
            it.setOnClickListener {
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

        val dialog = view.findViewById<View>(R.id.button_dialog)
        initDialog(dialog,imageViewPager!!)

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
            }.collect()
        }
        lifecycleScope.launchWhenStarted {
            productViewModel.rating.onEach {
                setRating(it)
            }.collect()
        }
        lifecycleScope.launchWhenStarted {
            productViewModel.isFavorites.onEach {
                likeButton!!.isChecked(it)
            }.collect()
        }
    }


    private fun showCart(){
        val uri = Uri.parse("App://feature_my_chart/")
        findNavController().navigate(uri)
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
        val pageTranslationZ = (10).dp2px(requireContext())
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.translationZ = pageTranslationZ*(1-abs(position))
            page.scaleY = 1 - (0.1f * abs(position))
        }

        imageViewPager!!.setPageTransformer(pageTransformer)



        val itemDecoration = HorizontalMarginItemDecoration( (50).dp2px(requireContext()))
        imageViewPager!!.addItemDecoration(itemDecoration)
        imageViewPager!!.addItemDecoration(VerticalMarginItemDecoration((20).dp2px(requireContext())))

    }

    private fun initDialog( view: View, topView: View){
        val heightSpec = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED)
        topView.measure(heightSpec,widthSpec)
        val topViewHeight = topView.measuredHeight

        val height = getDisplayHeight(requireContext()) -
                getStatusBarHeight() -
                (56).dp2px(requireContext()) -
                (30).dp2px(requireContext()) -
                (380).dp2px(requireContext())

        val  behavior = (view.layoutParams as CoordinatorLayout.LayoutParams).behavior as BottomSheetBehavior
        behavior.peekHeight = max(height,(50).dp2px(requireContext()))
        behavior.isDraggable = true
    }

    private fun getStatusBarHeight(): Int{
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    private fun getDisplayHeight(context : Context): Int =  try {
        context.display!!.height
    } catch (e : NoSuchMethodError){
        context.resources.displayMetrics.heightPixels
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

    private class VerticalMarginItemDecoration(private val verticalMargin: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.top = verticalMargin
            outRect.bottom = verticalMargin
        }

    }


}