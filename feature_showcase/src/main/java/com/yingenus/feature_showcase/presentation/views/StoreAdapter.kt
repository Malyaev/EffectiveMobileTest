package com.yingenus.feature_showcase.presentation.views

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.yingenus.api_network.api.ImageLoader
import com.yingenus.core.sizeutils.dp2px
import com.yingenus.feature_showcase.presentation.adapterItem.BestSeller
import com.yingenus.feature_showcase.presentation.adapterItem.Header
import com.yingenus.feature_showcase.presentation.adapterItem.HotSalesItem
import com.yingenus.feature_showcase.presentation.adapterItem.ShopItem
import com.yingenus.feature_showcase.presentation.adapterdelegate.getBestSalterAdapterDelegate
import com.yingenus.feature_showcase.presentation.adapterdelegate.getHeaderAdapterDelegate
import com.yingenus.feature_showcase.presentation.adapterdelegate.getHotSalesContainerAdapterDelegate

internal class StoreAdapter( imageLoader: ImageLoader,
    onHeader : (Header) -> Unit,
    onBestSeller : (BestSeller) -> Unit,
    onLikeBestSeller : (BestSeller, Boolean) -> Unit,
    hotSalesItemAdapter: ListDelegationAdapter<List<HotSalesItem>>
): ListDelegationAdapter<List<ShopItem>>(
    getHeaderAdapterDelegate(onHeader),
    getBestSalterAdapterDelegate(imageLoader,onBestSeller,onLikeBestSeller),
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

    class HotSalesBoundDecorator : RecyclerView.ItemDecoration(){
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildLayoutPosition(view)
            if ( (parent.adapter as StoreAdapter).getItems()?.getOrNull(position) is BestSeller ){
                val items = (parent.adapter as StoreAdapter).items!!
                val firstBestSeller = items.indexOfFirst { it is BestSeller }
                val positionInBestSellers = (items.size - position) - firstBestSeller
                val paddings = (8).dp2px(view.context)
                outRect.top = (paddings/2)
                outRect.bottom = (paddings/2)
                if (positionInBestSellers%2 != 0){
                    outRect.left += paddings
                    outRect.right += (paddings/2)
                }else{
                    outRect.right += paddings
                    outRect.left += (paddings/2)
                }
            }
        }
    }
}
