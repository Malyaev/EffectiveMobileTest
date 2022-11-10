package com.yingenus.feature_showcase.presentation.views

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.yingenus.feature_showcase.presentation.adapterItem.BestSeller
import com.yingenus.feature_showcase.presentation.adapterItem.Header
import com.yingenus.feature_showcase.presentation.adapterItem.HotSalesItem
import com.yingenus.feature_showcase.presentation.adapterItem.ShopItem
import com.yingenus.feature_showcase.presentation.adapterdelegate.getBestSalterAdapterDelegate
import com.yingenus.feature_showcase.presentation.adapterdelegate.getHeaderAdapterDelegate
import com.yingenus.feature_showcase.presentation.adapterdelegate.getHotSalesContainerAdapterDelegate

internal class StoreAdapter(
    onHeader : (Header) -> Unit,
    onBestSeller : (BestSeller) -> Unit,
    onLikeBestSeller : (BestSeller, Boolean) -> Unit,
    hotSalesItemAdapter: ListDelegationAdapter<List<HotSalesItem>>
): ListDelegationAdapter<List<ShopItem>>(
    getHeaderAdapterDelegate(onHeader),
    getBestSalterAdapterDelegate(onBestSeller,onLikeBestSeller),
    getHotSalesContainerAdapterDelegate(hotSalesItemAdapter)
){
    class HotSalesSizeLookup(val recyclerView: RecyclerView) : GridLayoutManager.SpanSizeLookup(){

        override fun getSpanSize(position: Int): Int {
            return (recyclerView.adapter as StoreAdapter).getItems()?.getOrNull(position)?.let {
                when(it){
                    is Header -> (recyclerView.layoutManager as GridLayoutManager).spanCount
                    is BestSeller -> 1
                    is List<*> -> (recyclerView.layoutManager as GridLayoutManager).spanCount
                    else -> (recyclerView.layoutManager as GridLayoutManager).spanCount
                }
            }?: throw RuntimeException()
        }

    }
}
