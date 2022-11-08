package com.yingenus.feature_showcase.presentation.adapterdelegate

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.yingenus.feature_showcase.R
import com.yingenus.feature_showcase.presentation.adapterItem.HotSales
import com.yingenus.feature_showcase.presentation.adapterItem.HotSalesItem

internal fun getHotSalesAdapterDelegate( onBuyClicked : (HotSales) -> Unit) =
    adapterDelegate<HotSales, HotSalesItem>(R.layout.hot_sales_item){
        val icNew : View = findViewById(R.id.new_icon)
        val title : TextView = findViewById(R.id.title)
        val details : TextView = findViewById(R.id.sub_title)
        val buyNow : Button = findViewById(R.id.buyButton)
        val image : ImageView = findViewById(R.id.image)

        buyNow.setOnClickListener { onBuyClicked(item) }

        bind {
            icNew.visibility = if(item.hotSalesProduct.isNew) View.VISIBLE else View.GONE
            title.text = item.hotSalesProduct.title
            details.text = item.hotSalesProduct.subtitle
            image.setImageURI(item.hotSalesProduct.picture)
        }
    }