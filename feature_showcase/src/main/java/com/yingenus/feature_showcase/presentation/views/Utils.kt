package com.yingenus.feature_showcase.presentation.views

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.yingenus.feature_showcase.domain.dto.Location

internal class AutoCompleteTextViewHelper<T>(val autoCompleteTextView: AutoCompleteTextView,private val toString : (T) -> String){

    private var itemList : List<T> = emptyList()
    private var onSelected : ((T) -> Unit)? = null
    private var onNotSelected : (() -> Unit)? = null

    fun init(context: Context){
        autoCompleteTextView.setAdapter(ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line))
        autoCompleteTextView.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                onSelected?.let { on ->
                    getSelectedItem()?.let { on.invoke(it) }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                onNotSelected?.invoke()
            }
        }
    }

    fun setItems(items : List<T>){
        itemList = emptyList()
        val adapter = autoCompleteTextView.adapter as ArrayAdapter<String>
        adapter.clear()
        adapter.addAll(itemList.map { toString.invoke(it) })
    }

    fun selectItem(item: T){
        if (!itemList.any { toString.invoke(it) == toString.invoke(item) }){
            itemList = itemList + item
        }
        autoCompleteTextView.setText(toString.invoke(item))
    }

    fun getSelectedItem():T?{
        val line = autoCompleteTextView.text.toString()
        return itemList.find { toString.invoke(it) == line }
    }

    fun onItemSelected(on : (T) -> Unit){
        onSelected = on
    }
    fun onNothingSelected( on : ()-> Unit){
        onNotSelected = on
    }

}