package com.yingenus.feature_showcase.presentation.adapterdelegate

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import com.yingenus.feature_showcase.R
import com.yingenus.feature_showcase.presentation.adapterItem.Category
import com.yingenus.feature_showcase.presentation.adapterItem.CategoryItem

internal fun getCategoryAdapterDelegate( onCategoryClicked : (Category) -> Unit) =
    adapterDelegate<Category, CategoryItem>(R.layout.category_item){
        val icon : ImageView = findViewById(R.id.icon)
        val title : TextView = findViewById(R.id.title)
        val container : View = findViewById(R.id.image_container)

        container.setOnClickListener {
            onCategoryClicked(item)
        }

        bind {
            icon.setImageURI(item.category.icon)
            title.text = item.category.title
            itemView.isSelected = item.isSelected
        }
    }