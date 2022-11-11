package com.yingenus.feature_showcase.presentation.adapterdelegate

import android.annotation.SuppressLint
import android.graphics.Paint
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.yingenus.api_network.api.ImageLoader
import com.yingenus.feature_showcase.R
import com.yingenus.core.textutils.convertPrise
import com.yingenus.feature_showcase.presentation.adapterItem.*
import com.yingenus.feature_showcase.presentation.adapterItem.ShopItem
import com.yingenus.feature_showcase.presentation.adapterItem.BestSeller
import com.yingenus.feature_showcase.presentation.adapterItem.Header
import com.yingenus.feature_showcase.presentation.adapterItem.HotSalesItem


internal fun getHeaderAdapterDelegate( viewAllClicked : (Header) -> Unit ) =
    adapterDelegate<Header, ShopItem>(R.layout.title_product_item){
        val title : TextView = findViewById(R.id.title)
        val viewButton : Button = findViewById(R.id.see_more)
        viewButton.setOnClickListener { viewAllClicked(item) }
        bind {
            title.text = item.title
        }
    }

internal fun getBestSalterAdapterDelegate(imageLoader: ImageLoader, viewClicked: (BestSeller) -> Unit,
                                         onLikeClicked : (BestSeller, Boolean) -> Unit) =
    adapterDelegate<BestSeller, ShopItem>(R.layout.best_seller_item){
        val image : ImageView = findViewById(R.id.image)
        val priseDiscount : TextView = findViewById(R.id.discount_prise)
        val priseWithoutDiscount : TextView = findViewById(R.id.prise_without_discount)
        priseWithoutDiscount.paintFlags = priseWithoutDiscount.paintFlags.and(Paint.STRIKE_THRU_TEXT_FLAG)
        val title : TextView = findViewById(R.id.title)
        val likeButton : ImageButton = findViewById(R.id.like)

        itemView.setOnClickListener { viewClicked(item) }
        likeButton.setOnClickListener {
            likeButton.isSelected = !likeButton.isSelected
            onLikeClicked(item, likeButton.isSelected)
        }

        bind {
            imageLoader.loadImage(item.bestSellerProduct.picture,image)
            priseDiscount.text = item.bestSellerProduct.discountPrise.convertPrise("$")
            priseWithoutDiscount.text = item.bestSellerProduct.priceWithoutDiscount.convertPrise("$")
            title.text = item.bestSellerProduct.title
            likeButton.isSelected = item.bestSellerProduct.isFavorites
        }

    }

internal fun getHotSalesContainerAdapterDelegate(
    hotSalesItemAdapter: ListDelegationAdapter<List<HotSalesItem>>)
= adapterDelegate<HotSalesContainer, ShopItem>(R.layout.hot_sales_item_container){
    val recycler : RecyclerView = findViewById(R.id.recycler)
    recycler.adapter = hotSalesItemAdapter

    bind {
        hotSalesItemAdapter.items = item.hotSalesItem
        hotSalesItemAdapter.notifyDataSetChanged()
    }
}