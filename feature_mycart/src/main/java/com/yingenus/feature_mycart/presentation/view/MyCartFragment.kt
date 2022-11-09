package com.yingenus.feature_mycart.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.yingenus.core.textutils.convertPrise
import com.yingenus.feature_mycart.R
import com.yingenus.feature_mycart.domain.dto.BasketProduct
import com.yingenus.feature_mycart.domain.dto.Delivery
import com.yingenus.feature_mycart.presentation.adapterdelegate.getProductDelegate
import com.yingenus.feature_mycart.presentation.adapteritem.BasketItem
import com.yingenus.feature_mycart.presentation.adapteritem.Product
import com.yingenus.feature_mycart.presentation.viewmodel.ComponentViewModel
import com.yingenus.feature_mycart.presentation.viewmodel.MyCartViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Provider

internal class MyCartFragment : Fragment(R.layout.my_cart_fragment) {

    private val componentViewModule: ComponentViewModel by viewModels()
    @Inject
    lateinit var myCartViewModelProvider: Provider<MyCartViewModel.MyCartViewModelProvider>
    private val myCartViewModel : MyCartViewModel by viewModels{
        componentViewModule.getFeatureComponent().injectMyCartFragment(this)
        myCartViewModelProvider.get()
    }

    private var total : TextView? = null
    private var delivery : TextView? = null
    private var basketRecycler : RecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)!!

        view.findViewById<Button>(R.id.back).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<Button>(R.id.location).setOnClickListener {
            addAddress()
        }
        view.findViewById<Button>(R.id.checkout).setOnClickListener {
            myCartViewModel.checkOut()
        }
        total = view.findViewById(R.id.total_value)
        delivery = view.findViewById(R.id.delivery_value)
        basketRecycler = view.findViewById(R.id.recycler)

        basketRecycler!!.adapter = ListDelegationAdapter<List<BasketItem>>(
            getProductDelegate({
                    myCartViewModel.addItemFromBasketItem(it.basketProduct,1) },
                {
                    myCartViewModel.deleteItemFromBasketItem(it.basketProduct,1)},
                {
                    myCartViewModel.deleteBasketItem(it.basketProduct)})
        )

        subscribeToViewModel()


        return view
    }

    override fun onStart() {
        super.onStart()
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
            }
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