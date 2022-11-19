package com.yingenus.feature_mycart.presentation.view

import android.app.Application
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.yingenus.api_network.api.ImageLoader
import com.yingenus.core.colors.resolveColorAttr
import com.yingenus.core.sizeutils.dp2px
import com.yingenus.core.textutils.convertPrise
import com.yingenus.core.viewmodels.ComponentViewModel
import com.yingenus.feature_mycart.R
import com.yingenus.feature_mycart.di.FeatureMyCartComponent
import com.yingenus.feature_mycart.domain.dto.Delivery
import com.yingenus.feature_mycart.presentation.adapterdelegate.getProductDelegate
import com.yingenus.feature_mycart.presentation.adapteritem.BasketItem
import com.yingenus.feature_mycart.presentation.adapteritem.Product
import com.yingenus.feature_mycart.presentation.viewmodel.MyCartComponentViewModelFactory
import com.yingenus.feature_mycart.presentation.viewmodel.MyCartViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Provider

internal class MyCartFragment : Fragment(R.layout.my_cart_fragment) {

    @Inject
    lateinit var imageLoader: ImageLoader

    private val componentViewModule: ComponentViewModel<FeatureMyCartComponent> by viewModels{
        MyCartComponentViewModelFactory()
    }
    @Inject
    lateinit var myCartViewModelProvider: Provider<MyCartViewModel.MyCartViewModelProvider>
    private val myCartViewModel : MyCartViewModel by viewModels{
        myCartViewModelProvider.get()
    }

    private var total : TextView? = null
    private var delivery : TextView? = null
    private var basketRecycler : RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        componentViewModule
            .getComponent(requireContext().applicationContext as Application)
            .injectMyCartFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!

        view.findViewById<ImageButton>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<ImageButton>(R.id.location).setOnClickListener {
            addAddress()
        }
        view.findViewById<Button>(R.id.checkout).setOnClickListener {
            myCartViewModel.checkOut()
        }
        total = view.findViewById(R.id.total_value)
        delivery = view.findViewById(R.id.delivery_value)
        basketRecycler = view.findViewById(R.id.recycler)

        basketRecycler!!.adapter = ListDelegationAdapter<List<BasketItem>>(
            getProductDelegate(imageLoader,{
                    myCartViewModel.addItemFromBasketItem(it.basketProduct,1) },
                {
                    myCartViewModel.deleteItemFromBasketItem(it.basketProduct,1)},
                {
                    myCartViewModel.deleteBasketItem(it.basketProduct)})
        )

        basketRecycler!!.addItemDecoration(object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildLayoutPosition(view)
                if (position == 0) outRect.top += (30).dp2px(requireContext())
                else outRect.top += (6).dp2px(requireContext())
                outRect.bottom += (6).dp2px(requireContext())
            }
        })

        subscribeToViewModel()

        return view
    }

    override fun onStart() {
        super.onStart()
        myCartViewModel.update()
        requireActivity().window.navigationBarColor = requireContext().resolveColorAttr(androidx.appcompat.R.attr.colorPrimary)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        total = null
        delivery = null
        basketRecycler = null
    }

    private fun subscribeToViewModel(){
        lifecycleScope.launchWhenStarted {
            myCartViewModel.basketItem.onEach { items ->
                (basketRecycler!!.adapter as ListDelegationAdapter<List<BasketItem>>).let {
                    it.items = items.map { Product(it) }
                    it.notifyDataSetChanged()
                }
            }.collect()
        }
        lifecycleScope.launchWhenStarted {
            myCartViewModel.delivery.onEach {
                delivery!!.text = when(it){
                    is Delivery.Prise ->
                        it.prise.convertPrise("$",2,"us")
                    is Delivery.Free ->
                        getString(R.string.delivery_free)
                }
            }.collect()
        }
        lifecycleScope.launchWhenStarted {
            myCartViewModel.total.onEach {
                total!!.text = it.convertPrise("$",2)
            }.collect()
        }
        lifecycleScope.launchWhenStarted {
            myCartViewModel.error.onEach {
                //TODO(show error dialog)
            }.collect()
        }
    }

    private fun addAddress(){

    }
}