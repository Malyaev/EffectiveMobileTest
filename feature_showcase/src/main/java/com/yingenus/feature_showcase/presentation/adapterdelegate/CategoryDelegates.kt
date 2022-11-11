package com.yingenus.feature_showcase.presentation.adapterdelegate

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.yingenus.api_network.api.ImageLoader
import com.yingenus.feature_showcase.R
import com.yingenus.feature_showcase.presentation.adapterItem.Category
import com.yingenus.feature_showcase.presentation.adapterItem.CategoryItem

internal fun getCategoryAdapterDelegate(imageLoader: ImageLoader, onCategoryClicked : (Category) -> Unit) =
    adapterDelegate<Category, CategoryItem>(R.layout.category_item){
        val icon : ImageView = findViewById(R.id.icon)
        val title : TextView = findViewById(R.id.title)
        val container : View = findViewById(R.id.image_container)

        container.setOnClickListener {
            onCategoryClicked(item)
        }

        bind {
            imageLoader.loadImage(item.category.icon,icon,icon.width,icon.height)
            title.text = item.category.title
            itemView.isSelected = item.isSelected
        }
    }