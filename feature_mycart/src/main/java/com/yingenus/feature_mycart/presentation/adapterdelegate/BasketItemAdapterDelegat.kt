package com.yingenus.feature_mycart.presentation.adapterdelegate

import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.yingenus.api_network.api.ImageLoader
import com.yingenus.core.textutils.convertPrise
import com.yingenus.feature_mycart.R
import com.yingenus.feature_mycart.presentation.adapteritem.BasketItem
import com.yingenus.feature_mycart.presentation.adapteritem.Product


internal fun getProductDelegate(
    imageLoader: ImageLoader,
    onAdd : (Product) -> Unit,
    onMinus : (Product) -> Unit,
    onDelete: (Product) -> Unit) = adapterDelegate<Product,BasketItem>(R.layout.basket_item){

    val image : ImageView = findViewById(R.id.image)
    val title : TextView = findViewById(R.id.name)
    val prise : TextView = findViewById(R.id.prise)
    val count : TextView = findViewById(R.id.count)

    findViewById<ImageButton>(R.id.delete).setOnClickListener {
        onDelete(item)
    }
    findViewById<Button>(R.id.plus).setOnClickListener {
        onAdd(item)
    }
    findViewById<Button>(R.id.minus).setOnClickListener {
        onMinus(item)
    }

    bind {
        imageLoader.loadImage(item.basketProduct.images,image)
        title.text = item.basketProduct.title
        prise.text = item.basketProduct.price.convertPrise("$",2)
        count.text = item.basketProduct.number.toString()
    }
}
